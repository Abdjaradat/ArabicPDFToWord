package com.arabicpdftoword.app.core.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.arabicpdftoword.app.core.common.Constants
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: NoorPreferences
) {
    companion object {
        private const val TAG = "AdManager"
        private const val MIN_INTERVAL_MS = 30000L
    }

    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private var appOpenAd: AppOpenAd? = null
    private var lastAdShowTime = 0L
    private var isInitialized = false

    init {
        initialize()
    }

    private fun initialize() {
        MobileAds.initialize(context) { _ ->
            isInitialized = true
            Log.d(TAG, "Mobile Ads SDK initialized")
        }
    }

    fun loadBannerAd(): AdView {
        val adView = AdView(context)
        adView.adUnitId = Constants.BANNER_AD_ID
        adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        return adView
    }

    fun loadInterstitialAd(onLoaded: (() -> Unit)? = null) {
        if (!isInitialized) return

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            Constants.INTERSTITIAL_AD_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    onLoaded?.invoke()
                    Log.d(TAG, "Interstitial ad loaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    Log.e(TAG, "Interstitial ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    fun showInterstitialAd(activity: Activity, onDismissed: () -> Unit) {
        if (!canShowAd()) {
            onDismissed()
            return
        }

        if (isPremiumUser()) {
            onDismissed()
            return
        }

        interstitialAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    lastAdShowTime = System.currentTimeMillis()
                    interstitialAd = null
                    loadInterstitialAd()
                    onDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    interstitialAd = null
                    onDismissed()
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad showed")
                }
            }
            ad.show(activity)
        } ?: run {
            onDismissed()
        }
    }

    fun loadRewardedAd() {
        if (!isInitialized) return

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            Constants.REWARDED_AD_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    Log.d(TAG, "Rewarded ad loaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    rewardedAd = null
                    Log.e(TAG, "Rewarded ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    fun showRewardedAd(activity: Activity, onRewarded: () -> Unit, onNotReady: () -> Unit) {
        if (isPremiumUser()) {
            onRewarded()
            return
        }

        rewardedAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    rewardedAd = null
                    loadRewardedAd()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    rewardedAd = null
                    onNotReady()
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Rewarded ad showed")
                }
            }
            ad.show(activity) { _ ->
                onRewarded()
            }
        } ?: run {
            onNotReady()
        }
    }

    fun loadAppOpenAd(activity: Activity) {
        if (!isInitialized) return

        val adRequest = AdRequest.Builder().build()
        AppOpenAd.load(
            activity,
            Constants.APP_OPEN_AD_ID,
            adRequest,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    Log.d(TAG, "App open ad loaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    appOpenAd = null
                    Log.e(TAG, "App open ad failed: ${loadAdError.message}")
                }
            }
        )
    }

    fun showAppOpenAd(activity: Activity, onDismissed: () -> Unit) {
        if (!canShowAd() || isPremiumUser()) {
            onDismissed()
            return
        }

        appOpenAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    onDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    appOpenAd = null
                    onDismissed()
                }
            }
            ad.show(activity)
        } ?: run {
            onDismissed()
        }
    }

    fun isPremiumUser(): Boolean {
        var premium = false
        kotlinx.coroutines.runBlocking {
            preferences.isPremium.collect { premium = it; return@collect }
        }
        return premium
    }

    fun resetAds() {
        interstitialAd = null
        rewardedAd = null
        appOpenAd = null
        loadInterstitialAd()
        loadRewardedAd()
    }

    private fun canShowAd(): Boolean {
        val now = System.currentTimeMillis()
        return (now - lastAdShowTime) >= MIN_INTERVAL_MS
    }
}
