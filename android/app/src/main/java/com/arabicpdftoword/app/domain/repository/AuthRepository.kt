package com.arabicpdftoword.app.domain.repository

import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(email: String, password: String, fullName: String): Resource<User>
    suspend fun login(email: String, password: String): Resource<User>
    suspend fun logout()
    suspend fun refreshToken(): Resource<String>
    suspend fun getProfile(): Resource<User>
    suspend fun updateProfile(fullName: String): Resource<User>
    fun isLoggedIn(): Flow<Boolean>
    fun getCurrentUser(): Flow<User?>
}
