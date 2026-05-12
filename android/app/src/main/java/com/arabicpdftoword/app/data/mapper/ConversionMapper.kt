package com.arabicpdftoword.app.data.mapper

import com.arabicpdftoword.app.core.database.entity.ConversionEntity
import com.arabicpdftoword.app.core.util.DateUtils
import com.arabicpdftoword.app.data.dto.ConversionStatusResponse
import com.arabicpdftoword.app.data.dto.UploadResponse
import com.arabicpdftoword.app.domain.model.ConversionItem
import com.arabicpdftoword.app.domain.model.ConversionStatus

fun UploadResponse.toDomainModel(): ConversionItem {
    return ConversionItem(
        conversionId = this.conversionId,
        originalFileName = this.originalFileName,
        originalFileSize = this.originalFileSize,
        status = ConversionStatus.fromString(this.status),
        pageCount = this.pageCount,
        ocrUsed = this.ocrUsed,
        language = this.language,
        createdAt = this.createdAt?.let { DateUtils.parseApiDate(it) } ?: System.currentTimeMillis()
    )
}

fun ConversionStatusResponse.toDomainModel(): ConversionItem {
    return ConversionItem(
        conversionId = this.conversionId,
        originalFileName = this.originalFileName ?: "",
        originalFileSize = this.originalFileSize ?: 0L,
        outputFileName = this.outputFileName,
        outputFileSize = this.outputFileSize,
        status = ConversionStatus.fromString(this.status),
        pageCount = this.pageCount,
        ocrUsed = this.ocrUsed,
        errorMessage = this.errorMessage,
        language = this.language,
        createdAt = this.createdAt?.let { DateUtils.parseApiDate(it) } ?: System.currentTimeMillis(),
        completedAt = this.completedAt?.let { DateUtils.parseApiDate(it) }
    )
}

fun ConversionItem.toEntity(): ConversionEntity {
    return ConversionEntity(
        id = this.id,
        conversionId = this.conversionId,
        originalFileName = this.originalFileName,
        originalFileSize = this.originalFileSize,
        outputFileName = this.outputFileName,
        outputFileSize = this.outputFileSize,
        status = ConversionStatus.toString(this.status),
        pageCount = this.pageCount,
        ocrUsed = this.ocrUsed,
        errorMessage = this.errorMessage,
        language = this.language,
        createdAt = this.createdAt,
        completedAt = this.completedAt,
        filePath = this.filePath,
        outputPath = this.outputPath
    )
}

fun ConversionEntity.toDomainModel(): ConversionItem {
    return ConversionItem(
        id = this.id,
        conversionId = this.conversionId,
        originalFileName = this.originalFileName,
        originalFileSize = this.originalFileSize,
        outputFileName = this.outputFileName,
        outputFileSize = this.outputFileSize,
        status = ConversionStatus.fromString(this.status),
        pageCount = this.pageCount,
        ocrUsed = this.ocrUsed,
        errorMessage = this.errorMessage,
        language = this.language,
        createdAt = this.createdAt,
        completedAt = this.completedAt,
        filePath = this.filePath,
        outputPath = this.outputPath
    )
}
