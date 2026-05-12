package com.arabicpdftoword.app.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversions")
data class ConversionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "conversion_id")
    val conversionId: String,
    @ColumnInfo(name = "original_file_name")
    val originalFileName: String,
    @ColumnInfo(name = "original_file_size")
    val originalFileSize: Long,
    @ColumnInfo(name = "output_file_name")
    val outputFileName: String? = null,
    @ColumnInfo(name = "output_file_size")
    val outputFileSize: Long? = null,
    @ColumnInfo(name = "status")
    val status: String = "pending",
    @ColumnInfo(name = "page_count")
    val pageCount: Int? = null,
    @ColumnInfo(name = "ocr_used")
    val ocrUsed: Boolean = false,
    @ColumnInfo(name = "error_message")
    val errorMessage: String? = null,
    @ColumnInfo(name = "language")
    val language: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "completed_at")
    val completedAt: Long? = null,
    @ColumnInfo(name = "file_path")
    val filePath: String? = null,
    @ColumnInfo(name = "output_path")
    val outputPath: String? = null,
    @ColumnInfo(name = "is_premium")
    val isPremium: Boolean = false
)
