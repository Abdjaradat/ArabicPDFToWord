package com.arabicpdftoword.app.core.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.arabicpdftoword.app.core.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREF_NAME)

@Singleton
class NoorPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_ID = stringPreferencesKey("user_id")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_FULL_NAME = stringPreferencesKey("user_full_name")
        val IS_PREMIUM = booleanPreferencesKey("is_premium")
        val PREMIUM_UNTIL = stringPreferencesKey("premium_until")
        val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        val DAILY_CONVERSION_COUNT = intPreferencesKey("daily_conversion_count")
        val LAST_CONVERSION_DATE = stringPreferencesKey("last_conversion_date")
        val AD_FREE = booleanPreferencesKey("ad_free")
        val LANGUAGE = stringPreferencesKey("language")
        val TOTAL_CONVERSIONS = intPreferencesKey("total_conversions")
        val AUTO_DELETE = booleanPreferencesKey("auto_delete")
        val SHARE_COUNT = intPreferencesKey("share_count")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        !prefs[Keys.AUTH_TOKEN].isNullOrBlank()
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        if (!loggedIn) clearAuth()
    }

    val autoDelete: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.AUTO_DELETE] ?: false
    }

    suspend fun setAutoDelete(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.AUTO_DELETE] = enabled
        }
    }

    val darkMode: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.DARK_MODE] ?: false
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.DARK_MODE] = enabled
        }
    }

    val authToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.AUTH_TOKEN]
    }

    fun getAuthTokenSync(): String? {
        return runBlocking {
            context.dataStore.data.first()[Keys.AUTH_TOKEN]
        }
    }

    suspend fun setAuthToken(token: String?) {
        context.dataStore.edit { prefs ->
            if (token != null) prefs[Keys.AUTH_TOKEN] = token
            else prefs.remove(Keys.AUTH_TOKEN)
        }
    }

    fun setAuthTokenSync(token: String?) {
        runBlocking {
            setAuthToken(token)
        }
    }

    val refreshToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.REFRESH_TOKEN]
    }

    fun getRefreshTokenSync(): String? {
        return runBlocking {
            context.dataStore.data.first()[Keys.REFRESH_TOKEN]
        }
    }

    suspend fun setRefreshToken(token: String?) {
        context.dataStore.edit { prefs ->
            if (token != null) prefs[Keys.REFRESH_TOKEN] = token
            else prefs.remove(Keys.REFRESH_TOKEN)
        }
    }

    fun setRefreshTokenSync(token: String?) {
        runBlocking {
            setRefreshToken(token)
        }
    }

    val userId: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.USER_ID]
    }

    suspend fun setUserId(id: String?) {
        context.dataStore.edit { prefs ->
            if (id != null) prefs[Keys.USER_ID] = id
            else prefs.remove(Keys.USER_ID)
        }
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.USER_EMAIL]
    }

    suspend fun setUserEmail(email: String?) {
        context.dataStore.edit { prefs ->
            if (email != null) prefs[Keys.USER_EMAIL] = email
            else prefs.remove(Keys.USER_EMAIL)
        }
    }

    val userFullName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.USER_FULL_NAME]
    }

    suspend fun setUserFullName(name: String?) {
        context.dataStore.edit { prefs ->
            if (name != null) prefs[Keys.USER_FULL_NAME] = name
            else prefs.remove(Keys.USER_FULL_NAME)
        }
    }

    val isPremium: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.IS_PREMIUM] ?: false
    }

    suspend fun setIsPremium(premium: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_PREMIUM] = premium
        }
    }

    val isFirstLaunch: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.IS_FIRST_LAUNCH] ?: true
    }

    suspend fun setFirstLaunch(isFirst: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_FIRST_LAUNCH] = isFirst
        }
    }

    val dailyConversionCount: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[Keys.DAILY_CONVERSION_COUNT] ?: 0
    }

    suspend fun setDailyConversionCount(count: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.DAILY_CONVERSION_COUNT] = count
        }
    }

    val lastConversionDate: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.LAST_CONVERSION_DATE]
    }

    suspend fun setLastConversionDate(date: String?) {
        context.dataStore.edit { prefs ->
            if (date != null) prefs[Keys.LAST_CONVERSION_DATE] = date
            else prefs.remove(Keys.LAST_CONVERSION_DATE)
        }
    }

    val adFree: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.AD_FREE] ?: false
    }

    suspend fun setAdFree(adFree: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.AD_FREE] = adFree
        }
    }

    val language: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[Keys.LANGUAGE] ?: "ar"
    }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.LANGUAGE] = lang
        }
    }

    val totalConversions: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[Keys.TOTAL_CONVERSIONS] ?: 0
    }

    suspend fun setTotalConversions(count: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.TOTAL_CONVERSIONS] = count
        }
    }

    val shareCount: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[Keys.SHARE_COUNT] ?: 0
    }

    suspend fun setShareCount(count: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.SHARE_COUNT] = count
        }
    }

    suspend fun clearAuth() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.AUTH_TOKEN)
            prefs.remove(Keys.REFRESH_TOKEN)
            prefs.remove(Keys.USER_ID)
            prefs.remove(Keys.USER_EMAIL)
            prefs.remove(Keys.USER_FULL_NAME)
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
