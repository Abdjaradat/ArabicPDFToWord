package com.arabicpdftoword.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arabicpdftoword.app.core.network.ApiService
import com.arabicpdftoword.app.core.util.CrashHandler
import com.arabicpdftoword.app.core.util.NoorPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val crashLog: String? = null,
    val navigateTo: SplashDestination? = null
)

sealed class SplashDestination {
    data object GoToHome : SplashDestination()
    data object GoToLogin : SplashDestination()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefs: NoorPreferences,
    private val crashHandler: CrashHandler,
    private val apiService: ApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkAppState()
    }

    private fun checkAppState() {
        viewModelScope.launch {
            delay(1500)
            val isLoggedIn = prefs.authToken.first() != null
            val crashLog = crashHandler.getLastCrashLog()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = isLoggedIn,
                    crashLog = crashLog
                )
            }

            delay(500)

            if (crashLog != null) {
                return@launch
            }

            navigateBasedOnAuth()
        }
    }

    private suspend fun navigateBasedOnAuth() {
        val isLoggedIn = prefs.authToken.first() != null
        _uiState.update { it.copy(isLoggedIn = isLoggedIn) }

        if (!isLoggedIn) {
            prefs.clearAuth()
            _uiState.update { it.copy(navigateTo = SplashDestination.GoToLogin) }
        } else {
            try {
                val profileResponse = apiService.getProfile()
                if (profileResponse.isSuccessful) {
                    _uiState.update { it.copy(navigateTo = SplashDestination.GoToHome) }
                } else {
                    prefs.clearAuth()
                    _uiState.update { it.copy(navigateTo = SplashDestination.GoToLogin) }
                }
            } catch (e: Exception) {
                prefs.clearAuth()
                _uiState.update { it.copy(navigateTo = SplashDestination.GoToLogin) }
            }
        }
    }

    fun onCrashDialogDismissed() {
        crashHandler.clearCrashLogs()
        viewModelScope.launch {
            navigateBasedOnAuth()
        }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateTo = null) }
    }
}