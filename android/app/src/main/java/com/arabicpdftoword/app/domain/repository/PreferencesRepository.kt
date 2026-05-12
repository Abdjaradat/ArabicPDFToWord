package com.arabicpdftoword.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val isDarkMode: Flow<Boolean>
    suspend fun setDarkMode(enabled: Boolean)
    val isFirstLaunch: Flow<Boolean>
    suspend fun setFirstLaunched()
    val language: Flow<String>
    suspend fun setLanguage(lang: String)
}
