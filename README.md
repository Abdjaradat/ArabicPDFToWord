# Arabic PDF To Word AI Converter

<div dir="rtl">

# محول PDF إلى Word بالذكاء الاصطناعي

تطبيق احترافي لتحويل ملفات PDF العربية إلى Word مع الحفاظ على التنسيق والتوجيه باستخدام أحدث تقنيات الذكاء الاصطناعي والتعرف على النصوص (OCR).

</div>

---

## Features / المميزات

- **Smart PDF to DOCX Conversion** — High-fidelity conversion preserving Arabic RTL layout, fonts, and formatting
- **OCR Engine** — Dual OCR support (PaddleOCR + Tesseract) with Arabic language optimization
- **AI-Powered Text Recognition** — Automatic detection of scanned documents with intelligent OCR fallback
- **Layout Preservation** — Maintains headings, paragraphs, tables, and images
- **Image Enhancement** — Pre-processing pipeline (denoising, contrast, deskewing, thresholding)
- **Cross-Platform** — Android app + REST API backend
- **Premium Subscription** — Free tier (5 conversions/day, 10MB max) and Premium (unlimited, 100MB, priority)
- **Cloud Processing** — Files processed on backend server, no device storage burden
- **Conversion History** — Track, download, and manage all conversions
- **Ad Supported** — Free tier with AdMob banner ads
- **RTL / Arabic First** — Full Arabic language UI and right-to-left layout support

---

## Screenshots / لقطات الشاشة

<!-- TODO: Add screenshots -->
| Home | File Picker | Conversion | Premium |
|------|-------------|------------|---------|
| ![Home](screenshots/home.png) | ![Picker](screenshots/picker.png) | ![Progress](screenshots/progress.png) | ![Premium](screenshots/premium.png) |

---

## Tech Stack / التقنيات المستخدمة

### Android (Frontend)
| Technology | Purpose |
|------------|---------|
| **Kotlin** | Primary language |
| **Jetpack Compose** | UI framework |
| **Material 3** | Design system |
| **MVVM + Clean Architecture** | Architecture pattern |
| **Hilt** | Dependency injection |
| **Room** | Local database |
| **Retrofit + OkHttp** | Networking |
| **WorkManager** | Background tasks |
| **Coroutines + Flow** | Async & reactive |
| **AdMob** | Advertising |
| **Firebase** | Auth, Firestore, Storage, FCM, Analytics, Crashlytics, Remote Config |

### Backend
| Technology | Purpose |
|------------|---------|
| **Python FastAPI** | REST API framework |
| **PostgreSQL** | Primary database |
| **Redis** | Caching & rate limiting |
| **Celery** | Async task queue |
| **Docker** | Containerization |
| **PaddleOCR** | Deep learning OCR engine |
| **Tesseract** | Traditional OCR engine (fallback) |
| **PyMuPDF (fitz)** | PDF text extraction |
| **pdfplumber** | Table extraction |
| **python-docx** | DOCX generation |
| **Nginx** | Reverse proxy |
| **Prometheus** | Metrics |

---

## Project Structure / هيكل المشروع

```
ArabicPDFToWord/
├── android/                          # Android App (Kotlin + Jetpack Compose)
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── java/com/arabicpdftoword/app/
│   │   │   │   ├── core/
│   │   │   │   │   ├── common/           # Constants, Resource wrapper
│   │   │   │   │   ├── database/         # Room DB, DAOs, Entities
│   │   │   │   │   ├── di/               # Hilt DI modules
│   │   │   │   │   ├── network/          # Retrofit, OkHttp, ApiService
│   │   │   │   │   └── util/             # Preferences, FileUtils, etc.
│   │   │   │   ├── data/
│   │   │   │   │   ├── datasource/       # Local & remote data sources
│   │   │   │   │   ├── dto/              # Data transfer objects
│   │   │   │   │   ├── mapper/           # Entity/DTO <-> Domain mappers
│   │   │   │   │   └── repository/       # Repository implementations
│   │   │   │   ├── domain/
│   │   │   │   │   ├── model/            # Domain models
│   │   │   │   │   ├── repository/       # Repository interfaces
│   │   │   │   │   └── usecase/          # Use cases
│   │   │   │   └── presentation/
│   │   │   │       ├── navigation/       # NavGraph, Routes
│   │   │   │       ├── splash/
│   │   │   │       ├── home/
│   │   │   │       ├── filepicker/
│   │   │   │       ├── conversion/
│   │   │   │       ├── history/
│   │   │   │       ├── settings/
│   │   │   │       ├── premium/
│   │   │   │       ├── about/
│   │   │   │       └── privacy/
│   │   │   ├── res/                      # Resources (layouts, drawables, strings)
│   │   │   └── AndroidManifest.xml
│   │   ├── build.gradle.kts
│   │   └── proguard-rules.pro
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   └── gradle.properties
│
├── backend/                          # Python FastAPI Backend
│   ├── app/
│   │   ├── api/                      # Route handlers (auth, convert, health)
│   │   ├── core/                     # Config, security, logging, rate_limiter
│   │   ├── models/                   # SQLAlchemy ORM models
│   │   ├── schemas/                  # Pydantic request/response schemas
│   │   ├── services/                 # Business logic (OCR, PDF, DOCX, pipeline)
│   │   ├── worker/                   # Celery task queue
│   │   ├── dependencies.py           # FastAPI dependency injection
│   │   ├── config.py                 # Pydantic-based settings
│   │   └── main.py                   # FastAPI app entry point
│   ├── Dockerfile
│   ├── docker-compose.yml
│   ├── nginx.conf
│   ├── requirements.txt
│   └── .env
│
├── README.md
├── ARCHITECTURE.md
├── CONTRIBUTING.md
├── GOOGLE_PLAY_DEPLOYMENT.md
└── BUSINESS_PLAN.md
```

