package com.arabicpdftoword.app.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arabicpdftoword.app.core.util.NoorPreferences
import com.arabicpdftoword.app.domain.model.ConversionStatus
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: ConversionRepository,
    private val prefs: NoorPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val PAGE_SIZE = 20
    private var searchJob: Job? = null

    init {
        loadConversions()
    }

    fun loadConversions() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getHistory().collect { allConversions ->
                    val filtered = filterConversions(allConversions)
                    _uiState.update {
                        it.copy(
                            conversions = filtered,
                            isLoading = false,
                            hasMorePages = filtered.size >= PAGE_SIZE
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun filterConversions(conversions: List<com.arabicpdftoword.app.domain.model.ConversionItem>): List<com.arabicpdftoword.app.domain.model.ConversionItem> {
        val query = _uiState.value.searchQuery
        val filter = _uiState.value.filter
        return conversions.filter { item ->
            val matchesQuery = query.isNullOrBlank() || item.originalFileName.lowercase().contains(query.lowercase())
            val matchesFilter = filter == null || filter == "all" ||
                when (filter) {
                    "COMPLETED" -> item.status == ConversionStatus.COMPLETED
                    "FAILED" -> item.status == ConversionStatus.FAILED
                    else -> true
                }
            matchesQuery && matchesFilter
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                loadConversions()
                _uiState.update { it.copy(isRefreshing = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isRefreshing = false, error = e.message) }
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            try {
                repository.clearAllLocal()
                loadConversions()
            } catch (_: Exception) {}
        }
    }

    fun deleteConversion(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteConversion(id)
            } catch (_: Exception) {}
        }
    }

    fun setFilter(filter: String?) {
        _uiState.update { it.copy(filter = filter) }
        loadConversions()
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            kotlinx.coroutines.delay(300)
            loadConversions()
        }
    }

    fun loadMore() {
        val state = _uiState.value
        if (!state.hasMorePages || state.isLoading) return
        _uiState.update { it.copy(currentPage = state.currentPage + 1) }
        loadConversions()
    }
}
