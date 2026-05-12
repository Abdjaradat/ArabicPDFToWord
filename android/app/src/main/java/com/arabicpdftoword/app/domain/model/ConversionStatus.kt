package com.arabicpdftoword.app.domain.model

enum class ConversionStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED;

    companion object {
        fun fromString(value: String): ConversionStatus {
            return when (value.lowercase()) {
                "pending" -> PENDING
                "processing", "in_progress" -> PROCESSING
                "completed", "done", "success" -> COMPLETED
                "failed", "error" -> FAILED
                else -> PENDING
            }
        }

        fun toString(status: ConversionStatus): String {
            return when (status) {
                PENDING -> "pending"
                PROCESSING -> "processing"
                COMPLETED -> "completed"
                FAILED -> "failed"
            }
        }
    }
}
