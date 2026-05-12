# Architecture Documentation — Arabic PDF To Word AI Converter

## Overview

This project follows **Clean Architecture** with **MVVM (Model-View-ViewModel)** pattern on the Android side and a layered **FastAPI** backend. The architecture emphasizes separation of concerns, testability, and maintainability.

---

## Android Architecture

### Layers

```
┌────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  (Composables, ViewModels, UiState, Navigation)            │
├────────────────────────────────────────────────────────────┤
│                      Domain Layer                          │
│  (UseCases, Repository Interfaces, Domain Models)          │
├────────────────────────────────────────────────────────────┤
│                       Data Layer                           │
│  (Repository Impls, DataSources, DTOs, Mappers)           │
├────────────────────────────────────────────────────────────┤
│                       Core Layer                           │
│  (DI, Database, Network, Utils, Common)                   │
└────────────────────────────────────────────────────────────┘
```

### Presentation Layer
- **Composable Screens** — 100% Jetpack Compose with Material3
- **ViewModels** — Each feature has a ViewModel extending `ViewModel()`, injected via `@HiltViewModel`
- **UiState** — Each screen has a sealed class / data class representing its state
- **StateFlow** — ViewModels expose `StateFlow<UiState>` collected as Compose state
- **Navigation** — All routes defined in `NavGraph.kt` with `Routes` object

```
presentation/
├── navigation/          # NavGraph.kt, Routes
├── splash/              # SplashScreen, SplashViewModel
├── home/                # HomeScreen, HomeViewModel, HomeUiState
├── filepicker/          # FilePickerScreen
├── conversion/          # ConversionProgressScreen
├── history/             # HistoryScreen, HistoryViewModel, HistoryUiState
├── settings/            # SettingsScreen, SettingsViewModel, SettingsUiState
├── premium/             # PremiumScreen, PremiumViewModel, PremiumUiState
├── about/               # AboutScreen
└── privacy/             # PrivacyPolicyScreen, PrivacyPolicyViewModel
```

### Domain Layer
- **UseCases** — Single-responsibility classes injected into ViewModels
- **Repository Interfaces** — Contracts for data operations (e.g., `ConversionRepository`, `AuthRepository`, `PreferencesRepository`)
- **Domain Models** — Pure Kotlin data classes free of framework annotations

```
domain/
├── model/
│   ├── ConversionItem.kt
│   ├── ConversionStats.kt
│   ├── ConversionStatus.kt
│   └── User.kt
├── repository/
│   ├── ConversionRepository.kt
│   ├── AuthRepository.kt
│   └── PreferencesRepository.kt
└── usecase/
    ├── UploadPdfUseCase.kt
    ├── CheckConversionStatusUseCase.kt
    ├── GetConversionHistoryUseCase.kt
    ├── DeleteConversionUseCase.kt
    ├── GetConversionStatsUseCase.kt
    ├── RegisterUseCase.kt
    ├── LoginUseCase.kt
    └── LogoutUseCase.kt
```

### Data Layer
- **Repository Implementations** — Implement domain interfaces, coordinate local + remote data sources
- **DataSources** — `LocalConversionDataSource` (Room), `RemoteConversionDataSource` (Retrofit)
- **DTOs** — Network response models
- **Mappers** — Convert between DTOs/Entities and domain models

```
data/
├── datasource/
│   ├── LocalConversionDataSource.kt
│   └── RemoteConversionDataSource.kt
├── dto/                 # Network response/request DTOs
├── mapper/
│   ├── ConversionMapper.kt
│   └── UserMapper.kt
└── repository/
    ├── ConversionRepositoryImpl.kt
    ├── AuthRepositoryImpl.kt
    └── PreferencesRepositoryImpl.kt
```

### Core Layer

```
core/
├── common/
│   ├── Constants.kt          # App-wide constants (no secrets)
│   └── Resource.kt           # Success/Error/Loading wrapper
├── database/
│   ├── AppDatabase.kt        # Room Database
│   ├── dao/
│   │   └── ConversionDao.kt  # Room DAO
│   └── entity/
│       └── ConversionEntity.kt
├── di/
│   ├── AppModule.kt          # DI: Database, DAO
│   ├── NetworkModule.kt      # DI: OkHttp, Retrofit, ApiService
│   └── RepositoryModule.kt   # DI: Repository bindings
├── network/
│   ├── ApiService.kt         # Retrofit interface
│   ├── AuthInterceptor.kt    # JWT token injection
│   └── RetryInterceptor.kt   # Retry logic
└── util/                     # NoorPreferences, FileUtils, DateUtils, etc.
```

