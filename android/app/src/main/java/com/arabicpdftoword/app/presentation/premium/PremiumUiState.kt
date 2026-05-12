package com.arabicpdftoword.app.presentation.premium

data class PremiumUiState(
    val monthlyPrice: String? = "$9.99/month",
    val yearlyPrice: String? = "$49.99/year",
    val yearlySavings: String? = "Save 58%",
    val isPurchasing: Boolean = false,
    val isLoadingProducts: Boolean = true,
    val isPremium: Boolean = false,
    val error: String? = null,
    val purchaseSuccess: Boolean = false
)
