package com.arabicpdftoword.app.data.dto

import com.google.gson.annotations.SerializedName

data class ConversionStatusResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("conversion_id")
    val conversionId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("original_file_name")
    val originalFileName: String? = null,
    @SerializedName("original_file_size")
    val originalFileSize: Long? = null,
    @SerializedName("output_file_name")
    val outputFileName: String? = null,
    @SerializedName("output_file_size")
    val outputFileSize: Long? = null,
    @SerializedName("page_count")
    val pageCount: Int? = null,
    @SerializedName("ocr_used")
    val ocrUsed: Boolean = false,
    @SerializedName("error_message")
    val errorMessage: String? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("completed_at")
    val completedAt: String? = null,
    @SerializedName("progress")
    val progress: Int? = null,
    @SerializedName("step")
    val step: String? = null
)
