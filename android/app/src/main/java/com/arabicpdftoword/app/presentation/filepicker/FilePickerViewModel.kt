package com.arabicpdftoword.app.presentation.filepicker

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arabicpdftoword.app.core.common.Constants
import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.core.util.AdManager
import com.arabicpdftoword.app.core.util.NoorPreferences
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FilePickerViewModel @Inject constructor(
    private val repository: ConversionRepository,
    private val prefs: NoorPreferences,
    private val adManager: AdManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilePickerUiState())
    val uiState: StateFlow<FilePickerUiState> = _uiState.asStateFlow()

    init {
        loadUserState()
    }

    private fun loadUserState() {
        viewModelScope.launch {
            prefs.isPremium.collect { isPremium ->
                _uiState.update { it.copy(isPremium = isPremium) }
            }
        }
        viewModelScope.launch {
            val todayDate = currentDateString()
            val lastDate = prefs.lastConversionDate.first()
            if (lastDate != todayDate) {
                prefs.setDailyConversionCount(0)
                prefs.setLastConversionDate(todayDate)
            }
            prefs.dailyConversionCount.collect { count ->
                _uiState.update { it.copy(dailyCount = count) }
            }
        }
    }

    fun onFileSelected(uri: Uri) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(error = null) }

                val fileName = getFileName(uri) ?: "document.pdf"
                val fileSize = getFileSize(uri)
                val mimeType = context.contentResolver.getType(uri)

                if (mimeType != Constants.PDF_MIME_TYPE && !fileName.lowercase().endsWith(".pdf")) {
                    _uiState.update { it.copy(error = "Please select a PDF file") }
                    return@launch
                }

                val maxSize = if (_uiState.value.isPremium) {
                    Constants.MAX_FILE_SIZE_PREMIUM
                } else {
                    Constants.MAX_FILE_SIZE_FREE
                }

                if (fileSize != null && fileSize > maxSize) {
                    val maxMb = maxSize / (1024 * 1024)
                    _uiState.update { it.copy(error = "File size exceeds $maxMb MB limit") }
                    return@launch
                }

                _uiState.update {
                    it.copy(
                        selectedFileUri = uri,
                        selectedFileName = fileName,
                        selectedFileSize = fileSize,
                        isValid = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Failed to read file") }
            }
        }
    }

    fun clearFile() {
        _uiState.update {
            FilePickerUiState(
                language = it.language,
                dailyCount = it.dailyCount,
                isPremium = it.isPremium
            )
        }
    }

    fun setLanguage(language: String) {
        _uiState.update { it.copy(language = language) }
    }

    fun setQuality(quality: String) {
        _uiState.update { it.copy(quality = quality) }
    }

    /** Called when user clicks "Start" — triggers ad if needed */
    fun onStartClick(activity: Activity) {
        val state = _uiState.value
        val quality = state.quality

        if (quality == "normal" || state.isPremium) {
            startConversion()
            return
        }

        if (quality == "high") {
            _uiState.update { it.copy(showAdForQuality = "high", isLoading = true) }
            adManager.loadInterstitialAd(onLoaded = {
                adManager.showInterstitialAd(activity) {
                    _uiState.update { it.copy(showAdForQuality = null) }
                    startConversion()
                }
            })
        } else if (quality == "premium") {
            _uiState.update { it.copy(showAdForQuality = "premium", isLoading = true) }
            adManager.loadRewardedAd()
            adManager.showRewardedAd(activity, onRewarded = {
                _uiState.update { it.copy(showAdForQuality = null) }
                startConversion()
            }, onNotReady = {
                _uiState.update { it.copy(showAdForQuality = null, isLoading = false, error = "جاري تحميل الإعلان، حاول مرة أخرى") }
            })
        }
    }

    private fun startConversion() {
        val state = _uiState.value
        val uri = state.selectedFileUri ?: return
        val fileName = state.selectedFileName ?: return

        if (!state.isPremium && state.dailyCount >= Constants.FREE_DAILY_LIMIT) {
            _uiState.update { it.copy(error = "Daily limit reached. Upgrade to premium.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val tempFile = File(context.cacheDir, "uploads/$fileName")
                tempFile.parentFile?.mkdirs()
                context.contentResolver.openInputStream(uri)?.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                val result = repository.uploadPdf(tempFile, state.language, state.quality)
                when (result) {
                    is Resource.Success -> {
                        val item = result.data!!
                        repository.saveLocalConversion(item)
                        prefs.setDailyConversionCount(state.dailyCount + 1)
                        prefs.setLastConversionDate(currentDateString())

                        _uiState.update {
                            it.copy(isLoading = false, conversionId = item.conversionId)
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = result.message)
                        }
                    }
                    is Resource.Loading -> {}
                }

                tempFile.delete()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Conversion failed")
                }
            }
        }
    }

    private fun getFileName(uri: Uri): String? {
        var name: String? = null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex >= 0) {
                name = cursor.getString(nameIndex)
            }
        }
        return name
    }

    private fun getFileSize(uri: Uri): Long? {
        var size: Long? = null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst() && sizeIndex >= 0) {
                size = cursor.getLong(sizeIndex)
            }
        }
        return size
    }

    private fun currentDateString(): String {
        val cal = Calendar.getInstance()
        return "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DAY_OF_MONTH)}"
    }
}
