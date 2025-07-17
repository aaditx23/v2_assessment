# v2_assessment Android App

## Introduction

v2_assessment is a modern Android application that demonstrates a dynamic question-and-answer flow, similar to a survey or form. The app fetches questions from a remote API, displays them based on their type (text input, number input, checkbox, dropdown, multiple choice, camera, etc.), and allows users to answer, skip, or submit responses. Submitted data is stored and can be viewed in a decorated summary screen. The app is built using Jetpack Compose, MVVM architecture, Hilt for dependency injection, and follows industry best practices for modularity and maintainability.

### Key Features
- Dynamic question loading from API
- Multiple question types (text, number, checkbox, dropdown, multiple choice, camera)
- Navigation between questions using referTo and skip logic
- Error handling and user feedback dialogs
- Submission and decorated display of answers
- Modular, scalable codebase (MVVM, Compose, Hilt)

## Folder Structure

```
app/
  src/
    main/
      java/com/aaditx23/v2_assessment/
        application/      # App-level classes
        data/             # Data sources, repositories
        di/               # Dependency injection setup
        model/            # Data models
        ui/
          components/     # Reusable UI components
          screens/        # Screen composables (answer, submitted, main)
          theme/          # App theming
        util/             # Utility/helper classes
      res/                # Resources (layouts, drawables, etc.)
  build.gradle.kts        # App-level Gradle config
README.md                 # Project documentation
```

## Setup & Running in Android Studio

1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   ```
2. **Open in Android Studio:**
   - Launch Android Studio.
   - Select 'Open' and choose the project folder.
3. **Sync Gradle:**
   - Let Android Studio sync and download dependencies automatically.
4. **Run the app:**
   - Connect an Android device or start an emulator.
   - Click the 'Run' button or use `Shift + F10`.

## Technologies Used & Third-Party Libraries

- **Kotlin**: Main language for Android development.
- **Jetpack Compose**: UI toolkit for building native interfaces.
- **MVVM Architecture**: Separation of UI, business logic, and data.
- **Hilt**: Dependency injection for managing app components.
- **Retrofit**: Handles network/API calls.
- **Moshi**: JSON serialization/deserialization for API responses.
- **Room**: Local database for storing submissions and caching data.
- **Coil**: Efficient image loading in Compose UI.
- **Accompanist Permissions**: Simplifies runtime permission handling in Compose.
- **Material Icons Extended**: Provides additional icon resources for UI.
- **Navigation Compose**: Manages in-app navigation between screens.
- **ViewModel Compose**: State management for Compose screens.
- **Test Libraries**: OkHttp MockWebServer (API mocking), Room Testing, AndroidX Test Core, JUnit, Espresso, Kotlin Coroutines Test (for robust backend and UI testing).

## API Base URL & Properties Files

- The API base URL is hidden in `remote.properties` for security and flexibility. This file is loaded at build time and injected into the app using Gradle's `buildConfigField`.
- For production, sensitive values (API keys, endpoints) should be stored in `.properties` files and excluded from version control (e.g., via `.gitignore`).
- To use a `.properties` file, add your key-value pairs (e.g., `BASE_URL=https://your.api.url`) and reference them in your Gradle build script as shown in this project.

## Testing

Some test cases are included to verify backend functionality (API calls, data handling, repositories) before diving into UI development. This ensures reliability and correctness of core logic.

## Additional Notes

- The codebase is modular and follows industry standards for scalability and maintainability.
- Error handling and user feedback are implemented throughout the app.
