package com.arabicpdftoword.app.core.network

import com.arabicpdftoword.app.data.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("api/v1/convert/upload")
    suspend fun uploadPdf(
        @Part file: MultipartBody.Part,
        @Part("language") language: RequestBody,
        @Part("quality") quality: RequestBody
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
