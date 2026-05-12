package com.arabicpdftoword.app.domain.model

data class ConversionStats(
    val totalConversions: Int = 0,
    val todayConversions: Int = 0,
    val storageUsed: Long = 0L,
    val premiumConversions: Int = 0
)
