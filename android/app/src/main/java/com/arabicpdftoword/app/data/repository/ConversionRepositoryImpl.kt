package com.arabicpdftoword.app.data.repository

import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.core.util.FileUtils
import com.arabicpdftoword.app.data.datasource.LocalConversionDataSource
import com.arabicpdftoword.app.data.datasource.RemoteConversionDataSource
import com.arabicpdftoword.app.data.dto.ConversionStatsResponse
import com.arabicpdftoword.app.data.mapper.toDomainModel
import com.arabicpdftoword.app.data.mapper.toEntity
import com.arabicpdftoword.app.domain.model.ConversionItem
import com.arabicpdftoword.app.domain.model.ConversionStats
import com.arabicpdftoword.app.domain.model.ConversionStatus
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversionRepositoryImpl @Inject constructor(
    private val localDataSource: LocalConversionDataSource,
    private val remoteDataSource: RemoteConversionDataSource
) : ConversionRepository {

    override suspend fun uploadPdf(file: File, language: String): Resource<ConversionItem> {
        return try {
            val uploadResult = remoteDataSource.uploadPdf(file, language)
            if (uploadResult.isSuccess) {
                val uploadResponse = uploadResult.getOrThrow()
                val conversionItem = uploadResponse.toDomainModel().copy(
                    filePath = file.absolutePath,
                    language = language
                )
                localDataSource.insert(conversionItem)
                Resource.Success(conversionItem)
            } else {
                val error = uploadResult.exceptionOrNull()
                Resource.Error(error?.message ?: "فشل رفع الملف")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "خطأ في رفع الملف", null)
        }
    }

    override suspend fun getConversionStatus(conversionId: String): Resource<ConversionItem> {
        return try {
            val result = remoteDataSource.getConversionStatus(conversionId)
            if (result.isSuccess) {
                val statusResponse = result.getOrThrow()
                val conversionItem = statusResponse.toDomainModel()

                localDataSource.updateStatus(
                    conversionId = conversionId,
                    status = conversionItem.status,
                    errorMessage = conversionItem.errorMessage,
                    completedAt = conversionItem.completedAt
                )

                if (conversionItem.status == ConversionStatus.COMPLETED && conversionItem.outputFileName != null) {
                    localDataSource.markCompleted(
                        conversionId = conversionId,
                        fileName = conversionItem.outputFileName,
                        fileSize = conversionItem.outputFileSize,
                        filePath = null
                    )
                }

                Resource.Success(conversionItem)
            } else {
                val localItem = localDataSource.getByConversionId(conversionId)
                if (localItem != null) {
                    Resource.Success(localItem)
                } else {
                    val error = result.exceptionOrNull()
                    Resource.Error(error?.message ?: "فشل التحقق من حالة التحويل")
                }
            }
        } catch (e: Exception) {
            val localItem = localDataSource.getByConversionId(conversionId)
            if (localItem != null) {
                Resource.Success(localItem)
            } else {
                Resource.Error(e.message ?: "خطأ في التحقق من الحالة")
            }
        }
    }

    override suspend fun downloadResult(conversionId: String, outputFile: File): Resource<File> {
        return try {
            val result = remoteDataSource.downloadResult(conversionId)
            if (result.isSuccess) {
                val responseBody = result.getOrThrow()
                FileOutputStream(outputFile).use { outputStream ->
                    responseBody.byteStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                localDataSource.markCompleted(
                    conversionId = conversionId,
                    fileName = outputFile.name,
                    fileSize = outputFile.length(),
                    filePath = outputFile.absolutePath
                )

                Resource.Success(outputFile)
            } else {
                val error = result.exceptionOrNull()
                Resource.Error(error?.message ?: "فشل تحميل الملف")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "خطأ في تحميل الملف")
        }
    }

    override suspend fun deleteConversion(conversionId: String): Resource<Unit> {
        return try {
            val result = remoteDataSource.deleteConversion(conversionId)
            if (result.isSuccess) {
                localDataSource.deleteByConversionId(conversionId)
                Resource.Success(Unit)
            } else {
                val error = result.exceptionOrNull()
                Resource.Error(error?.message ?: "فشل حذف التحويل")
            }
        } catch (e: Exception) {
            localDataSource.deleteByConversionId(conversionId)
            Resource.Success(Unit)
        }
    }

    override fun getHistory(): Flow<List<ConversionItem>> {
        return localDataSource.getAllConversions()
    }

    override suspend fun refreshHistory(): Resource<List<ConversionItem>> {
        return try {
            val result = remoteDataSource.getHistory(page = 1, size = 50)
            if (result.isSuccess) {
                val historyResponse = result.getOrThrow()
                val items = historyResponse.data.map { it.toDomainModel() }

                for (item in items) {
                    val existing = localDataSource.getByConversionId(item.conversionId)
                    if (existing == null) {
                        localDataSource.insert(item)
                    } else if (item.status != existing.status) {
                        localDataSource.update(item)
                    }
                }

                Resource.Success(items)
            } else {
                val error = result.exceptionOrNull()
                Resource.Error(error?.message ?: "فشل تحديث التاريخ")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "خطأ في تحديث التاريخ")
        }
    }

    override suspend fun getStats(): Resource<ConversionStats> {
        return try {
            val result = remoteDataSource.getStats()
            if (result.isSuccess) {
                val statsResponse = result.getOrThrow()
                Resource.Success(statsResponse.toDomainModel())
            } else {
                val localCount = localDataSource.getCount()
                Resource.Success(
                    ConversionStats(
                        totalConversions = localCount,
                        todayConversions = localDataSource.getCountSince(
                            com.arabicpdftoword.app.core.util.DateUtils.getTodayStart()
                        )
                    )
                )
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "خطأ في جلب الإحصائيات")
        }
    }

    override suspend fun saveLocalConversion(item: ConversionItem) {
        try {
            val existing = localDataSource.getByConversionId(item.conversionId)
            if (existing != null) {
                localDataSource.update(item)
            } else {
                localDataSource.insert(item)
            }
        } catch (_: Exception) {
        }
    }

    override suspend fun deleteLocalConversion(conversionId: String) {
        try {
            localDataSource.deleteByConversionId(conversionId)
        } catch (_: Exception) {
        }
    }

    override suspend fun clearAllLocal() {
        try {
            localDataSource.clearAll()
        } catch (_: Exception) {
        }
    }

    private fun ConversionStatsResponse.toDomainModel(): ConversionStats {
        return ConversionStats(
            totalConversions = this.totalConversions,
            todayConversions = this.todayConversions,
            storageUsed = this.storageUsed,
            premiumConversions = this.premiumConversions
        )
    }
}
