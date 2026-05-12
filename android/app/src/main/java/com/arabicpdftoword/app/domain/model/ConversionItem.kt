package com.arabicpdftoword.app.domain.model

data class ConversionItem(
    val id: Long = 0,
    val conversionId: String,
    val originalFileName: String,
    val originalFileSize: Long,
    val outputFileName: String? = null,
    val outputFileSize: Long? = null,
    val status: ConversionStatus = ConversionStatus.PENDING,
    val pageCount: Int? = null,
    val ocrUsed: Boolean = false,
    val errorMessage: String? = null,
    val language: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val filePath: String? = null,
    val outputPath: String? = null
)
