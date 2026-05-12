package com.arabicpdftoword.app.domain.usecase

import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.domain.model.ConversionItem
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import javax.inject.Inject

class CheckConversionStatusUseCase @Inject constructor(
    private val repository: ConversionRepository
) {
    suspend operator fun invoke(conversionId: String): Resource<ConversionItem> {
        return repository.getConversionStatus(conversionId)
    }
}
