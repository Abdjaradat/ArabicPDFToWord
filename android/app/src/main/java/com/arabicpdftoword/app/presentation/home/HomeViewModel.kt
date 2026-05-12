package com.arabicpdftoword.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arabicpdftoword.app.core.common.Constants
import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.core.util.NoorPreferences
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ConversionRepository,
    private val prefs: NoorPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
        observePreferences()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            combine(
                prefs.isPremium,
                prefs.isLoggedIn
            ) { isPremium, loggedIn ->
                _uiState.update { it.copy(isPremium = isPremium, isLoggedIn = loggedIn) }
            }.collect()
        }
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getHistory().collect { conversions ->
                    val todayStart = getTodayStartMillis()
                    val todayCount = conversions.count { it.createdAt >= todayStart }
                    _uiState.update {
                        it.copy(
                            recentConversions = conversions,
                            todayConversions = todayCount,
                            totalConversions = conversions.size,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                val result = repository.refreshHistory()
                when (result) {
                    is Resource.Success -> {
                        val conversions = result.data ?: emptyList()
                        val todayStart = getTodayStartMillis()
                        val todayCount = conversions.count { it.createdAt >= todayStart }
                        _uiState.update {
                            it.copy(
                                recentConversions = conversions,
                                todayConversions = todayCount,
                                totalConversions = conversions.size,
                                isRefreshing = false
                            )
                        }
                    }
                    else -> {
                        _uiState.update { it.copy(isRefreshing = false) }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isRefreshing = false, error = e.message) }
            }
        }
    }

    fun deleteConversion(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteConversion(id)
            } catch (_: Exception) {}
        }
    }

    private fun getTodayStartMillis(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }
}
