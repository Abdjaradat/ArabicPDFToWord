package com.arabicpdftoword.app.presentation.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arabicpdftoword.app.core.util.NoorPreferences
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs: NoorPreferences,
    private val conversionRepository: ConversionRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        observePreferences()
        calculateCacheSize()
        loadAppVersion()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            combine(
                prefs.darkMode,
                prefs.language,
                prefs.isLoggedIn,
                prefs.userEmail,
                prefs.isPremium
            ) { darkMode, lang, loggedIn, email, premium ->
                _uiState.update {
                    it.copy(
                        isDarkMode = darkMode,
                        language = lang,
                        isLoggedIn = loggedIn,
                        userEmail = email,
                        isPremium = premium
                    )
                }
            }.collect()
        }
    }

    private fun loadAppVersion() {
        try {
            val version = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            _uiState.update { it.copy(appVersion = version ?: "1.0.0") }
        } catch (_: Exception) {}
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            prefs.setDarkMode(enabled)
        }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            prefs.setLanguage(lang)
        }
    }

    fun toggleAutoDelete(enabled: Boolean) {
        viewModelScope.launch {
            prefs.setAutoDelete(enabled)
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            try {
                val cacheDir = context.cacheDir
                cacheDir.deleteRecursively()
                cacheDir.mkdirs()
                _uiState.update { it.copy(cacheSize = "0 B") }
            } catch (_: Exception) {}
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            try {
                conversionRepository.clearAllLocal()
                val conversionsDir = File(context.cacheDir, "conversions")
                if (conversionsDir.exists()) conversionsDir.deleteRecursively()
            } catch (_: Exception) {}
        }
    }

    fun logout() {
        viewModelScope.launch {
            prefs.setLoggedIn(false)
            prefs.setUserEmail(null)
            try {
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
            } catch (_: Exception) {}
        }
    }

    private fun calculateCacheSize() {
        viewModelScope.launch {
            try {
                val size = getDirectorySize(context.cacheDir)
                _uiState.update { it.copy(cacheSize = formatSize(size)) }
            } catch (_: Exception) {}
        }
    }

    private fun getDirectorySize(dir: File): Long {
        var size = 0L
        if (dir.isDirectory) {
            for (file in dir.listFiles() ?: emptyArray()) {
                size += if (file.isDirectory) getDirectorySize(file) else file.length()
            }
        } else {
            size = dir.length()
        }
        return size
    }

    private fun formatSize(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> "%.1f KB".format(bytes / 1024.0)
            else -> "%.1f MB".format(bytes / (1024.0 * 1024.0))
        }
    }
}
