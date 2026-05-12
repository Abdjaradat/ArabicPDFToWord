package com.arabicpdftoword.app.core.common

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import java.io.File
import java.text.DecimalFormat
import java.util.Locale

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun File.toUri(context: Context): Uri {
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        this
    )
}

fun Long.formatFileSize(): String {
    val bytes = this.toDouble()
    return when {
        bytes < 1024 -> "$bytes B"
        bytes < 1024 * 1024 -> String.format(Locale.US, "%.1f KB", bytes / 1024)
        bytes < 1024 * 1024 * 1024 -> String.format(Locale.US, "%.1f MB", bytes / (1024 * 1024))
        else -> String.format(Locale.US, "%.1f GB", bytes / (1024 * 1024 * 1024))
    }
}

fun String.isRtl(): Boolean {
    return this.any { c ->
        val direction = Character.getDirectionality(c)
        direction == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC ||
        direction == Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING ||
        direction == Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE
    }
}

fun String.toArabicDigits(): String {
    val arabicDigits = charArrayOf('\u0660', '\u0661', '\u0662', '\u0663', '\u0664', '\u0665', '\u0666', '\u0667', '\u0668', '\u0669')
    return this.map { c ->
        if (c in '0'..'9') arabicDigits[c - '0']
        else c
    }.joinToString("")
}

fun <T> List<T>.toStateFlow(): MutableStateFlow<List<T>> {
    return MutableStateFlow(this)
}

fun <T> MutableStateFlow<List<T>>.updateList(transform: (List<T>) -> List<T>) {
    this.value = transform(this.value)
}