---

## MVVM Pattern

```
User Action → Composable → ViewModel (StateFlow) → UseCase → Repository → DataSource
                                                                              │
                    ┌─────────────────────────────────────────────────────────┘
                    ▼
              API Response / DB Result
                    │
                    ▼
              Repository → Domain Model
                    │
                    ▼
              ViewModel updates StateFlow
                    │
                    ▼
              Composable recomposes with new state
```

### ViewModel Pattern

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ConversionRepository,
    private val prefs: NoorPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
        observePreferences()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            prefs.isPremium.collect { isPremium ->
                _uiState.update { it.copy(isPremium = isPremium) }
            }
        }
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getAllConversions().collect { conversions ->
                    _uiState.update { it.copy(recentConversions = conversions, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
```

---

## Dependency Injection with Hilt

### Module Structure

| Module | Scope | Provides |
|--------|-------|----------|
| `AppModule` | Singleton | Room DB, DAO, App version |
| `NetworkModule` | Singleton | OkHttpClient, AuthInterceptor, RetryInterceptor, Retrofit, ApiService |
| `RepositoryModule` | Singleton | Repository implementations bound to interfaces |

### Key Bindings

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindConversionRepository(impl: ConversionRepositoryImpl): ConversionRepository

    @Binds @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds @Singleton
    abstract fun bindPreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository
}
```

---

## Room Database Schema

### Entity: `ConversionEntity`

| Column | Type | Description |
|--------|------|-------------|
| `id` | Long (PK, auto) | Auto-generated ID |
| `conversion_id` | String | Server-assigned UUID |
| `original_file_name` | String | Original uploaded PDF name |
| `original_file_size` | Long | PDF file size in bytes |
| `output_file_name` | String? | Output DOCX file name |
| `output_file_size` | Long? | Output file size |
| `status` | String | `pending`, `processing`, `completed`, `failed` |
| `page_count` | Int? | Number of pages |
| `ocr_used` | Boolean | Whether OCR was applied |
| `error_message` | String? | Error details if failed |
| `language` | String? | OCR language used |
| `created_at` | Long | Timestamp (millis) |
| `completed_at` | Long? | Completion timestamp |
| `file_path` | String? | Local PDF path |
| `output_path` | String? | Local output path |
| `is_premium` | Boolean | Whether premium was used |

### DAO: `ConversionDao`

| Method | Description |
|--------|-------------|
| `insert(entity)` | Insert or replace conversion |
| `update(entity)` | Update existing entity |
| `delete(entity)` | Delete entity |
| `getAllConversions()` | Flow of all conversions (desc by date) |
| `getByConversionId(id)` | Find by server ID |
| `getById(id)` | Find by local ID |
| `deleteByConversionId(id)` | Delete by server ID |
| `clearAll()` | Delete all conversions |
| `getCount()` | Total count |
| `getCountSince(timestamp)` | Count since timestamp |
| `updateStatus(...)` | Update conversion status |
| `markCompleted(...)` | Mark as completed with output details |

---

## Network Layer

### ApiService (Retrofit)

```kotlin
interface ApiService {
    @Multipart @POST("api/convert")
    suspend fun uploadAndConvert(@Part file, @Part language, @Part isPremium): Response<ConversionResponse>

    @GET("api/convert/{id}/status")
    suspend fun getConversionStatus(@Path("id") id: String): Response<ConversionStatusResponse>

    @GET("api/convert/{id}/download") @Streaming
    suspend fun downloadFile(@Path("id") id: String): Response<ResponseBody>

    @DELETE("api/convert/{id}")
    suspend fun cancelConversion(@Path("id") id: String): Response<Unit>

    @GET("api/user/stats")
    suspend fun getUserStats(): Response<UserStatsResponse>

    @POST("api/user/upgrade")
    suspend fun upgradeToPremium(@Body request): Response<Unit>
}
```

### Interceptors

- **AuthInterceptor** — Injects JWT Bearer token from `NoorPreferences` into all requests
- **RetryInterceptor** — Retries failed requests (up to 3 times with exponential backoff)

### OkHttp Configuration

```kotlin
val client = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .addInterceptor(retryInterceptor)
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) BODY else NONE
    })
    .cache(Cache(cacheDir, 10 * 1024 * 1024))  // 10MB cache
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()
```

---

## Navigation Flow

```
SPLASH ──► HOME
            ├──► FILE_PICKER ──► CONVERSION_PROGRESS ──► HOME
            ├──► HISTORY ──► CONVERSION_PROGRESS
            ├──► SETTINGS ──► PREMIUM
            │               └──► ABOUT
            │               └──► PRIVACY_POLICY
            ├──► PREMIUM
            └──► ABOUT
```

All routes are defined in `NavGraph.kt` using Jetpack Navigation Compose.

---

## Error Handling Strategy

### Android

1. **Repository level**: Operations return `Resource<T>` (Success / Error / Loading)
2. **ViewModel level**: Errors caught in `viewModelScope.launch`, set in `UiState.error`
3. **UI level**: Compose screens observe `uiState.error` and show Snackbar/Dialog
4. **Network errors**: `RetryInterceptor` retries transient failures; `Resource.Error` includes user-friendly Arabic messages

```kotlin
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String, val data: T? = null) : Resource<T>()
    object Loading : Resource<Nothing>()
}
```

### Backend

1. **HTTPException** — Standard FastAPI exceptions with appropriate status codes
2. **Global exception handler** — Catches unhandled exceptions, logs to Sentry
3. **Validation** — Pydantic schemas validate all request/response data
4. **Rate limiting** — FastAPI Limiter with Redis backend (30 req/min default)
5. **File validation** — Magic bytes check for PDF header; size validation

---

## Testing Strategy

### Unit Tests (`app/src/test/`)

| Component | Framework | What to Test |
|-----------|-----------|-------------|
| ViewModels | MockK + Truth | State changes, use case calls |
| UseCases | MockK + Truth | Business logic, edge cases |
| Repositories | MockK + Truth | Data source coordination, error handling |
| Mappers | Truth | DTO <-> Domain model conversion |
| Utils | Truth | FileUtils, DateUtils, etc. |

### Example Test Structure

```kotlin
class HomeViewModelTest {
    @MockK private lateinit var repository: ConversionRepository
    @MockK private lateinit var prefs: NoorPreferences
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { prefs.isPremium } returns flowOf(false)
        every { repository.getAllConversions() } returns flowOf(emptyList())
        viewModel = HomeViewModel(repository, prefs)
    }

    @Test
    fun `loadData should update state with conversions`() = runTest {
        // ...
    }
}
```

### Instrumentation Tests (`app/src/androidTest/`)

- Compose UI tests using `createComposeRule()` and `ComposeTestRule`
- Navigation tests
- Flow-based integration tests

---

## Backend Architecture (FastAPI)

### Layer Structure

```
backend/
├── api/               # Route handlers (controllers)
│   ├── health.py      # Health check endpoints
│   ├── auth.py        # Authentication endpoints
│   └── convert.py     # PDF conversion endpoints
├── core/              # Cross-cutting concerns
│   ├── config.py      # Pydantic-based settings (env vars)
│   ├── security.py    # JWT, password hashing, file validation
│   ├── logging_config.py
│   └── rate_limiter.py
├── models/            # SQLAlchemy ORM models
│   ├── base.py        # Declarative base
│   ├── user.py        # User model
│   └── conversion.py  # Conversion model
├── schemas/           # Pydantic models (request/response)
│   ├── user.py
│   └── conversion.py
├── services/          # Business logic
│   ├── pdf_service.py        # PDF text extraction, analysis
│   ├── ocr_service.py        # PaddleOCR + Tesseract
│   ├── conversion_pipeline.py # Orchestration
│   └── docx_service.py       # DOCX generation
├── worker/            # Celery async tasks
│   ├── queue.py
│   └── tasks.py
├── dependencies.py    # FastAPI DI (DB sessions, auth)
├── config.py          # App configuration
└── main.py            # FastAPI application
```

### Data Flow (Conversion Pipeline)

```
Client Upload ──► FastAPI ──► File Validation ──► DB Insert (pending)
                                    │
                                    ▼
                         PDF Analysis (PyMuPDF)
                                    │
                         ┌──────────┴──────────┐
                         ▼                     ▼
                    Has Text?              Scanned?
                         │                     │
                    Extract Text         Convert to Images
                         │                     │
                         │               OCR Processing
                         │             ┌────┴────┐
                         │          PaddleOCR  Tesseract
                         │             └────┬────┘
                         │                  │
                         └──────────┬───────┘
                                    ▼
                          Layout Detection
                                    │
                                    ▼
                            DOCX Generation
                                    │
                                    ▼
                          DB Update (completed)
                                    │
                                    ▼
                         Client Polls Status
                         → Downloads DOCX
```

### Database Schema (PostgreSQL)

**Users Table:**
| Column | Type | Details |
|--------|------|---------|
| id | UUID | PK, default uuid4 |
| email | VARCHAR(255) | Unique, indexed |
| hashed_password | TEXT | bcrypt hash |
| full_name | VARCHAR(255) | Nullable |
| is_premium | BOOLEAN | Default false |
| premium_until | DATE | Nullable |
| is_active | BOOLEAN | Default true |
| conversion_count | INTEGER | Default 0 |
| daily_conversion_count | INTEGER | Default 0 |
| last_conversion_date | DATE | Nullable |

**Conversions Table:**
| Column | Type | Details |
|--------|------|---------|
| id | UUID | PK, default uuid4 |
| user_id | UUID | FK → users.id (CASCADE) |
| original_filename | VARCHAR(500) | |
| original_size | INTEGER | Bytes |
| status | VARCHAR(20) | pending/completed/failed |
| page_count | INTEGER | Nullable |
| ocr_used | BOOLEAN | Default false |
| ocr_engine | VARCHAR(50) | paddle/tesseract |
| error_message | TEXT | Nullable |
| language | VARCHAR(10) | Default "ara" |
| file_path | VARCHAR(1000) | Upload path |
| output_path | VARCHAR(1000) | Output path |

### Celery Task Queue

- **Broker**: Redis
- **Workers**: 4 concurrent (configurable)
- **Queues**: `conversions`, `maintenance`, `notifications`
- **Beat Schedule**: Cleanup old files, reset daily counters, send reminders

### Monitoring

- **Prometheus metrics** at `/api/v1/metrics`
- **Flower** (Celery monitor) at port 5555
- **Sentry integration** for error tracking
- **Structured logging** with structlog

---

## Key Architectural Decisions

| Decision | Rationale |
|----------|-----------|
| **Clean Architecture + MVVM** | Separates concerns, testable, maintainable, follows Android best practices |
| **StateFlow over LiveData** | First-class coroutine support, better lifecycle management, cleaner API |
| **Resource<T> wrapper** | Explicitly models Success/Error/Loading states |
| **Room for local cache** | Persists conversion history offline; works as single source of truth |
| **Firebase Firestore as server** | Real-time sync, offline support, serverless scaling |
| **Dual OCR (Paddle + Tesseract)** | PaddleOCR for deep learning accuracy, Tesseract as lightweight fallback |
| **Celery for async processing** | Heavy OCR tasks offloaded from API server, improves responsiveness |
| **Docker Compose** | Single-command infrastructure setup, consistent dev/prod environments |

---

## Feature-by-Feature Architecture

### PDF Upload & Convert
1. User selects PDF → FilePickerScreen
2. ViewModel calls `UploadPdfUseCase`
3. Repository uploads via `RemoteDataSource.uploadPdf()`
4. Backend validates, analyzes, converts (sync or async)
5. Client polls `getConversionStatus()` every 2 seconds
6. On completion, `ConversionProgressScreen` shows download option

### Premium Subscription
1. User taps "Go Premium" → PremiumScreen
2. Google Play Billing flow (purchase / restore)
3. On purchase success: `PremiumUpgradeRequest` sent to backend
4. Backend sets `is_premium = true`, `premium_until` extended
5. Client updates `NoorPreferences.isPremium`
6. UI reflects premium status everywhere

### Dark Mode
1. User toggles in SettingsScreen
2. Preference saved to DataStore via `NoorPreferences`
3. `MainActivity` observes the preference as Compose state
4. `MaterialTheme` composable switches between light/dark color schemes
5. Preference persists across app restarts
