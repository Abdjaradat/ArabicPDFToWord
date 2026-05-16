package com.arabicpdftoword.app.core.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileUtils {

    fun getPdfFilesFromUri(context: Context, uri: Uri): List<File> {
        val files = mutableListOf<File>()
        try {
            val fileName = getFileName(context, uri) ?: "document_${System.currentTimeMillis()}.pdf"
            val file = copyFileToCache(context, uri, fileName)
            if (file.exists()) {
                files.add(file)
            }
        } catch (_: Exception) {
        }
        return files
    }

    fun copyFileToCache(context: Context, uri: Uri, fileName: String): File {
        val cacheDir = File(context.cacheDir, "pdf_uploads")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }

        val outputFile = File(cacheDir, fileName)
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(outputFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return outputFile
    }

    fun deleteFile(file: File): Boolean {
        return try {
            if (file.exists()) {
                file.delete()
            } else true
        } catch (_: Exception) {
            false
        }
    }

    fun getFileName(context: Context, uri: Uri): String? {
        var name: String? = null
        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) {
                        name = cursor.getString(nameIndex)
                    }
                }
            }
        }
        if (name == null) {
            name = uri.path?.substringAfterLast('/') ?: "document_${System.currentTimeMillis()}.pdf"
        }
        return name
    }

    fun getFileSize(context: Context, uri: Uri): Long {
        var size = 0L
        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (sizeIndex >= 0) {
                        size = cursor.getLong(sizeIndex)
                    }
                }
            }
        }
        if (size == 0L) {
            try {
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    size = stream.available().toLong()
                }
            } catch (_: Exception) {
            }
        }
        return size
    }

    fun getMimeType(uri: Uri): String {
        return uri.path?.let { path ->
            when {
                path.endsWith(".pdf", ignoreCase = true) -> "application/pdf"
                path.endsWith(".doc", ignoreCase = true) -> "application/msword"
                path.endsWith(".docx", ignoreCase = true) -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                path.endsWith(".txt", ignoreCase = true) -> "text/plain"
                path.endsWith(".png", ignoreCase = true) -> "image/png"
                path.endsWith(".jpg", ignoreCase = true) || path.endsWith(".jpeg", ignoreCase = true) -> "image/jpeg"
                else -> "application/octet-stream"
            }
        } ?: "application/octet-stream"
    }

    fun createTempFile(context: Context, fileName: String): File {
        val tempDir = File(context.cacheDir, "temp")
        if (!tempDir.exists()) {
            tempDir.mkdirs()
        }
        return File(tempDir, fileName)
    }

    fun clearTempDirectory(context: Context) {
        val tempDir = File(context.cacheDir, "temp")
        if (tempDir.exists()) {
            tempDir.listFiles()?.forEach { file ->
                file.delete()
            }
        }
        val pdfUploadDir = File(context.cacheDir, "pdf_uploads")
        if (pdfUploadDir.exists()) {
            pdfUploadDir.listFiles()?.forEach { file ->
                file.delete()
            }
        }
        val downloadDir = File(context.cacheDir, "downloads")
        if (downloadDir.exists()) {
            downloadDir.listFiles()?.forEach { file ->
                file.delete()
            }
        }
    }

    fun getDownloadsDirectory(): File {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    }
}
