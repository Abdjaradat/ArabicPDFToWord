package com.arabicpdftoword.app.presentation.history

import com.arabicpdftoword.app.domain.model.ConversionItem

data class HistoryUiState(
    val conversions: List<ConversionItem> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val filter: String? = null,
    val searchQuery: String? = null,
    val error: String? = null,
    val hasMorePages: Boolean = true,
    val currentPage: Int = 0,
    val isPremium: Boolean = false
)
