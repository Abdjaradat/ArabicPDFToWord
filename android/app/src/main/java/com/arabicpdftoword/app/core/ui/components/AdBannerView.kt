package com.arabicpdftoword.app.core.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arabicpdftoword.app.core.common.Constants
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener

@Composable
fun AdBannerView(
    modifier: Modifier = Modifier,
    adUnitId: String = Constants.BANNER_AD_ID
) {
    val context = LocalContext.current
    var isLoaded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val adView = remember {
        AdView(context).apply {
            setAdUnitId(adUnitId)
            setAdSize(AdSize.BANNER)
        }
    }

    LaunchedEffect(Unit) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    DisposableEffect(Unit) {
        adView.adListener = object : com.google.android.gms.ads.AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                isLoaded = true
                errorMessage = null
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                isLoaded = false
                errorMessage = adError.message
                Log.e("AdBannerView", "Ad failed to load: ${adError.message}")
            }

            override fun onAdOpened() {
                super.onAdOpened()
            }

            override fun onAdClosed() {
                super.onAdClosed()
            }
        }

        onDispose {
            adView.destroy()
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoaded) {
            android.widget.FrameLayout(context).apply {
                addView(adView)
            }
        } else if (errorMessage != null) {
            Text(
                text = "",
                modifier = Modifier.height(0.dp)
            )
        }
    }
}
