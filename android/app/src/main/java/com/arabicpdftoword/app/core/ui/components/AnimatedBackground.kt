package com.arabicpdftoword.app.core.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.arabicpdftoword.app.core.ui.theme.GradientEnd
import com.arabicpdftoword.app.core.ui.theme.GradientMiddle
import com.arabicpdftoword.app.core.ui.theme.GradientPremiumEnd
import com.arabicpdftoword.app.core.ui.theme.GradientPremiumStart
import com.arabicpdftoword.app.core.ui.theme.GradientStart

@Composable
fun AnimatedBackground(
    modifier: Modifier = Modifier,
    isPremium: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bg_anim")
    val xOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "x_offset"
    )
    val yOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "y_offset"
    )

    val colors = if (isPremium) {
        listOf(GradientPremiumStart, GradientPremiumEnd)
    } else {
        listOf(GradientStart, GradientMiddle, GradientEnd)
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val startX = width * xOffset * 0.3f
        val startY = height * yOffset * 0.3f
        val endX = width - (width * xOffset * 0.3f)
        val endY = height - (height * yOffset * 0.3f)

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(startX, startY),
                end = Offset(endX, endY)
            ),
            size = size
        )
    }
}
