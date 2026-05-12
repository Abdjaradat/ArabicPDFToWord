package com.arabicpdftoword.app.core.network

import android.content.Context
import com.arabicpdftoword.app.core.network.RetryInterceptor
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) {
    companion object {
        private const val BASE_URL = "https://arabic-pdf-to-word-api-1.onrender.com/"
        private const val CONNECT_TIMEOUT = 30L
        private const val READ_TIMEOUT = 60L

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .addInterceptor(RetryInterceptor())
                        .build()
                )
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

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
}

class ApiException(val code: Int, val message: String? = null) : RuntimeException(message)
