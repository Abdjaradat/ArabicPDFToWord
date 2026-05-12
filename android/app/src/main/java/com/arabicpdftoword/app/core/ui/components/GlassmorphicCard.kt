package com.arabicpdftoword.app.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arabicpdftoword.app.core.ui.theme.GlassDark
import com.arabicpdftoword.app.core.ui.theme.GlassLight
import com.arabicpdftoword.app.core.ui.theme.GlassStrokeDark
import com.arabicpdftoword.app.core.ui.theme.GlassStrokeLight
import com.arabicpdftoword.app.core.ui.theme.ShadowColor

@Composable
fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    val backgroundColor = if (isDark) GlassDark else GlassLight
    val strokeColor = if (isDark) GlassStrokeDark else GlassStrokeLight

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            backgroundColor,
                            backgroundColor.copy(alpha = 0.5f)
                        )
                    )
                )
                .then(
                    if (cornerRadius > 0.dp) {
                        Modifier.clip(RoundedCornerShape(cornerRadius))
                    } else Modifier
                )
                .then(
                    Modifier.background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                strokeColor.copy(alpha = 0.1f),
                                Color.Transparent
                            )
                        )
                    )
                )
                .then(Modifier.padding(16.dp))
        ) {
            content()
        }
    }
}
