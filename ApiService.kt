package com.arabicpdftoword.app.core.network

import android.content.Context
import com.arabicpdftoword.app.data.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
                        .build()
                )
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

    fun uploadPdf(file: File, language: String): Flow<Result<UploadResponse>> {
        return flow {
            try {
                val requestFile = file.readBytes().toRequestBody("application/pdf".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
                val languagePart = language.toRequestBody("text/plain".toMediaTypeOrNull())

                apiService.uploadPdf(filePart, languagePart).collect { response ->
                    if (response.isSuccessful && response.body() != null) {
                        emit(Result.success(response.body()!!))
                    } else {
                        emit(Result.failure(ApiException(response.code(), response.message())))
                    }
                }
            } catch (e: Exception) {
                emit(Result.failure(ApiException(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getConversionStatus(conversionId: String): Flow<Result<ConversionStatusResponse>> {
        return flow {
            try {
                apiService.getConversionStatus(conversionId).collect { response ->
                    if (response.isSuccessful && response.body() != null) {
                        emit(Result.success(response.body()!!))
                    } else {
                        emit(Result.failure(ApiException(response.code(), response.message())))
                    }
                }
            } catch (e: Exception) {
                emit(Result.failure(ApiException(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun downloadResult(conversionId: String, outputFile: File): Flow<Result<File>> {
        return flow {
            try {
                apiService.downloadResult(conversionId).collect { response ->
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!
                        FileOutputStream(outputFile).use { outputStream ->
                            responseBody.byteStream().use { inputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }

                        emit(Result.success(outputFile))
                    } else {
                        emit(Result.failure(ApiException(response.code(), response.message())))
                    }
                }
            } catch (e: Exception) {
                emit(Result.failure(ApiException(e)))
            }
        }.flowOn(Dispatchers.IO)
    }
}

class ApiException(val code: Int, val message: String? = null) : RuntimeException(message)
