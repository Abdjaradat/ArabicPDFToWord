package com.arabicpdftoword.app.domain.usecase

import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import javax.inject.Inject

class DeleteConversionUseCase @Inject constructor(
    private val repository: ConversionRepository
) {
    suspend operator fun invoke(conversionId: String, deleteFromServer: Boolean = true): Resource<Unit> {
        if (deleteFromServer) {
            val result = repository.deleteConversion(conversionId)
            if (result is Resource.Error) {
                repository.deleteLocalConversion(conversionId)
                return result
            }
        }
        repository.deleteLocalConversion(conversionId)
        return Resource.Success(Unit)
    }
}
