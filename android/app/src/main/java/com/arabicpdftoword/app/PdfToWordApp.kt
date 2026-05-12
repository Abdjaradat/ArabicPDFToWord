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
        // crashHandler registers itself in init block
        initializeFirebase()
        initializeLeakCanary()
        initializeAdMob()
    }

    private fun initializeFirebase() {
        try {
            com.google.firebase.FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            android.util.Log.w("PdfToWordApp", "Firebase init failed", e)
        }
    }

    private fun initializeLeakCanary() {
        if (BuildConfig.DEBUG) {
            try {
                val leakCanary = Class.forName("leakcanary.LeakCanary")
                leakCanary.getMethod("install", Application::class.java)
                    .invoke(null, this)
            } catch (_: Exception) {
            }
        }
    }

    private fun initializeAdMob() {
        Thread {
            try {
                com.google.android.gms.ads.MobileAds.initialize(this) { }
            } catch (_: Exception) {
            }
        }.start()
    }
}
