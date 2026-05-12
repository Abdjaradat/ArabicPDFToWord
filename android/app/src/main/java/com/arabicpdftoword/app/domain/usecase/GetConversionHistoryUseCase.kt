package com.arabicpdftoword.app.domain.usecase

import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.domain.model.ConversionItem
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConversionHistoryUseCase @Inject constructor(
    private val repository: ConversionRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): Flow<List<ConversionItem>> {
        if (forceRefresh) {
            repository.refreshHistory()
        }
        return repository.getHistory()
    }
}
