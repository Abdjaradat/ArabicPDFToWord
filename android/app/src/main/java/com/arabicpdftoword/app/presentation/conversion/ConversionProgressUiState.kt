package com.arabicpdftoword.app.presentation.conversion

import com.arabicpdftoword.app.domain.model.ConversionItem
import java.io.File

enum class ConversionStep(val label: String, val icon: String) {
    READING("قراءة الملف...", "\uD83D\uDCD6"),
    WRITING("كتابة المحتوى...", "\u270D\uFE0F"),
    FORMATTING("تطبيق التنسيق...", "\uD83C\uDFA8"),
    DONE("جاهز!", "\u2705"),
    IDLE("", "");

    companion object {
        fun fromApi(value: String?): ConversionStep = when (value) {
            "reading" -> READING
            "writing" -> WRITING
            "formatting" -> FORMATTING
            "done" -> DONE
            else -> IDLE
        }
    }
}

sealed interface ConversionProgressUiState {
    data object Idle : ConversionProgressUiState
    data class Processing(
        val progress: Int = 0,
        val status: String = "Starting...",
        val fileName: String = "",
        val step: ConversionStep = ConversionStep.READING
    ) : ConversionProgressUiState
    data class Completed(
        val conversionItem: ConversionItem,
        val outputFile: File? = null
    ) : ConversionProgressUiState
    data class Failed(val error: String) : ConversionProgressUiState
}
