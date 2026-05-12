package com.arabicpdftoword.app.data.dto

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("conversion_id")
    val conversionId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("original_file_name")
    val originalFileName: String,
    @SerializedName("original_file_size")
    val originalFileSize: Long,
    @SerializedName("page_count")
    val pageCount: Int? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("ocr_used")
    val ocrUsed: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("message")
    val message: String? = null
)
