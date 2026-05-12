package com.arabicpdftoword.app.presentation.premium

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arabicpdftoword.app.core.ui.IslamicGold
import com.arabicpdftoword.app.core.ui.IslamicTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(
    onPurchaseComplete: () -> Unit,
    onBack: () -> Unit,
    viewModel: PremiumViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.purchaseSuccess) {
        if (uiState.purchaseSuccess) {
            kotlinx.coroutines.delay(1500)
            onPurchaseComplete()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Premium", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(IslamicTeal, Color(0xFF004D40))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = IslamicGold,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Upgrade to Premium",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Unlock all features",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.purchaseSuccess) {
                // Success state
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50).copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Welcome to Premium!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Enjoy all premium features",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                // Feature list
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "All Features",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        PremiumFeature("Unlimited Conversions", Icons.Filled.AllInclusive)
                        PremiumFeature("No Ads", Icons.Filled.CancelPresentation)
                        PremiumFeature("Priority Processing", Icons.Filled.Speed)
                        PremiumFeature("OCR Support", Icons.Filled.TextFields)
                        PremiumFeature("100MB File Support", Icons.Filled.CloudUpload)
                        PremiumFeature("Multiple Export Formats", Icons.Filled.FileCopy)
                        PremiumFeature("Premium Support", Icons.Filled.SupportAgent)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Pricing cards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Monthly
                    PricingCard(
                        title = "Monthly",
                        price = uiState.monthlyPrice ?: "$9.99",
                        period = "per month",
                        description = "Billed monthly",
                        isPopular = false,
                        isLoading = uiState.isLoadingProducts,
                        onSelect = { viewModel.purchaseMonthly(context as Activity) },
                        modifier = Modifier.weight(1f)
                    )

                    // Yearly
                    PricingCard(
                        title = "Yearly",
                        price = uiState.yearlyPrice ?: "$49.99",
                        period = "per year",
                        description = uiState.yearlySavings ?: "Best value",
                        isPopular = true,
                        isLoading = uiState.isLoadingProducts,
                        onSelect = { viewModel.purchaseYearly(context as Activity) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Restore
                TextButton(
                    onClick = { viewModel.restorePurchases() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(Icons.Filled.Restore, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Restore Purchases")
                }

                if (uiState.isPurchasing) {
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Processing your purchase...",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (uiState.error != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Error,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = uiState.error ?: "",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Footer links
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = {}) {
                    Text("Terms", fontSize = 12.sp)
                }
                TextButton(onClick = {}) {
                    Text("Privacy", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PremiumFeature(text: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = IslamicTeal,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 15.sp)
    }
}

@Composable
private fun PricingCard(
    title: String,
    price: String,
    period: String,
    description: String,
    isPopular: Boolean,
    isLoading: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onSelect,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = if (isPopular) BorderStroke(2.dp, IslamicGold) else null,
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isPopular) 8.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isPopular) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = IslamicGold
                ) {
                    Text(
                        text = "Most Popular",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text(
                    text = price,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = period,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = if (isPopular) ButtonDefaults.buttonColors(
                    containerColor = IslamicGold,
                    contentColor = Color.Black
                ) else ButtonDefaults.buttonColors()
            ) {
                Text(
                    text = "Subscribe",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
