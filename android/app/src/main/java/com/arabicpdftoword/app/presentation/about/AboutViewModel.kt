package com.arabicpdftoword.app.presentation.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arabicpdftoword.app.core.util.NoorPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AboutUiState(
    val appVersion: String = "1.0.0",
    val appName: String = "Arabic PDF To Word AI Converter",
    val developerName: String = "Noor AlArabia WalIslam",
    val description: String = "Convert Arabic PDF documents to Word format with cutting-edge AI technology. Preserves Arabic text, formatting, and right-to-left layout."
)

@HiltViewModel
class AboutViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AboutUiState())
    val uiState: StateFlow<AboutUiState> = _uiState.asStateFlow()
}
