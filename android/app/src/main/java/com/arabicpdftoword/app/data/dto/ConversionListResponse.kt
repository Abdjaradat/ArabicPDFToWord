package com.arabicpdftoword.app.data.dto

import com.google.gson.annotations.SerializedName

data class ConversionListResponse(
    @SerializedName("data")
    val data: List<ConversionStatusResponse>,
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("size")
    val size: Int = 20,
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 1,
    @SerializedName("has_more")
    val hasMore: Boolean = false
)
