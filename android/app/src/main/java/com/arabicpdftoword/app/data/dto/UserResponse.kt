package com.arabicpdftoword.app.data.dto

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String? = null,
    @SerializedName("is_premium")
    val isPremium: Boolean = false,
    @SerializedName("premium_until")
    val premiumUntil: String? = null,
    @SerializedName("conversion_count")
    val conversionCount: Int = 0,
    @SerializedName("daily_conversion_count")
    val dailyConversionCount: Int = 0,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("email_verified")
    val emailVerified: Boolean = false
)
