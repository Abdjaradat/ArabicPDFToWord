package com.arabicpdftoword.app.presentation.settings

data class SettingsUiState(
    val isDarkMode: Boolean = false,
    val language: String = "ar",
    val cacheSize: String = "Calculating...",
    val isLoggedIn: Boolean = false,
    val userEmail: String? = null,
    val isPremium: Boolean = false,
    val appVersion: String = "1.0.0"
)
