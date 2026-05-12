package com.arabicpdftoword.app.presentation.home

import com.arabicpdftoword.app.domain.model.ConversionItem

data class HomeUiState(
    val recentConversions: List<ConversionItem> = emptyList(),
    val todayConversions: Int = 0,
    val totalConversions: Int = 0,
    val isPremium: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
