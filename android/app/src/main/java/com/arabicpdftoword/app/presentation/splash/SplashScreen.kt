package com.arabicpdftoword.app.presentation.splash

import android.content.Intent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arabicpdftoword.app.core.ui.IslamicGold

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.navigateTo) {
        when (uiState.navigateTo) {
            SplashDestination.GoToHome -> {
                viewModel.onNavigated()
                onNavigateToHome()
            }
            SplashDestination.GoToLogin -> {
                viewModel.onNavigated()
                onNavigateToLogin()
            }
            null -> { }
        }
    }

    if (uiState.crashLog != null) {
        AlertDialog(
            onDismissRequest = { viewModel.onCrashDialogDismissed() },
            title = { Text("تم اكتشاف عطل / Crash Detected") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "حدث عطل في آخر تشغيل. يرجى إرسال التقرير أدناه للمطور.\n" +
                               "A crash occurred on last launch. Please share the report below.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(12.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = uiState.crashLog!!,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val share = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, uiState.crashLog)
                    }
                    context.startActivity(Intent.createChooser(share, "إرسال تقرير العطل"))
                }) {
                    Text("مشاركة / Share")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onCrashDialogDismissed() }) {
                    Text("تجاهل / Dismiss")
                }
            }
        )
    }

    val scaleAnim = remember { Animatable(0.3f) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(1f, animationSpec = spring(dampingRatio = 0.5f, stiffness = 200f))
        alphaAnim.animateTo(1f, animationSpec = tween(800))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF00897B), Color(0xFF2E7D32))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scaleAnim.value)
                .alpha(alphaAnim.value)
                .padding(32.dp)
        ) {
            Text(
                text = "PDF",
                fontSize = 64.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = "↓",
                fontSize = 40.sp,
                color = IslamicGold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Word",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Arabic PDF To Word AI Converter",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "محول PDF إلى Word بالذكاء الاصطناعي",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            CircularProgressIndicator(
                color = IslamicGold,
                strokeWidth = 3.dp,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}