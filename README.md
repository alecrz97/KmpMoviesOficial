# 🎬 KmpMoviesOficial

KmpMoviesOficial is a Kotlin Multiplatform movie app built with Compose Multiplatform and the TMDB API.  
The project shares UI, business logic, networking, persistence, and presentation logic across Android / iOS.

It demonstrates a clean architecture approach using MVVM, use cases, repository pattern, Ktor, Room Multiplatform, Kotlinx Serialization, Coroutines, Flow, and Compose Navigation.

---

## 📱 Features

- Browse popular movies from TMDB
- Search movies with debounce
- View movie details, including backdrop, overview, release date, rating, popularity, and language
- Add or remove movies from favorites
- Add or remove movies from watchlist
- Local persistence using Room Multiplatform
- Pagination for popular movies
- Shared UI with Compose Multiplatform
- Shared ViewModels, use cases, repositories, and domain models
- Cross-platform networking with Ktor
- Loading and empty states
- English and Spanish string resources

---

## 🛠 Tech Stack

- Kotlin Multiplatform
- Compose Multiplatform
- Material 3
- Ktor Client
- OkHttp engine for Android
- Darwin engine for iOS
- Kotlinx Serialization
- Coroutines and Flow
- Room Multiplatform
- SQLite Bundled
- Coil 3
- Compose Navigation
- MVVM
- Clean Architecture
- Gradle Kotlin DSL
- BuildConfig for API key management

---

Architecture

The project follows a clean architecture style:

- data: remote API service, Room database, entities, mappers, and repository implementation
- domain: business models, repository contracts, and use cases
- ui: Compose screens, navigation, and ViewModels

Main flow:

UI -> ViewModel -> UseCase -> Repository -> API / Local Database

## 🧱 Project Structure
composeApp/
└── src/
    ├── commonMain/
    ├── androidMain/
    └── iosMain/

iosApp/


---

## 🔑 API Setup

This project uses The Movie Database (TMDB) API.

1. Create an account at https://www.themoviedb.org/
2. Go to Settings → API
3. Request an API key
4. Create a file named `local.properties` in the root project directory
5. Add the following:
```properties
API_KEY=your_api_key_here
```
6. Sync the project and run

⚠️ The API key is not included in this repository for security reasons.

---

## 🚀 How to Run

### Android

- Open the project in Android Studio
- Sync Gradle
- Run on emulator or device

### iOS

- Open the `iosApp` project in Xcode
- Build and run on simulator

---

## 🎯 Purpose

This project demonstrates production-ready Kotlin Multiplatform architecture,
showcasing shared UI, networking, and clean separation of concerns.

- Kotlin Multiplatform architecture
- Shared UI with Compose
- Cross-platform networking
- Clean project structure for production-ready apps

---

## 👨‍💻 Author

Alecrz97 
Android & Kotlin Multiplatform Developer  
Open to opportunities
