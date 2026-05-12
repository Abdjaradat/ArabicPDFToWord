package com.arabicpdftoword.app.core.util

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class WorkManagerHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val FILE_CLEANUP_WORK_NAME = "file_cleanup"
        private const val CONVERSION_WORK_NAME_PREFIX = "conversion_"
    }

    fun scheduleFileCleanup() {
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()

        val cleanupRequest = PeriodicWorkRequestBuilder<FileCleanupWorker>(
            1, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .addTag(FILE_CLEANUP_WORK_NAME)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            FILE_CLEANUP_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            cleanupRequest
        )
    }

    fun scheduleConversionWorker(
        conversionId: String,
        filePath: String,
        language: String
    ) {
        val inputData = Data.Builder()
            .putString("conversion_id", conversionId)
            .putString("file_path", filePath)
            .putString("language", language)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val conversionRequest = OneTimeWorkRequestBuilder<ConversionWorker>()
            .setInputData(inputData)
            .setConstraints(constraints)
            .addTag("$CONVERSION_WORK_NAME_PREFIX$conversionId")
            .build()

        WorkManager.getInstance(context).enqueue(conversionRequest)
    }

    fun observeConversionWork(conversionId: String): Flow<WorkInfo> {
        val workTag = "$CONVERSION_WORK_NAME_PREFIX$conversionId"
        return WorkManager.getInstance(context)
            .getWorkInfosByTagFlow(workTag)
            .map { workInfos ->
                workInfos.firstOrNull() ?: WorkInfo(
                    java.util.UUID.randomUUID(),
                    WorkInfo.State.SUCCEEDED,
                    emptySet<String>(),
                    Data.EMPTY,
                    Data.EMPTY,
                    0
                )
            }
    }
}

class FileCleanupWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            FileUtils.clearTempDirectory(applicationContext)
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}

class ConversionWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            val conversionId = inputData.getString("conversion_id") ?: return Result.failure()
            val filePath = inputData.getString("file_path") ?: return Result.failure()
            val language = inputData.getString("language") ?: "ar"

            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}
