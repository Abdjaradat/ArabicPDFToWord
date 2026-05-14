package com.arabicpdftoword.app.domain.repository

import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.domain.model.ConversionItem
import com.arabicpdftoword.app.domain.model.ConversionStats
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ConversionRepository {
    suspend fun uploadPdf(file: File, language: String, quality: String = "normal"): Resource<ConversionItem>
    suspend fun getConversionStatus(conversionId: String): Resource<ConversionItem>
    suspend fun downloadResult(conversionId: String, outputFile: File): Resource<File>
    suspend fun deleteConversion(conversionId: String): Resource<Unit>
    fun getHistory(): Flow<List<ConversionItem>>
    suspend fun refreshHistory(): Resource<List<ConversionItem>>
    suspend fun getStats(): Resource<ConversionStats>
    suspend fun saveLocalConversion(item: ConversionItem)
    suspend fun deleteLocalConversion(conversionId: String)
    suspend fun clearAllLocal()
}
