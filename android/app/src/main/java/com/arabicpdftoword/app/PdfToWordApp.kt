package com.arabicpdftoword.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.arabicpdftoword.app.core.util.CrashHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PdfToWordApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var crashHandler: CrashHandler

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    override fun onCreate() {
        super.onCreate()
        initializeFirebase()
        initializeAdMob()
    }

    private fun initializeFirebase() {
        try {
            com.google.firebase.FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            android.util.Log.w("PdfToWordApp", "Firebase init failed", e)
        }
    }

    private fun initializeAdMob() {
        try {
            com.google.android.gms.ads.MobileAds.initialize(this) { }
        } catch (_: Exception) {
        }
    }
}
