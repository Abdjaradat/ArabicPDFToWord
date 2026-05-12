package com.arabicpdftoword.app.core.util

import android.content.Context
import android.content.Intent
import android.os.Process
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrashHandler @Inject constructor(
    @ApplicationContext private val context: Context
) : Thread.UncaughtExceptionHandler {

    private val defaultHandler: Thread.UncaughtExceptionHandler? =
        Thread.getDefaultUncaughtExceptionHandler()

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            logCrashToFile(throwable)
            reportToCrashlytics(throwable)
        } catch (_: Exception) {
        } finally {
            defaultHandler?.uncaughtException(thread, throwable)
                ?: Process.killProcess(Process.myPid())
        }
    }

    private fun logCrashToFile(throwable: Throwable) {
        try {
            val crashDir = File(context.filesDir, "crashes")
            if (!crashDir.exists()) {
                crashDir.mkdirs()
            }

            val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US)
            val fileName = "crash_${dateFormat.format(Date())}.txt"
            val crashFile = File(crashDir, fileName)

            FileWriter(crashFile).use { writer ->
                writer.write("=== CRASH REPORT ===\n")
                writer.write("Date: ${Date()}\n")
                writer.write("App Version: ${context.packageManager.getPackageInfo(context.packageName, 0).versionName}\n")
                writer.write("Device: ${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}\n")
                writer.write("Android: ${android.os.Build.VERSION.RELEASE}\n\n")
                writer.write("Stack Trace:\n")

                val sw = StringWriter()
                val pw = PrintWriter(sw)
                throwable.printStackTrace(pw)
                pw.flush()
                writer.write(sw.toString())
            }
        } catch (_: Exception) {
        }
    }

    private fun reportToCrashlytics(throwable: Throwable) {
        try {
            FirebaseCrashlytics.getInstance().recordException(throwable)
        } catch (_: Exception) {
        }
    }

    fun getLastCrashLog(): String? {
        return try {
            val crashDir = File(context.filesDir, "crashes")
            if (!crashDir.exists()) return null
            val files = crashDir.listFiles() ?: return null
            val latestFile = files.maxByOrNull { it.lastModified() } ?: return null
            latestFile.readText()
        } catch (_: Exception) {
            null
        }
    }

    fun clearCrashLogs() {
        try {
            val crashDir = File(context.filesDir, "crashes")
            if (crashDir.exists()) {
                crashDir.listFiles()?.forEach { it.delete() }
            }
        } catch (_: Exception) {
        }
    }
}
