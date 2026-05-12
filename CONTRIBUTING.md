# Contributing to Arabic PDF To Word AI Converter

Thank you for considering contributing to this project! We welcome contributions that help improve the accuracy, performance, and user experience of the Arabic PDF to Word conversion tool.

## Code of Conduct

By participating in this project, you agree to maintain a respectful, inclusive, and harassment-free environment for everyone.

## How to Contribute

### Reporting Bugs

1. **Search existing issues** — Check if the bug has already been reported
2. **Create a new issue** with:
   - Clear, descriptive title
   - Steps to reproduce (include PDF sample if possible)
   - Expected vs actual behavior
   - Screenshots / screen recordings
   - Device info (Android version, device model)
   - App version (from Settings > About)

### Feature Requests

1. **Check existing requests** — Search for similar feature requests
2. **Open a new issue** with:
   - Use case / problem statement
   - Proposed solution
   - Alternative solutions considered
   - Any relevant mockups or references

### Pull Requests

#### 1. Fork and Clone

```bash
git clone https://github.com/your-username/ArabicPDFToWord.git
cd ArabicPDFToWord
git remote add upstream https://github.com/original-owner/ArabicPDFToWord.git
```

#### 2. Create a Branch

```bash
git checkout -b fix/issue-123-description
# or
git checkout -b feature/awesome-new-feature
```

Branch naming convention:
- `fix/` — Bug fixes
- `feature/` — New features
- `refactor/` — Code improvements
- `docs/` — Documentation changes
- `test/` — Test additions/changes

#### 3. Make Changes

**Android (Kotlin + Jetpack Compose):**
- Follow Clean Architecture + MVVM pattern
- Use `StateFlow<UiState>` in all ViewModels
- All strings must support Arabic (RTL)

**Backend (Python FastAPI):**
- Follow existing service-layer pattern
- Use async/await for all database operations
- All new endpoints must have Pydantic schemas

#### 4. Run Tests

```bash
# Android unit tests
cd android
./gradlew test

# Backend tests
cd backend
pytest
```

#### 5. Commit Guidelines

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <description>

[optional body]
```

Types: `feat`, `fix`, `refactor`, `test`, `docs`, `style`, `perf`, `chore`

Examples:
```
feat(ocr): add PaddleOCR fallback for low-confidence text
fix(android): handle empty PDF crash on upload
docs(api): update conversion endpoint response schema
```

#### 6. Push and Open PR

```bash
git push origin fix/issue-123-description
```

Then open a Pull Request on GitHub with:
- Reference to the issue being fixed (e.g., `Closes #123`)
- Description of changes
- Screenshots (for UI changes)
- Testing steps performed
- Any migration steps or breaking changes noted

### PR Review Process

1. Automated checks (lint, tests) must pass
2. At least one maintainer review required
3. Address all review comments
4. PR will be squashed-merged

---

## Development Setup

### Android

1. Open `android/` in Android Studio
2. Wait for Gradle sync
3. Add `google-services.json` from Firebase Console
4. Run on emulator or device

### Backend

1. Install Python 3.11+ and Docker
2. `cd backend && docker-compose up -d`
3. API available at `http://localhost:8000`

### Required Tools

| Tool | Version | Purpose |
|------|---------|---------|
| Android Studio | Hedgehog+ | Android development |
| JDK | 17+ | Compilation |
| Python | 3.11+ | Backend |
| Docker | 24+ | Containerization |
| PostgreSQL | 16 | Database |
| Redis | 7 | Caching/queue |

---

## Coding Standards

### Kotlin / Android
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use `val` over `var` where possible
- All composable functions use `@Composable` annotation
- RTL layout support for all screens (`LayoutDirection.Rtl`)
- No hardcoded strings — use `strings.xml` resources

### Python / Backend
- Follow [PEP 8](https://pep8.org/)
- Type hints required for all function signatures
- Async functions for I/O operations
- Docstrings for all public functions

### Git
- Keep commits small and focused
- Write meaningful commit messages
- Rebase before merging (no merge commits in PRs)

---

## Testing Requirements

All PRs must include or update tests:

| Layer | Test Type | Framework |
|-------|-----------|-----------|
| ViewModels | Unit test | MockK + Truth |
| UseCases | Unit test | MockK + Truth |
| Repositories | Unit test | MockK + Truth |
| API routes | Integration | pytest + httpx (backend) |
| Compose UI | Instrumentation | Compose Test |

---

## Questions?

- Open a GitHub Discussion
- Contact: developers@arabicpdftoword.com

Thank you for contributing! 🙌
