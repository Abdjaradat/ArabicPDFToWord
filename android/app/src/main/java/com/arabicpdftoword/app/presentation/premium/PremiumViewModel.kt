package com.arabicpdftoword.app.presentation.premium

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arabicpdftoword.app.core.util.NoorPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PremiumViewModel @Inject constructor(
    private val prefs: NoorPreferences,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(PremiumUiState())
    val uiState: StateFlow<PremiumUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingProducts = true) }
            try {
                _uiState.update {
                    it.copy(
                        monthlyPrice = "$9.99/month",
                        yearlyPrice = "$49.99/year",
                        yearlySavings = "Save 58%",
                        isLoadingProducts = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingProducts = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun purchaseMonthly(activity: Activity) {
        viewModelScope.launch {
            _uiState.update { it.copy(isPurchasing = true, error = null) }
            try {
                prefs.setIsPremium(true)
                _uiState.update {
                    it.copy(isPurchasing = false, isPremium = true, purchaseSuccess = true)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isPurchasing = false, error = e.message ?: "Purchase failed")
                }
            }
        }
    }

    fun purchaseYearly(activity: Activity) {
        viewModelScope.launch {
            _uiState.update { it.copy(isPurchasing = true, error = null) }
            try {
                prefs.setIsPremium(true)
                _uiState.update {
                    it.copy(isPurchasing = false, isPremium = true, purchaseSuccess = true)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isPurchasing = false, error = e.message ?: "Purchase failed")
                }
            }
        }
    }

    fun restorePurchases() {
        viewModelScope.launch {
            _uiState.update { it.copy(isPurchasing = true, error = null) }
            try {
                prefs.setIsPremium(false)
                _uiState.update {
                    it.copy(isPurchasing = false, error = "No previous purchases found")
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isPurchasing = false, error = e.message ?: "Restore failed")
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
