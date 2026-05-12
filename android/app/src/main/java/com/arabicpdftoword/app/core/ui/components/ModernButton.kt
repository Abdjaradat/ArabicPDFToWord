package com.arabicpdftoword.app.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arabicpdftoword.app.core.ui.theme.GradientEnd
import com.arabicpdftoword.app.core.ui.theme.GradientPremiumEnd
import com.arabicpdftoword.app.core.ui.theme.GradientPremiumStart
import com.arabicpdftoword.app.core.ui.theme.GradientStart

@Composable
fun ModernButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    isPremium: Boolean = false,
    showGradient: Boolean = true,
    height: Dp = 52.dp,
    cornerRadius: Dp = 12.dp,
    contentColor: Color = Color.White,
    loadingIndicatorColor: Color = Color.White
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.5f,
        label = "button_alpha"
    )

    val gradientBrush = when {
        isPremium -> Brush.horizontalGradient(
            colors = listOf(GradientPremiumStart, GradientPremiumEnd)
        )
        showGradient -> Brush.horizontalGradient(
            colors = listOf(GradientStart, GradientEnd)
        )
        else -> null
    }

    val shape = RoundedCornerShape(cornerRadius)

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        enabled = enabled && !isLoading,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
        )
    ) {
        if (showGradient && gradientBrush != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .clip(shape)
                    .background(gradientBrush)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = loadingIndicatorColor,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )
        }
    }
}
