package com.arabicpdftoword.app.data.dto

import com.google.gson.annotations.SerializedName

data class ConversionStatsResponse(
    @SerializedName("total_conversions")
    val totalConversions: Int = 0,
    @SerializedName("today_conversions")
    val todayConversions: Int = 0,
    @SerializedName("storage_used")
    val storageUsed: Long = 0L,
    @SerializedName("premium_conversions")
    val premiumConversions: Int = 0,
    @SerializedName("free_conversions_remaining")
    val freeConversionsRemaining: Int? = null,
    @SerializedName("is_premium")
    val isPremium: Boolean = false
)
