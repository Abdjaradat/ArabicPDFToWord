package com.arabicpdftoword.app.core.network

import com.arabicpdftoword.app.core.common.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetryInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null
        var attempt = 0

        while (attempt < Constants.RETRY_MAX_ATTEMPTS) {
            try {
                response = chain.proceed(request)
                if (response.isSuccessful) {
                    return response
                }
                if (!shouldRetry(response.code)) {
                    return response
                }
            } catch (e: Exception) {
                if (attempt >= Constants.RETRY_MAX_ATTEMPTS - 1) {
                    throw e
                }
            }

            attempt++
            if (attempt < Constants.RETRY_MAX_ATTEMPTS) {
                val delayMs = Constants.RETRY_BASE_DELAY_MS * (1L shl attempt)
                Thread.sleep(delayMs)
            }
        }

        return response ?: chain.proceed(request)
    }

    private fun shouldRetry(code: Int): Boolean {
        return code >= 500 || code == 429
    }
}
