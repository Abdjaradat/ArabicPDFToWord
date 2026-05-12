# Google Play Deployment Guide — Arabic PDF To Word AI Converter

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [App Signing](#app-signing)
3. [Generating Release Build](#generating-release-build)
4. [ProGuard / R8 Notes](#proguard--r8-notes)
5. [Google Play Console Setup](#google-play-console-setup)
6. [Store Listing Preparation](#store-listing-preparation)
7. [Content Rating](#content-rating)
8. [Pricing & Distribution](#pricing--distribution)
9. [Review Process](#review-process)
10. [Post-Launch Monitoring](#post-launch-monitoring)
11. [A/B Testing](#ab-testing)
12. [In-App Updates](#in-app-updates)
13. [Rollout Strategies](#rollout-strategies)

---

## Prerequisites

- **Google Play Developer Account** — Register at [play.google.com/console](https://play.google.com/console/) (\$25 one-time fee)
- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 17**
- **Android SDK 35** (compileSdk), minSdk 24
- **Release Keystore** — A Java keystore for signing your app
- **Firebase Project** — With `google-services.json` configured
- **AdMob Account** — For ad monetization
- **Google Play Billing** — For premium subscriptions

---

## App Signing

### Option A: Google Play App Signing (Recommended)

1. Generate an upload key (distinct from your app signing key):
   ```bash
   keytool -genkey -v -keystore upload-keystore.jks -alias upload -keyalg RSA -keysize 2048 -validity 10000
   ```

2. Extract the upload certificate for Google Play:
   ```bash
   keytool -export -rfc -alias upload -keystore upload-keystore.jks -file upload_certificate.pem
   ```

3. In Google Play Console:
   - Go to **Release > Setup > App Integrity**
   - Choose **"Let Google manage your app signing key"**
   - Upload your upload certificate

4. Configure your project:
   - Place `upload-keystore.jks` at `android/app/upload-keystore.jks`
   - Set environment variables or configure in `build.gradle.kts`

### Option B: Self-Managed Signing

1. Generate a single release key:
   ```bash
   keytool -genkey -v -keystore release.keystore -alias release -keyalg RSA -keysize 2048 -validity 10000
   ```
2. **IMPORTANT**: Back up this keystore securely. Losing it means you cannot update your app.

---

## Generating Release Build

### Prerequisites

Ensure your `android/app/build.gradle.kts` has proper signing configuration:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file("release.keystore")
        storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
        keyAlias = System.getenv("KEY_ALIAS") ?: ""
        keyPassword = System.getenv("KEY_PASSWORD") ?: ""
    }
}
```

Set environment variables:
```bash
# Windows
set KEYSTORE_PASSWORD=your_keystore_password
set KEY_ALIAS=your_key_alias
set KEY_PASSWORD=your_key_password

# macOS / Linux
export KEYSTORE_PASSWORD=your_keystore_password
export KEY_ALIAS=your_key_alias
export KEY_PASSWORD=your_key_password
```

### Build Commands

```bash
cd android

# Clean project
./gradlew clean

# Generate signed App Bundle (AAB) — recommended for Google Play
./gradlew bundleRelease

# Generate signed APK
./gradlew assembleRelease

# Run lint checks
./gradlew lint

# Run unit tests
./gradlew testReleaseUnitTest
```

### Output Files

| Format | Path |
|--------|------|
| **AAB** | `app/build/outputs/bundle/release/app-release.aab` |
| **APK** | `app/build/outputs/apk/release/app-release.apk` |
| **Mapping** | `app/build/outputs/mapping/release/mapping.txt` |

---

## ProGuard / R8 Notes

The project uses R8 for code shrinking and obfuscation in release builds.

### Key Rules (in `proguard-rules.pro`)

```proguard
# Keep Retrofit interfaces
-keep,allowobfuscation interface com.arabicpdftoword.app.core.network.ApiService

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep Room entities
-keep class com.arabicpdftoword.app.core.database.entity.** { *; }

# Keep data classes used with Gson
-keep class com.arabicpdftoword.app.data.dto.** { *; }

# Keep Firebase model classes
-keep class com.google.firebase.** { *; }

# Keep AdMob classes
-keep class com.google.android.gms.ads.** { *; }
```

### Testing the Release Build

Before uploading to Play Store, test the release build:

```bash
# Install release APK on device
./gradlew installRelease

# Or test bundle
bundletool build-apks --bundle=app/build/outputs/bundle/release/app-release.aab --output=app-release.apks --ks=release.keystore --ks-pass=pass:your_password --ks-key-alias=upload
bundletool install-apks --apks=app-release.apks
```

---

## Google Play Console Setup

### 1. Create Application

1. Go to [Google Play Console](https://play.google.com/console/)
2. Click **Create app**
3. Select app name: "Arabic PDF To Word AI Converter" (or Arabic: "محول PDF إلى Word بالذكاء الاصطناعي")
4. Select default language (Arabic or English)
5. Select app or game: **App**
6. Select free or paid: **Free** (with in-app purchases)
7. Agree to Play Console terms

### 2. Set Up App Integrity

- Upload the app signing certificate
- Choose between Google-managed or self-managed signing key

### 3. Complete Setup Tasks

In the Play Console dashboard, complete each section:

- **App details** — Full description, screenshots, feature graphic
- **App integrity** — Upload key certificate
- **Data safety** — Complete privacy questionnaire
- **Production** — Upload release build
- **Users & permissions** — Review permissions
- **Pricing & distribution** — Select countries
- **In-app products** — Set up subscriptions
- **Ads** — Configure ad settings

---

## Store Listing Preparation

### Short Description (80 chars max)

**English:** Convert Arabic PDF to Word with AI-powered OCR. Fast, accurate, and easy to use.

**Arabic:** حوّل ملفات PDF العربية إلى Word بدقة باستخدام الذكاء الاصطناعي OCR.

### Full Description (4000 chars max)

**English:**
```
Arabic PDF To Word AI Converter is the most accurate tool for converting Arabic PDF documents to editable Word files. Powered by advanced AI and OCR technology, it preserves Arabic text direction, formatting, and layout.

FEATURES:
• AI-Powered OCR – State-of-the-art text recognition for Arabic and English
• Layout Preservation – Maintains headings, tables, images, and RTL formatting
• Batch Processing – Convert multiple PDFs with ease
• 100% Accurate – Dual OCR engine (PaddleOCR + Tesseract) for maximum accuracy
• Cloud Processing – No device storage burden, fast server-side conversion
• Premium Features – Unlimited conversions, no ads, priority processing, up to 100MB files

HOW IT WORKS:
1. Select a PDF file from your device
2. Choose language (Arabic, English, or both)
3. Tap convert and let our AI process it
4. Download your perfectly formatted Word document

FREE TIER:
• 5 conversions per day
• Files up to 10MB
• Ad-supported

PREMIUM:
• Unlimited conversions
• No ads
• Files up to 100MB
• Priority processing
• OCR support for images
• Multiple export formats
• Premium support

Download now and experience the most accurate Arabic PDF to Word conversion!
```

**Arabic (Arabic description text):**
```
محول PDF إلى Word بالذكاء الاصطناعي هو الأداة الأكثر دقة لتحويل مستندات PDF العربية إلى ملفات Word قابلة للتعديل. مدعوم بأحدث تقنيات الذكاء الاصطناعي والتعرف على النصوص (OCR) مع الحفاظ على اتجاه النص العربي والتنسيق والتخطيط.

المميزات:
• OCR بالذكاء الاصطناعي – تعرف على النصوص العربية والإنجليزية بأعلى دقة
• الحفاظ على التنسيق – يحافظ على العناوين والجداول والصور والتنسيق من اليمين لليسار
• معالجة سحابية – لا حاجة لمساحة تخزين على جهازك، معالجة سريعة على الخوادم
• تقنية OCR مزدوجة – PaddleOCR + Tesseract لأقصى دقة
• المميزات الاحترافية – تحويلات غير محدودة، بدون إعلانات، معالجة ذات أولوية

الخطة المجانية:
• 5 تحويلات يومياً
• ملفات حتى 10 ميجابايت
• مدعوم بالإعلانات

البريميوم:
• تحويلات غير محدودة
• بدون إعلانات
• ملفات حتى 100 ميجابايت
• معالجة ذات أولوية
• دعم OCR للصور
• دعم فني متميز
```

### Graphic Assets

| Asset | Size | Format | Notes |
|-------|------|--------|-------|
| **Icon** | 512×512 | PNG 32-bit | App launcher icon |
| **Feature Graphic** | 1024×500 | PNG/JPG | Banner for Play Store |
| **Phone Screenshots** | 1080×1920 | PNG | Min 2, max 8 (English + Arabic) |
| **Tablet Screenshots** | 1080×1920 or 2048×2732 | PNG | Optional but recommended |
| **Promo Video** | 1920×1080 | MP4 | 30 sec - 2 min (optional) |

---

## Content Rating

1. Go to **Release > Setup > Content rating** in Play Console
2. Complete the questionnaire:
   - Category: **Education / Productivity**
   - No violent or sexual content
   - No gambling
   - AdMob serves personalized ads (check appropriate boxes)
3. Submit for rating — typically takes 1-3 days

---

## Pricing & Distribution

1. **Pricing** — Select **Free** (monetized via ads + in-app purchases)
2. **Countries / Regions** — Select all countries or targeted markets (MENA region + global)
3. **Device categories** — Check: Phones, Tablets
4. **Android requirements** — Min SDK 24 (Android 7.0+)
5. **In-app products** — Configure:
   - Monthly subscription: `premium_monthly`
   - Yearly subscription: `premium_yearly`

### Subscription Pricing

| Tier | Price (USD) | Period | Trial |
|------|-------------|--------|-------|
| Monthly | $4.99 | 1 month | No |
| Yearly | $29.99 | 1 year | 7-day free trial |

---

## Review Process

### Common Rejection Reasons & How to Avoid

| Issue | Solution |
|-------|----------|
| **Crashes on launch** | Test release build thoroughly on multiple devices. Use Crashlytics in pre-launch. |
| **Broken functionality** | Test all core flows: upload, conversion, download, premium purchase. |
| **Incomplete store listing** | Provide descriptions in all languages. Upload all required screenshots. |
| **Inappropriate permissions** | Review permissions. Only request `READ_EXTERNAL_STORAGE` when needed (use SAF/scoped storage). |
| **Ads policy violation** | Ensure AdMob compliance. Show privacy policy. Implement ad consent (GDPR). |
| **Missing privacy policy** | Provide a valid privacy policy URL accessible from the app and store listing. |

### Pre-Launch Checklist

- [ ] App builds and runs in **release mode**
- [ ] All Firebase services configured and working
- [ ] AdMob test ads replaced with production IDs
- [ ] Crashlytics integration verified (crash reported in test)
- [ ] Analytics events firing correctly
- [ ] Subscription flow works end-to-end (sandbox testers)
- [ ] Arabic RTL layout renders correctly
- [ ] App works offline (graceful degradation)
- [ ] Permissions requested at runtime (not at install)
- [ ] Privacy policy linked in app and store listing
- [ ] Content rating submitted
- [ ] App signing key backed up

---

## Post-Launch Monitoring

### Crashlytics
- Monitor crash-free rate (target: >99.5%)
- Set up real-time alerts for critical crashes
- Review non-fatal issues weekly
- Symbolicate all crash reports with mapping file

### Performance Monitoring
- Monitor app startup time
- Track conversion flow latency
- Monitor ANR rate

### Analytics
- Track: DAU/MAU, conversion rate, conversion count, premium conversion rate
- Set up funnels: Upload → Convert → Download
- Monitor: Churn rate, premium subscription conversion, ad revenue

### Revenue Monitoring
- Track: Ad impressions, eCPM, ad revenue
- Monitor: Subscription revenue, MRR, churn
- A/B test ad placements and frequency

---

## A/B Testing

### Google Play Store Listing Experiments
- Test different icons, screenshots, and descriptions
- Test pricing (different price points per country)

### Firebase Remote Config A/B
- Test daily conversion limits (5 vs 3 vs 7)
- Test ad frequency
- Test UI variations (button colors, placement)

### In-App A/B Testing
- Onboarding flow variations
- Premium upsell timing and copy
- Feature discovery prompts

---

## In-App Updates

### Implementation
The project uses `app-update` library for in-app updates:

```kotlin
// Check for updates on app launch
val appUpdateManager = AppUpdateManagerFactory.create(context)
val task = appUpdateManager.appUpdateInfo
task.addOnSuccessListener { info ->
    if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
        && info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
        appUpdateManager.startUpdateFlowForResult(
            info,
            AppUpdateType.IMMEDIATE,
            activity,
            REQUEST_CODE_UPDATE
        )
    }
}
```

### Update Types
- **Immediate** — Critical updates (security, crash fixes)
- **Flexible** — Feature updates (user can dismiss)

---

## Rollout Strategies

### Staged Rollout (Recommended)

| Stage | % of Users | Duration | Criteria to Advance |
|-------|------------|----------|---------------------|
| **Internal Test** | 5-10 testers | 1-2 days | No crashes, core flow OK |
| **Closed Alpha** | 0.1% | 2-3 days | Crash rate <0.5% |
| **Closed Beta** | 1-5% | 3-5 days | Crash rate <0.1% |
| **Open Beta** | 10-20% | 5-7 days | Crash rate <0.05% |
| **Full Rollout** | 100% | Immediate | Stable metrics |

### Rollback Plan
1. Monitor crash rate and ANR rate in Play Console
2. If crash rate exceeds threshold, immediately halt rollout
3. Roll back to previous version in Play Console
4. Fix issues in a hotfix release
5. Resume rollout with the hotfix

### Version Naming

| Type | Version | Version Code |
|------|---------|--------------|
| Initial Release | 1.0.0 | 1 |
| Bug Fix | 1.0.1 | 2 |
| Minor Feature | 1.1.0 | 3 |
| Major Release | 2.0.0 | 4 |

Version codes must be **monotonically increasing** for each upload.

---

## Post-Release Checklist

- [ ] Monitor Play Console for crash reports daily (first week)
- [ ] Respond to user reviews (especially 1-3 star ratings)
- [ ] Check Crashlytics for unexpected errors
- [ ] Verify analytics events are firing
- [ ] Check subscription revenue and churn
- [ ] Monitor AdMob performance
- [ ] Plan next iteration based on user feedback
- [ ] Update store listing with new features

---

## Emergency Procedures

### Critical Bug Found
1. Halt rollout in Play Console
2. Fix the bug and create a hotfix release
3. Increment version code (do NOT change version name for hotfix)
4. Upload new AAB with release notes explaining the fix
5. Resume rollout

### Security Issue
1. Immediately remove affected versions from Play Console
2. Fix the vulnerability
3. Push critical update with IMMEDIATE in-app update
4. Submit new release for review with "Security Update" flagged

---

## Useful Links

- [Google Play Console](https://play.google.com/console/)
- [Firebase Console](https://console.firebase.google.com/)
- [AdMob Console](https://apps.admob.com/)
- [Google Play Developer Policy Center](https://play.google.com/about/developer-content-policy/)
- [Android Developer Documentation](https://developer.android.com/distribute)
- [Google Play Signing](https://developer.android.com/studio/publish/app-signing)
