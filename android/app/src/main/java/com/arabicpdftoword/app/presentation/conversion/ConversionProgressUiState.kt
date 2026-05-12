package com.arabicpdftoword.app.presentation.conversion

import com.arabicpdftoword.app.domain.model.ConversionItem
import java.io.File

sealed interface ConversionProgressUiState {
    data object Idle : ConversionProgressUiState
    data class Processing(
        val progress: Int = 0,
        val status: String = "Starting...",
        val fileName: String = ""
    ) : ConversionProgressUiState
    data class Completed(
        val conversionItem: ConversionItem,
        val outputFile: File? = null
    ) : ConversionProgressUiState
    data class Failed(val error: String) : ConversionProgressUiState
}
