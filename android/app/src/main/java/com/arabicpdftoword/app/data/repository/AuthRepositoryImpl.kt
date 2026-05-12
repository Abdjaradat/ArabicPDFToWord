package com.arabicpdftoword.app.data.repository

import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.core.network.ApiService
import com.arabicpdftoword.app.core.util.NoorPreferences
import com.arabicpdftoword.app.data.dto.LoginRequest
import com.arabicpdftoword.app.data.dto.RefreshTokenRequest
import com.arabicpdftoword.app.data.dto.RegisterRequest
import com.arabicpdftoword.app.data.dto.UpdateProfileRequest
import com.arabicpdftoword.app.data.mapper.toDomainModel
import com.arabicpdftoword.app.data.mapper.toUserModel
import com.arabicpdftoword.app.domain.model.User
import com.arabicpdftoword.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val preferences: NoorPreferences
) : AuthRepository {

    override suspend fun register(email: String, password: String, fullName: String): Resource<User> {
        return try {
            val request = RegisterRequest(email = email, password = password, fullName = fullName)
            val response = apiService.register(request)
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                preferences.setAuthToken(authResponse.accessToken)
                preferences.setRefreshToken(authResponse.refreshToken)
                return fetchProfileFallback(email)
            } else {
                val errorBody = response.errorBody()?.string() ?: "فشل التسجيل"
                Resource.Error(errorBody)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "حدث خطأ في الاتصال")
        }
    }

    override suspend fun login(email: String, password: String): Resource<User> {
        return try {
            val request = LoginRequest(email = email, password = password)
            val response = apiService.login(request)
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                preferences.setAuthToken(authResponse.accessToken)
                preferences.setRefreshToken(authResponse.refreshToken)
                return fetchProfileFallback(email)
            } else {
                val errorBody = response.errorBody()?.string() ?: "فشل تسجيل الدخول"
                Resource.Error(errorBody)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "حدث خطأ في الاتصال")
        }
    }

    private suspend fun fetchProfileFallback(email: String): Resource<User> {
        return when (val profile = getProfile()) {
            is Resource.Success -> Resource.Success(profile.data)
            else -> {
                val user = User(id = "", email = email, fullName = "", isPremium = false)
                preferences.setUserId(user.id)
                preferences.setUserEmail(user.email)
                Resource.Success(user)
            }
        }
    }

    override suspend fun logout() {
        preferences.clearAuth()
    }

    override suspend fun refreshToken(): Resource<String> {
        return try {
            val currentRefreshToken = preferences.getRefreshTokenSync()
            if (currentRefreshToken.isNullOrBlank()) {
                preferences.clearAuth()
                return Resource.Error("لا يوجد رمز تحديث")
            }

            val request = RefreshTokenRequest(refreshToken = currentRefreshToken)
            val response = apiService.refreshToken(request)
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                preferences.setAuthToken(authResponse.accessToken)
                preferences.setRefreshToken(authResponse.refreshToken)
                Resource.Success(authResponse.accessToken)
            } else {
                preferences.clearAuth()
                Resource.Error("انتهت صلاحية الجلسة")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "فشل تحديث الرمز")
        }
    }

    override suspend fun getProfile(): Resource<User> {
        return try {
            val response = apiService.getProfile()
            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!.toDomainModel()
                preferences.setUserId(user.id)
                preferences.setUserEmail(user.email)
                Resource.Success(user)
            } else {
                Resource.Error("فشل جلب البيانات")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "حدث خطأ في الاتصال")
        }
    }

    override suspend fun updateProfile(fullName: String): Resource<User> {
        return try {
            val request = UpdateProfileRequest(fullName = fullName)
            val response = apiService.updateProfile(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.toDomainModel())
            } else {
                Resource.Error("فشل تحديث الملف الشخصي")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "حدث خطأ في الاتصال")
        }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return preferences.authToken.map { token ->
            !token.isNullOrBlank()
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        return preferences.authToken.map { token ->
            if (token.isNullOrBlank()) null
            else User(
                id = runBlockingGet(preferences.userId) ?: "",
                email = runBlockingGet(preferences.userEmail) ?: "",
                fullName = runBlockingGet(preferences.userFullName) ?: "",
                isPremium = runBlockingBoolean(preferences.isPremium)
            )
        }
    }

    private fun runBlockingGet(flow: Flow<String?>): String? {
        var value: String? = null
        kotlinx.coroutines.runBlocking {
            flow.collect { value = it; return@collect }
        }
        return value
    }

    private fun runBlockingBoolean(flow: Flow<Boolean>): Boolean {
        var value = false
        kotlinx.coroutines.runBlocking {
            flow.collect { value = it; return@collect }
        }
        return value
    }
}
