package com.arabicpdftoword.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.arabicpdftoword.app.core.ui.PdfToWordTheme
import com.arabicpdftoword.app.core.util.NoorPreferences
import com.arabicpdftoword.app.presentation.navigation.NavGraph
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: NoorPreferences

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            val splashScreen = installSplashScreen()
            super.onCreate(savedInstanceState)

            try { enableEdgeToEdge() } catch (e: Exception) { android.util.Log.e("MainActivity", "enableEdgeToEdge failed", e) }
            try { checkGooglePlayServices() } catch (e: Exception) { android.util.Log.e("MainActivity", "checkGooglePlayServices failed", e) }
            try { requestNotificationPermissionIfNeeded() } catch (e: Exception) { android.util.Log.e("MainActivity", "requestNotification failed", e) }
            try { checkInAppUpdate() } catch (e: Exception) { android.util.Log.e("MainActivity", "checkInAppUpdate failed", e) }

            setContent {
                val isDarkMode by preferences.darkMode.collectAsState(initial = false)
                val language by preferences.language.collectAsState(initial = "ar")

                PdfToWordTheme(darkTheme = isDarkMode) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        NavGraph()
                    }
                }
            }

            handleIntent(intent)

            android.util.Log.i("MainActivity", "onCreate completed successfully")
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "CRASH in onCreate", e)
            throw e
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.let { deepLink ->
            // Handle deep links
            android.util.Log.d("MainActivity", "Deep link: $deepLink")
        }
        intent?.getStringExtra("conversionId")?.let { conversionId ->
            // Handle notification intent
            android.util.Log.d("MainActivity", "Notification conversion: $conversionId")
        }
    }

    private fun checkGooglePlayServices() {
        val result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (result != com.google.android.gms.common.ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, result, 100)?.show()
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun checkInAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    info, AppUpdateType.IMMEDIATE, this, 200
                )
            }
        }
    }
}
