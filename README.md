# 🎬 KmpMoviesOficial

A Kotlin Multiplatform movie application built using Compose Multiplatform and TMDB API.

This project demonstrates clean architecture principles, shared business logic, and modern Android/iOS development using Kotlin Multiplatform.

---

## 📱 Features

- Browse popular movies
- Display movie posters and titles
- Shared UI using Compose Multiplatform
- Shared networking layer using Ktor
- MVVM architecture
- API key handled securely via local properties

---

## 🛠 Tech Stack

- Kotlin Multiplatform
- Compose Multiplatform
- Ktor Client
- Kotlinx Serialization
- Coroutines
- MVVM Architecture
- Gradle Kotlin DSL

---

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
