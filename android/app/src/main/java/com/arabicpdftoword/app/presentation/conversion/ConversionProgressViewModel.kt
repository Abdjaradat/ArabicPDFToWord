package com.arabicpdftoword.app.presentation.conversion

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arabicpdftoword.app.core.common.Constants
import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.core.util.NoorPreferences
import com.arabicpdftoword.app.domain.model.ConversionItem
import com.arabicpdftoword.app.domain.model.ConversionStatus
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConversionProgressViewModel @Inject constructor(
    private val repository: ConversionRepository,
    private val prefs: NoorPreferences,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConversionProgressUiState>(ConversionProgressUiState.Idle)
    val uiState: StateFlow<ConversionProgressUiState> = _uiState.asStateFlow()

    private var pollingJob: Job? = null
    private var isCancelled = false

    fun startPolling(conversionId: String) {
        pollingJob?.cancel()
        isCancelled = false
        _uiState.value = ConversionProgressUiState.Idle

        pollingJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            _uiState.update {
                ConversionProgressUiState.Processing(
                    progress = 0,
                    status = "Uploading...",
                    fileName = ""
                )
            }

            try {
                val initialResult = repository.getConversionStatus(conversionId)
                val initialFileName = when (initialResult) {
                    is Resource.Success -> initialResult.data?.originalFileName ?: ""
                    else -> ""
                }
                _uiState.update {
                    ConversionProgressUiState.Processing(
                        progress = 5,
                        status = "Preparing file...",
                        fileName = initialFileName
                    )
                }

                while (isActive) {
                    if (isCancelled) {
                        return@launch
                    }

                    val elapsed = System.currentTimeMillis() - startTime
                    if (elapsed > Constants.MAX_POLL_TIME_MS) {
                        _uiState.update {
                            ConversionProgressUiState.Failed("Conversion timed out after 30 minutes")
                        }
                        return@launch
                    }

                    delay(Constants.POLL_INTERVAL_MS)

                    try {
                        when (val result = repository.getConversionStatus(conversionId)) {
                            is Resource.Success -> {
                                val item = result.data!!
                                when (item.status) {
                                    ConversionStatus.COMPLETED -> {
                                        _uiState.update {
                                            ConversionProgressUiState.Processing(
                                                progress = 95,
                                                status = "Downloading result..."
                                            )
                                        }

                                        val outputDir = File(context.cacheDir, "conversions")
                                        outputDir.mkdirs()
                                        val outputFile = File(
                                            outputDir,
                                            "${UUID.randomUUID()}_result.docx"
                                        )

                                        val downloadResult = repository.downloadResult(conversionId, outputFile)
                                        when (downloadResult) {
                                            is Resource.Success -> {
                                                repository.saveLocalConversion(item)
                                                _uiState.update {
                                                    ConversionProgressUiState.Completed(
                                                        conversionItem = item,
                                                        outputFile = downloadResult.data
                                                    )
                                                }
                                            }
                                            is Resource.Error -> {
                                                _uiState.update {
                                                    ConversionProgressUiState.Failed(downloadResult.message)
                                                }
                                            }
                                            else -> {}
                                        }
                                        return@launch
                                    }
                                    ConversionStatus.FAILED -> {
                                        repository.saveLocalConversion(item)
                                        _uiState.update {
                                            ConversionProgressUiState.Failed(item.errorMessage ?: "Conversion failed")
                                        }
                                        return@launch
                                    }
                                    else -> {
                                        val progress = ((System.currentTimeMillis() - startTime).toFloat() / Constants.MAX_POLL_TIME_MS * 80 + 10).toInt().coerceIn(10, 90)
                                        val statusText = when {
                                            progress < 30 -> "Extracting text from PDF..."
                                            progress < 60 -> "Running OCR..."
                                            progress < 85 -> "Generating Word document..."
                                            else -> "Finalizing..."
                                        }
                                        _uiState.update {
                                            ConversionProgressUiState.Processing(
                                                progress = progress,
                                                status = statusText,
                                                fileName = item.originalFileName
                                            )
                                        }
                                    }
                                }
                            }
                            is Resource.Error -> {
                                if (result.message.contains("timeout", ignoreCase = true)) {
                                    continue
                                }
                            }
                            is Resource.Loading -> {}
                        }
                    } catch (e: Exception) {
                        if (e.message?.contains("timeout", ignoreCase = true) == true) {
                            continue
                        }
                        _uiState.update {
                            ConversionProgressUiState.Processing(
                                progress = (_uiState.value as? ConversionProgressUiState.Processing)?.progress
                                    ?: 0,
                                status = "Processing...",
                                fileName = (_uiState.value as? ConversionProgressUiState.Processing)?.fileName
                                    ?: ""
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    ConversionProgressUiState.Failed(e.message ?: "Unknown error occurred")
                }
            }
        }
    }

    fun cancelConversion() {
        isCancelled = true
        pollingJob?.cancel()
        _uiState.update {
            ConversionProgressUiState.Failed("Conversion cancelled")
        }
    }

    fun retry(conversionId: String) {
        startPolling(conversionId)
    }

    override fun onCleared() {
        super.onCleared()
        pollingJob?.cancel()
    }
}
