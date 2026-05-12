package com.arabicpdftoword.app.data.datasource

import com.arabicpdftoword.app.core.database.dao.ConversionDao
import com.arabicpdftoword.app.core.database.entity.ConversionEntity
import com.arabicpdftoword.app.domain.model.ConversionItem
import com.arabicpdftoword.app.domain.model.ConversionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalConversionDataSource @Inject constructor(
    private val conversionDao: ConversionDao
) {
    fun getAllConversions(): Flow<List<ConversionItem>> {
        return conversionDao.getAllConversions().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun getByConversionId(conversionId: String): ConversionItem? {
        return conversionDao.getByConversionId(conversionId)?.toDomainModel()
    }

    suspend fun insert(conversionItem: ConversionItem): Long {
        return conversionDao.insert(conversionItem.toEntity())
    }

    suspend fun update(conversionItem: ConversionItem) {
        conversionDao.update(conversionItem.toEntity())
    }

    suspend fun delete(conversionItem: ConversionItem) {
        conversionDao.delete(conversionItem.toEntity())
    }

    suspend fun deleteByConversionId(conversionId: String) {
        conversionDao.deleteByConversionId(conversionId)
    }

    suspend fun clearAll() {
        conversionDao.clearAll()
    }

    suspend fun getCount(): Int {
        return conversionDao.getCount()
    }

    suspend fun getCountSince(timestamp: Long): Int {
        return conversionDao.getCountSince(timestamp)
    }

    suspend fun updateStatus(
        conversionId: String,
        status: ConversionStatus,
        errorMessage: String? = null,
        completedAt: Long? = null
    ) {
        conversionDao.updateStatus(
            conversionId = conversionId,
            status = ConversionStatus.toString(status),
            errorMessage = errorMessage,
            completedAt = completedAt
        )
    }

    suspend fun markCompleted(
        conversionId: String,
        fileName: String?,
        fileSize: Long?,
        filePath: String?
    ) {
        conversionDao.markCompleted(
            conversionId = conversionId,
            fileName = fileName,
            fileSize = fileSize,
            filePath = filePath
        )
    }

    private fun ConversionItem.toEntity(): ConversionEntity {
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

    private fun ConversionEntity.toDomainModel(): ConversionItem {
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
}
