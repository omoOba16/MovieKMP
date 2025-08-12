# MovieKMP 🎬

MovieKMP is a Kotlin Multiplatform (KMP) application designed to showcase movies from The Movie Database (TMDB) API. It demonstrates modern Android and iOS development practices using Jetpack Compose for the UI layer, shared business logic, and a clean architecture approach.

## ✨ Features

*   Browse lists of movies:
    *   Trending
    *   Now Playing
    *   Popular
    *   Top Rated
    *   Upcoming
*   Clean, modern UI built with Jetpack Compose.
*   Shared ViewModels and business logic across Android and iOS.
*   Endless scrolling/pagination for movie lists.
*   Image loading with Coil 3, including crossfade animations.
*   Shimmer loading animations for a better user experience.

## 🛠️ Tech Stack & Architecture

*   **Kotlin Multiplatform (KMP):** For sharing code between Android and iOS.
*   **Jetpack Compose:** For building the user interface declaratively on Android and iOS (via Compose Multiplatform).
*   **Kotlin Coroutines & Flow:** For asynchronous programming and reactive data streams.
*   **ViewModel:** For managing UI-related data and state (using KMP-compatible ViewModel patterns).
*   **Koin:** For dependency injection (or your chosen DI framework).
*   **Coil 3:** For image loading.
*   **Retrofit & Ktor (or just Ktor):** For networking (Ktor is more KMP-idiomatic for the common module).
*   **Material 3 Design:** For UI components and theming.
*   **Clean Architecture (MVVM-like in Presentation):**
    *   **Presentation Layer:** Composables (UI), ViewModels.
    *   **Domain Layer (Conceptual):** Use cases, business logic (can be directly in ViewModels for simpler cases).
    *   **Data Layer:** Repositories, API service, local data sources (if any).
*   **Gradle Kotlin DSL:** For build configuration.

### API Key

This project uses The Movie Database (TMDB) API. To run the app, you need to obtain an API key and an Access Token from TMDB.

1.  Create an account at [TMDB](https://www.themoviedb.org/signup).
2.  Go to your account settings, then the "API" section.
3.  Request an API Key.
4.  You will also need your **API Read Access Token (v4 auth)**.

Once you have your Access Token:

### Android

1.  Create a `local.properties` file in the root of your project (if it doesn't already exist).
2.  Add your TMDB Access Token to the `local.properties` file

### iOS

1.  Create a `Secrets.plist` file (if it doesn't already exist).
2.  Add your TMDB Access Token
    