---

## Prerequisites / المتطلبات الأساسية

- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 17** (or later)
- **Python 3.11+** (for backend development)
- **Docker & Docker Compose** (for backend deployment)
- **PostgreSQL 16** (or use Docker image)
- **Git**

---

## Setup Instructions / تعليمات الإعداد

### 1. Clone the Repository

```bash
git clone https://github.com/your-organization/ArabicPDFToWord.git
cd ArabicPDFToWord
```

### 2. Backend Setup

```bash
cd backend

# Copy environment file
cp .env.example .env
# Edit .env with your configuration

# Start all services with Docker
docker-compose up -d

# The API will be available at:
#   http://localhost:8000
#   API Docs: http://localhost:8000/api/v1/docs
#   ReDoc:    http://localhost:8000/api/v1/redoc
#   Flower (Celery Monitor): http://localhost:5555

# Check service health
curl http://localhost:8000/api/v1/health
```

### 3. Android Setup

1. Open the `android/` directory in Android Studio
2. Wait for Gradle sync to complete

#### Firebase Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project (or use existing)
3. Register Android app with package name `com.arabicpdftoword.app`
4. Download `google-services.json` and place it in:
   ```
   android/app/google-services.json
   ```
5. Enable Firebase services:
   - Authentication (Email/Password + Google Sign-In)
   - Firestore Database
   - Storage
   - Cloud Messaging
   - Crashlytics
   - Performance Monitoring
   - Remote Config

#### Release Keystore
1. Generate a release keystore (if not exists):
   ```bash
   keytool -genkey -v -keystore release.keystore -alias upload -keyalg RSA -keysize 2048 -validity 10000
   ```
2. Place it at: `android/app/release.keystore`
3. Set environment variables:
   ```bash
   export KEYSTORE_PASSWORD=your_password
   export KEY_ALIAS=upload
   export KEY_PASSWORD=your_key_password
   ```

#### local.properties
Add to `android/local.properties`:
```properties
sdk.dir=C:\\Users\\YourUser\\AppData\\Local\\Android\\Sdk
```

### 4. Build and Run

```bash
# Debug build
cd android
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run on emulator/device
./gradlew installDebug
```

---

## Firebase Setup Guide

1. **Create Firebase Project**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Add Android app with package: `com.arabicpdftoword.app`
   - Download `google-services.json` → place in `android/app/`

2. **Authentication**
   - Enable Email/Password sign-in
   - Enable Google sign-in (configure OAuth consent screen, get Web Client ID)
   - Update `GOOGLE_WEB_CLIENT_ID` in `android/app/build.gradle.kts`

3. **Firestore Database**
   - Create in production mode
   - Set up security rules (start with test mode, then restrict)
   - Create collections: `users`, `conversions`, `subscriptions`

4. **Storage**
   - Set up bucket for uploaded PDFs
   - Configure CORS if needed

5. **Cloud Messaging**
   - Enable FCM for push notifications
   - Add server key to backend for notification sending

6. **Crashlytics & Performance**
   - Enable both in Firebase console
   - Add `google-services.json` with Crashlytics plugin active

7. **Remote Config**
   - Create parameters for feature flags, daily limits, etc.

---

## AdMob Setup Guide

### Ad Unit IDs

| Ad Format | Test ID | Production ID |
|-----------|---------|---------------|
| **Banner** | `ca-app-pub-3940256099942544/6300978111` | Create in AdMob console |
| **App Open** | `ca-app-pub-3940256099942544/3419835294` | Create in AdMob console |

