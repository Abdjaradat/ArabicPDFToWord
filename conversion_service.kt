package com.arabicpdftoword.app.core.service

import android.content.Context
import com.arabicpdftoword.app.data.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("api/v1/convert/upload")
    suspend fun uploadPdf(
        @Part file: MultipartBody.Part,
        @Part("language") language: RequestBody
    ): Response<UploadResponse>

    @GET("api/v1/convert/status/{id}")
    suspend fun getConversionStatus(
        @Path("id") conversionId: String
    ): Response<ConversionStatusResponse>

    @GET("api/v1/convert/download/{id}")
    @Streaming
    suspend fun downloadResult(
        @Path("id") conversionId: String
    ): Response<ResponseBody>

    @DELETE("api/v1/convert/{id}")
    suspend fun deleteConversion(
        @Path("id") conversionId: String
    ): Response<Unit>

    @GET("api/v1/convert/history")
    suspend fun getHistory(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): Response<ConversionListResponse>

    @GET("api/v1/convert/stats")
    suspend fun getStats(): Response<ConversionStatsResponse>

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): Response<AuthResponse>

    @POST("api/v1/auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<AuthResponse>

    @POST("api/v1/auth/refresh")
    suspend fun refreshToken(
        @Body body: RefreshTokenRequest
    ): Response<AuthResponse>

    @GET("api/v1/auth/me")
    suspend fun getProfile(): Response<UserResponse>

    @PUT("api/v1/auth/me")
    suspend fun updateProfile(
        @Body body: UpdateProfileRequest
    ): Response<UserResponse>
}

class ApiException(val code: Int, val message: String? = null) : RuntimeException(message)

class ConversionService(private val apiService: ApiService, private val context: Context) {

    suspend fun uploadPdf(file: File, language: String): Result<UploadResponse> {
        return try {
            val requestFile = file.readBytes().toRequestBody("application/pdf".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val languagePart = language.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = apiService.uploadPdf(filePart, languagePart)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(ApiException(response.code(), response.message()))
            }
        } catch (e: Exception) {
            Result.failure(ApiException(e))
        }
    }

    suspend fun getConversionStatus(conversionId: String): Result<ConversionStatusResponse> {
        return try {
            val response = apiService.getConversionStatus(conversionId)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(ApiException(response.code(), response.message()))
            }
        } catch (e: Exception) {
            Result.failure(ApiException(e))
        }
    }

    suspend fun downloadResult(conversionId: String, outputFile: File): Result<File> {
        return try {
            val response = apiService.downloadResult(conversionId)

            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                FileOutputStream(outputFile).use { outputStream ->
                    responseBody.byteStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                Result.success(outputFile)
            } else {
                Result.failure(ApiException(response.code(), response.message()))
            }
        } catch (e: Exception) {
            Result.failure(ApiException(e))
        }
    }

    suspend fun convertPdf(file: File, language: String): Result<String> {
        val uploadResult = uploadPdf(file, language)
        if (uploadResult.isSuccess) {
            val uploadResponse = uploadResult.getOrThrow()
            return try {
                // Integrate with AI converter for better text extraction
                val aiConverterUrl = "https://api.ai-converter.com/convert"
                val requestBody = MultipartBody.Builder()
                    .addFormDataPart("file", file.name, requestFile)
                    .build()

                val response = apiService.uploadPdf(requestBody, language)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(ApiException(response.code(), response.message()))
                }
            } catch (e: Exception) {
                Result.failure(ApiException(e))
            }
        } else {
            return uploadResult
        }
    }
}
