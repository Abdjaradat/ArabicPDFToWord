package com.arabicpdftoword.app.data.datasource

import com.arabicpdftoword.app.core.network.ApiService
import com.arabicpdftoword.app.data.dto.ConversionListResponse
import com.arabicpdftoword.app.data.dto.ConversionStatsResponse
import com.arabicpdftoword.app.data.dto.ConversionStatusResponse
import com.arabicpdftoword.app.data.dto.UploadResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class RemoteConversionDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun uploadPdf(file: File, language: String, quality: String = "normal"): Result<UploadResponse> {
        return try {
            val requestFile = file.readBytes().toRequestBody("application/pdf".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val languagePart = language.toRequestBody("text/plain".toMediaTypeOrNull())
            val qualityPart = quality.toRequestBody("text/plain".toMediaTypeOrNull())
            val response = apiService.uploadPdf(filePart, languagePart, qualityPart)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Upload failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConversionStatus(conversionId: String): Result<ConversionStatusResponse> {
        return try {
            val response = apiService.getConversionStatus(conversionId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Status check failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun downloadResult(conversionId: String): Result<ResponseBody> {
        return try {
            val response = apiService.downloadResult(conversionId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Download failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteConversion(conversionId: String): Result<Unit> {
        return try {
            val response = apiService.deleteConversion(conversionId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Delete failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getHistory(page: Int, size: Int): Result<ConversionListResponse> {
        return try {
            val response = apiService.getHistory(page, size)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("History fetch failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getStats(): Result<ConversionStatsResponse> {
        return try {
            val response = apiService.getStats()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Stats fetch failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
