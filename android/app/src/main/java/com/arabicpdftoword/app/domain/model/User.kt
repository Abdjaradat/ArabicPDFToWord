package com.arabicpdftoword.app.domain.model

data class User(
    val id: String,
    val email: String,
    val fullName: String = "",
    val isPremium: Boolean = false,
    val premiumUntil: Long? = null,
    val conversionCount: Int = 0,
    val dailyConversionCount: Int = 0
)
