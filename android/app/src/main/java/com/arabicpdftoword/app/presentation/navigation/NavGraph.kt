package com.arabicpdftoword.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arabicpdftoword.app.presentation.about.AboutScreen
import com.arabicpdftoword.app.presentation.conversion.ConversionProgressScreen
import com.arabicpdftoword.app.presentation.filepicker.FilePickerScreen
import com.arabicpdftoword.app.presentation.history.HistoryScreen
import com.arabicpdftoword.app.presentation.home.HomeScreen
import com.arabicpdftoword.app.presentation.login.LoginScreen
import com.arabicpdftoword.app.presentation.premium.PremiumScreen
import com.arabicpdftoword.app.presentation.privacy.PrivacyPolicyScreen
import com.arabicpdftoword.app.presentation.settings.SettingsScreen
import com.arabicpdftoword.app.presentation.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val FILE_PICKER = "file_picker"
    const val CONVERSION_PROGRESS = "conversion_progress/{conversionId}"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
    const val LOGIN = "login"
    const val PREMIUM = "premium"
    const val ABOUT = "about"
    const val PRIVACY_POLICY = "privacy_policy"

    fun conversionProgress(id: String) = "conversion_progress/$id"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToFilePicker = { navController.navigate(Routes.FILE_PICKER) },
                onNavigateToHistory = { navController.navigate(Routes.HISTORY) },
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) },
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) },
                onNavigateToPremium = { navController.navigate(Routes.PREMIUM) },
                onNavigateToAbout = { navController.navigate(Routes.ABOUT) }
            )
        }
        composable(Routes.FILE_PICKER) {
            FilePickerScreen(
                onConversionStarted = { conversionId ->
                    navController.navigate(Routes.conversionProgress(conversionId)) {
                        popUpTo(Routes.HOME)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Routes.CONVERSION_PROGRESS,
            arguments = listOf(navArgument("conversionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val conversionId = backStackEntry.arguments?.getString("conversionId") ?: return@composable
            ConversionProgressScreen(
                conversionId = conversionId,
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onNewConversion = {
                    navController.navigate(Routes.FILE_PICKER) {
                        popUpTo(Routes.HOME)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.HISTORY) {
            HistoryScreen(
                onNavigateToHome = { navController.popBackStack() },
                onNavigateToConversion = { conversionId ->
                    navController.navigate(Routes.conversionProgress(conversionId))
                }
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onNavigateToPremium = { navController.navigate(Routes.PREMIUM) },
                onNavigateToAbout = { navController.navigate(Routes.ABOUT) },
                onNavigateToPrivacy = { navController.navigate(Routes.PRIVACY_POLICY) },
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PREMIUM) {
            PremiumScreen(
                onPurchaseComplete = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ABOUT) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.PRIVACY_POLICY) {
            PrivacyPolicyScreen(onBack = { navController.popBackStack() })
        }
    }
}
