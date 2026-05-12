package com.arabicpdftoword.app.domain.usecase

import com.arabicpdftoword.app.core.common.Constants
import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.domain.model.ConversionItem
import com.arabicpdftoword.app.domain.repository.ConversionRepository
import java.io.File
import javax.inject.Inject

class UploadPdfUseCase @Inject constructor(
    private val repository: ConversionRepository
) {
    suspend operator fun invoke(file: File, language: String, isPremium: Boolean = false): Resource<ConversionItem> {
        if (!file.exists()) {
            return Resource.Error("الملف غير موجود")
        }

        val fileSizeMB = file.length() / (1024 * 1024)
        if (fileSizeMB > Constants.MAX_FILE_SIZE_MB) {
            return Resource.Error("حجم الملف يتجاوز الحد الأقصى المسموح به (${Constants.MAX_FILE_SIZE_MB} MB)")
        }

        return repository.uploadPdf(file, language)
    }
}
