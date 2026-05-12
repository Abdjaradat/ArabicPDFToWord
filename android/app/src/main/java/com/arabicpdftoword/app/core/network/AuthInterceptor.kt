package com.arabicpdftoword.app.core.network

import com.arabicpdftoword.app.core.util.NoorPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val preferences: NoorPreferences
) : Interceptor {

    private val noAuthPaths = listOf(
        "/api/v1/convert/upload",
        "/api/v1/convert/status/",
        "/api/v1/convert/download/",
        "/api/v1/auth/register",
        "/api/v1/auth/login"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val path = originalRequest.url.encodedPath

        val needsAuth = noAuthPaths.none { path.contains(it) }

        val token = runBlocking {
            preferences.getAuthTokenSync()
        }

        if (token.isNullOrBlank() || !needsAuth) {
            return chain.proceed(originalRequest)
        }

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .header("Accept", "application/json")
            .build()

        val response = chain.proceed(authenticatedRequest)

        if (response.code == 401) {
            response.close()
            val newToken = tryRefreshToken()
            if (newToken != null) {
                val retryRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .header("Accept", "application/json")
                    .build()
                return chain.proceed(retryRequest)
            }
        }

        return response
    }

    private fun tryRefreshToken(): String? {
        return runBlocking {
            val savedRefreshToken = preferences.getRefreshTokenSync()
            if (savedRefreshToken.isNullOrBlank()) {
                preferences.clearAuth()
                return@runBlocking null
            }
            try {
                val api = RetrofitClient.create()
                val requestBody = com.arabicpdftoword.app.data.dto.RefreshTokenRequest(savedRefreshToken)
                val apiResponse = api.refreshToken(requestBody)
                if (apiResponse.isSuccessful) {
                    val authResponse = apiResponse.body()
                    if (authResponse != null) {
                        preferences.setAuthTokenSync(authResponse.accessToken)
                        preferences.setRefreshTokenSync(authResponse.refreshToken)
                        return@runBlocking authResponse.accessToken
                    }
                } else {
                    preferences.clearAuth()
                }
            } catch (_: Exception) {
                // Network error, return null
            }
            return@runBlocking null
        }
    }
}
