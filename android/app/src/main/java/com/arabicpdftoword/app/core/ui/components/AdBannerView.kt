package com.arabicpdftoword.app.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.arabicpdftoword.app.core.common.Constants
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdBannerView(
    modifier: Modifier = Modifier,
    adUnitId: String = Constants.BANNER_AD_ID
) {
    AndroidView(
        factory = { context ->
            AdView(context).apply {
                setAdUnitId(adUnitId)
                setAdSize(AdSize.BANNER)
                loadAd(AdRequest.Builder().build())
            }
        },
        modifier = modifier.fillMaxWidth(),
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        }
    )
}
