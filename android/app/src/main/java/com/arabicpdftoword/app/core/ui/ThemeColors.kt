package com.arabicpdftoword.app.core.ui

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

val IslamicTeal = Color(0xFF00897B)
val IslamicGold = Color(0xFFFFD700)
val Primary = Color(0xFF1565C0)
val Secondary = Color(0xFF00897B)
val Tertiary = Color(0xFF7B1FA2)
val Surface = Color(0xFFFFFFFF)
val Background = Color(0xFFF8F9FA)
val OnPrimary = Color(0xFFFFFFFF)
val OnSurface = Color(0xFF1C1B1F)
val Error = Color(0xFFB00020)
val PremiumGold = Color(0xFFFFD700)
val GradientStart = Color(0xFF1565C0)
val GradientEnd = Color(0xFF00897B)

@Composable
fun PdfToWordTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF90CAF9),
            secondary = Color(0xFF80CBC4),
            tertiary = Color(0xFFCE93D8),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            error = Color(0xFFCF6679),
            onPrimary = Color(0xFF000000),
            onSecondary = Color(0xFF000000),
            onBackground = Color(0xFFE6E1E5),
            onSurface = Color(0xFFE6E1E5)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF1565C0),
            secondary = Color(0xFF00897B),
            tertiary = Color(0xFF7B1FA2),
            background = Color(0xFFF8F9FA),
            surface = Color(0xFFFFFFFF),
            error = Color(0xFFB00020),
            onPrimary = Color(0xFFFFFFFF),
            onSecondary = Color(0xFFFFFFFF),
            onBackground = Color(0xFF1C1B1F),
            onSurface = Color(0xFF1C1B1F)
        )
    }
    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

fun Activity.updateStatusBarColor(darkTheme: Boolean) {
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = !darkTheme
    }
}
