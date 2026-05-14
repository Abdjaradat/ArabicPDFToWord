package com.arabicpdftoword.app.core.common

object Constants {
    const val APP_NAME = "Arabic PDF To Word AI Converter"
    const val APP_NAME_AR = "محول PDF إلى Word بالذكاء الاصطناعي"
    const val BASE_URL = "https://arabic-pdf-to-word-api-1.onrender.com/"
    const val PDF_MIME_TYPE = "application/pdf"
    const val MAX_FILE_SIZE_MB = 10
    const val MAX_FILE_SIZE_FREE = 10L * 1024 * 1024 // 10MB
    const val MAX_FILE_SIZE_PREMIUM = 100L * 1024 * 1024 // 100MB
    const val FREE_DAILY_LIMIT = 5
    const val POLL_INTERVAL_MS = 5000L
    const val MAX_POLL_TIME_MS = 30L * 60 * 1000 // 30 minutes
    const val SPLASH_DURATION_MS = 2000L
    const val PREF_NAME = "arabic_pdf_to_word_prefs"
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 600L
    const val WRITE_TIMEOUT = 600L
    const val RETRY_MAX_ATTEMPTS = 3
    const val RETRY_BASE_DELAY_MS = 1000L
    object Prefs {
        const val DARK_MODE = "dark_mode"
        const val LANGUAGE = "language"
        const val IS_PREMIUM = "is_premium"
        const val USER_EMAIL = "user_email"
        const val IS_LOGGED_IN = "is_logged_in"
        const val DAILY_CONVERSION_COUNT = "daily_conversion_count"
        const val LAST_CONVERSION_DATE = "last_conversion_date"
        const val FIRST_LAUNCH = "first_launch"
        const val AUTO_DELETE = "auto_delete"
    }
    const val BANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111"
    const val INTERSTITIAL_AD_ID = "ca-app-pub-3940256099942544/1033173712"
    const val REWARDED_AD_ID = "ca-app-pub-3940256099942544/5224354917"
    const val APP_OPEN_AD_ID = "ca-app-pub-3940256099942544/3419835294"
    object AdMob {
        const val BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
        const val APP_OPEN_AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294"
    }
    const val SHARE_REQUIRED_COUNT = 3
    const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=com.arabicpdftoword.app"
}
