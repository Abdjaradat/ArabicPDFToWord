package com.arabicpdftoword.app.data.repository

import com.arabicpdftoword.app.core.util.NoorPreferences
import com.arabicpdftoword.app.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val preferences: NoorPreferences
) : PreferencesRepository {

    override val isDarkMode: Flow<Boolean>
        get() = preferences.darkMode

    override suspend fun setDarkMode(enabled: Boolean) {
        preferences.setDarkMode(enabled)
    }

    override val isFirstLaunch: Flow<Boolean>
        get() = preferences.isFirstLaunch

    override suspend fun setFirstLaunched() {
        preferences.setFirstLaunch(false)
    }

    override val language: Flow<String>
        get() = preferences.language

    override suspend fun setLanguage(lang: String) {
        preferences.setLanguage(lang)
    }
}