### Configuration

1. Create an [AdMob account](https://admob.google.com/)
2. Register your app in AdMob console
3. Create Ad Unit IDs for Banner and App Open formats
4. Update `Constants.kt` with your production Ad Unit IDs:
   ```kotlin
   object AdMob {
       const val BANNER_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXX/YYYYYYYYYY"
       const val APP_OPEN_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXX/YYYYYYYYYY"
   }
   ```
5. Test thoroughly with test IDs before switching to production

---

## Google Play Billing Setup

1. Create a Google Play Console account (\$25 fee)
2. Set up a managed product for premium subscription
3. Configure subscription tiers (Monthly / Yearly)
4. Add Google Play Billing library dependency (already in `build.gradle.kts`)
5. Implement billing flow in `PremiumViewModel`
6. Test with license testers before production release

---

## API Endpoints / نقاط النهاية

### Health
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/health` | Basic health check |
| GET | `/api/v1/health/detailed` | Detailed health (DB, Redis, storage) |
| GET | `/api/v1/metrics` | Prometheus metrics |

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Register new user |
| POST | `/api/v1/auth/login` | Login with email/password |
| POST | `/api/v1/auth/refresh` | Refresh access token |
| GET | `/api/v1/auth/me` | Get current user profile |
| PUT | `/api/v1/auth/me` | Update user profile |
| POST | `/api/v1/auth/forgot-password` | Request password reset |
| GET | `/api/v1/auth/stats` | Get user stats |
| POST | `/api/v1/auth/upgrade` | Upgrade to premium |

### Conversion
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/convert/upload` | Upload and convert PDF |
| GET | `/api/v1/convert/status/{id}` | Check conversion status |
| GET | `/api/v1/convert/download/{id}` | Download converted DOCX |
| DELETE | `/api/v1/convert/{id}` | Delete conversion |
| GET | `/api/v1/convert/history` | Get conversion history |
| GET | `/api/v1/convert/stats` | Get conversion statistics |

---

## Environment Variables Reference

| Variable | Default | Description |
|----------|---------|-------------|
| `DATABASE_URL` | `postgresql+asyncpg://postgres:postgres@localhost:5432/pdftoword` | PostgreSQL connection string |
| `REDIS_URL` | `redis://localhost:6379/0` | Redis connection string |
| `SECRET_KEY` | `dev-secret-key-change-in-production` | JWT signing key |
| `JWT_ALGORITHM` | `HS256` | JWT algorithm |
| `ACCESS_TOKEN_EXPIRE_MINUTES` | `60` | Access token TTL |
| `REFRESH_TOKEN_EXPIRE_DAYS` | `7` | Refresh token TTL |
| `UPLOAD_DIR` | `./uploads` | PDF upload directory |
| `OUTPUT_DIR` | `./output` | DOCX output directory |
| `MAX_FILE_SIZE_MB` | `100` | Max upload file size |
| `RATE_LIMIT_PER_MINUTE` | `30` | API rate limit |
| `PADDLEOCR_ENABLED` | `true` | Enable PaddleOCR |
| `TESSERACT_CMD` | `/usr/bin/tesseract` | Tesseract binary path |
| `OCR_LANGUAGE` | `ara+eng` | OCR language(s) |
| `ENVIRONMENT` | `development` | Runtime environment |
| `LOG_LEVEL` | `DEBUG` | Logging level |
| `SENTRY_DSN` | `` | Sentry error tracking DSN |
| `CELERY_BROKER_URL` | `redis://localhost:6379/0` | Celery broker |
| `CELERY_RESULT_BACKEND` | `redis://localhost:6379/0` | Celery result backend |

---

## Deployment / النشر

### Backend (Render / Railway / AWS)

```bash
# Build and push Docker image
docker build -t arabic-pdf-to-word-api .

# Deploy with docker-compose
docker-compose -f docker-compose.prod.yml up -d
```

### Android (Google Play)

1. Generate signed release bundle:
   ```bash
   cd android
   ./gradlew bundleRelease
   ```
2. The AAB will be at: `android/app/build/outputs/bundle/release/app-release.aab`
3. Upload to Google Play Console
4. Follow deployment guide in `GOOGLE_PLAY_DEPLOYMENT.md`

---

## License / الترخيص

All rights reserved. This project and its source code are proprietary and confidential. Unauthorized copying, distribution, or use is strictly prohibited.

---

## Contact / اتصل بنا

- **Email**: support@arabicpdftoword.com
- **Website**: https://arabicpdftoword.com
- **API Base URL**: `https://arabic-pdf-to-word-api.onrender.com`

---

*Made with ❤️ for Arabic content creators everywhere*
