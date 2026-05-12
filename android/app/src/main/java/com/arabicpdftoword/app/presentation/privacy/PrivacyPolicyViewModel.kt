package com.arabicpdftoword.app.presentation.privacy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PrivacyPolicyUiState(
    val content: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PrivacyPolicyUiState())
    val uiState: StateFlow<PrivacyPolicyUiState> = _uiState.asStateFlow()

    init {
        loadPrivacyPolicy()
    }

    private fun loadPrivacyPolicy() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val content = """
                    |Privacy Policy
                    |Last updated: January 1, 2024
                    |
                    |1. Information We Collect
                    |We collect information you provide when using our PDF conversion service, including the PDF files you upload for conversion.
                    |
                    |2. How We Use Your Information
                    |Your PDF files are processed solely for the purpose of conversion. Files are automatically deleted from our servers after 24 hours.
                    |
                    |3. Data Storage
                    |We store conversion history locally on your device. Premium subscription data is stored securely on our servers.
                    |
                    |4. Third-Party Services
                    |We use Firebase for analytics and crash reporting. Google Play Services for in-app purchases.
                    |
                    |5. Data Security
                    |We implement industry-standard security measures to protect your data during transmission and processing.
                    |
                    |6. Your Rights
                    |You can request deletion of your data at any time by contacting us.
                    |
                    |7. Contact Us
                    |For privacy concerns: privacy@arabicpdftoword.com
                """.trimMargin()

                _uiState.update { it.copy(content = content, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }
}
