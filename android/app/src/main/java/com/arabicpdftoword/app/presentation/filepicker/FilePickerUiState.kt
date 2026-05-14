package com.arabicpdftoword.app.presentation.filepicker

import android.net.Uri

data class FilePickerUiState(
    val selectedFileUri: Uri? = null,
    val selectedFileName: String? = null,
    val selectedFileSize: Long? = null,
    val language: String = "ara",
    val quality: String = "normal",
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
    val error: String? = null,
    val conversionId: String? = null,
    val dailyCount: Int = 0,
    val isPremium: Boolean = false,
    val showAdForQuality: String? = null
)
