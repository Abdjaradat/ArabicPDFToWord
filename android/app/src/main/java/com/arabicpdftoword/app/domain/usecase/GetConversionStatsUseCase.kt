package com.arabicpdftoword.app.domain.usecase

import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.domain.model.ConversionStats
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import javax.inject.Inject

class GetConversionStatsUseCase @Inject constructor(
    private val repository: ConversionRepository
) {
    suspend operator fun invoke(): Resource<ConversionStats> {
        return repository.getStats()
    }
}
