# Fake Contacts Avalanche

A testing utility Android app for generating large amounts of fake contacts in the phone book.

## Purpose

This application is designed for **testing purposes** when you need to populate a device with a large number of contacts quickly. Useful for:

- Testing contact synchronization
- Performance testing of contact-related features
- UI/UX testing with realistic data
- Stress testing contact management systems
- Testing search and filtering functionality

## Features

- ✅ **Bulk Contact Generation** - Create 1 to 10,000 contacts at once
- ✅ **Realistic Data** - Generates authentic names, phone numbers, companies, and job titles
- ✅ **Real-time Progress** - Live progress bar with percentage and count
- ✅ **Cancellation Support** - Stop generation at any time
- ✅ **Modern UI** - Material Design 3 with clean, intuitive interface
- ✅ **Multi-language Support** - 7 languages including RTL languages
- ✅ **MVVM Architecture** - Clean, maintainable code structure
- ✅ **Kotlin Coroutines** - Efficient async operations

## Screenshots

### Main Screen
- Input field for number of contacts (1-10,000)
- Generate button to start creation
- Real-time progress display
- Cancel button during generation

### Progress Display
- Current progress: "Creating contacts: 50 / 100"
- Visual progress bar
- Percentage indicator
- Cancellable operation

## Supported Languages

The app automatically adapts to your device language:

- 🇬🇧 **English** (default)
- 🇷🇺 **Russian** (Русский)
- 🇺🇦 **Ukrainian** (Українська)
- 🇪🇸 **Spanish** (Español)
- 🇸🇦 **Arabic** (العربية) - RTL support
- 🇮🇱 **Hebrew** (עברית) - RTL support
- 🇮🇷 **Persian** (فارسی) - RTL support

## Technical Details

### Requirements

- **Minimum SDK**: Android 7.1 (API 25)
- **Target SDK**: Android 14 (API 34)
- **Compile SDK**: Android 14 (API 34)
- **Language**: Kotlin 1.9.22
- **Build System**: Gradle 8.4

### Permissions Required

- `READ_CONTACTS` - To verify contact creation
- `WRITE_CONTACTS` - To create fake contacts

The app properly requests runtime permissions with clear explanations.

### Architecture

- **MVVM Pattern** - Separation of concerns
- **Repository Pattern** - Clean data access layer
- **Kotlin Coroutines** - Async operations without blocking UI
- **StateFlow** - Reactive state management
- **View Binding** - Type-safe view access
- **Material Design 3** - Modern UI components

### Key Technologies

- **AndroidX Libraries** - Modern Android components
- **Lifecycle & ViewModel** - Lifecycle-aware components
- **Kotlin Coroutines** - Structured concurrency
- **DataFaker** - Realistic fake data generation
- **Material Components** - Material Design 3 UI

## Generated Contact Data

Each contact includes:
- **Full Name** - Random realistic names
- **Phone Number** - Random cell phone numbers
- **Company** - Random company names
- **Job Title** - Random job positions

All data is generated using the DataFaker library for authenticity.

## Build & Installation

### Using Android Studio

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run on device or emulator

### Using Command Line

```bash
# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Build and install
./gradlew assembleDebug installDebug
```

The APK will be located in `app/build/outputs/apk/debug/app-debug.apk`

## Usage

1. **Launch the app** - Open "Fake Contacts Avalanche"
2. **Grant permissions** - Allow access to contacts when prompted
3. **Enter quantity** - Type the number of contacts (1-10,000)
4. **Generate** - Tap the "Generate" button
5. **Monitor progress** - Watch real-time progress updates
6. **Cancel** (optional) - Stop generation at any time
7. **Done** - Contacts are created and ready to use

## Safety Features

- **Input Validation** - Prevents invalid numbers
- **Maximum Limit** - Capped at 10,000 contacts per operation
- **Cancellation** - Can stop at any time without corruption
- **Error Handling** - Graceful handling of failures
- **Progress Feedback** - Always know what's happening

## Performance

- **Efficient** - One Faker instance reused for all contacts
- **Async** - Non-blocking UI with Coroutines
- **Fast** - Creates ~100 contacts per second (device dependent)
- **Memory Safe** - Proper resource management

## Testing

### On Emulator

```bash
# Create emulator (if needed)
avdmanager create avd -n TestDevice -k "system-images;android-34;google_apis;x86_64"

# Start emulator
emulator -avd TestDevice

# Install and run
./gradlew installDebug
```

### On Physical Device

1. Enable Developer Options
2. Enable USB Debugging
3. Connect device via USB
4. Trust the computer
5. Run `./gradlew installDebug`

## Localization

The app supports 7 languages with automatic fallback to English for unsupported languages. RTL (Right-to-Left) languages are fully supported with proper UI mirroring.

See [LOCALIZATION.md](LOCALIZATION.md) for details on adding new languages.

## Project Structure

```
app/
├── src/main/
│   ├── java/com/toptunov/fakecontactsavalanche/
│   │   ├── MainActivity.kt          # Main UI controller
│   │   ├── MainViewModel.kt         # State management
│   │   ├── ContactsRepository.kt    # Data access layer
│   │   └── UiState.kt               # UI state definitions
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml    # Main screen layout
│   │   ├── values/                  # English strings (default)
│   │   ├── values-ru/               # Russian strings
│   │   ├── values-uk/               # Ukrainian strings
│   │   ├── values-es/               # Spanish strings
│   │   ├── values-ar/               # Arabic strings (RTL)
│   │   ├── values-iw/               # Hebrew strings (RTL)
│   │   └── values-fa/               # Persian strings (RTL)
│   └── AndroidManifest.xml
└── build.gradle
```

## Dependencies

### Core
- `androidx.core:core-ktx:1.17.0`
- `androidx.appcompat:appcompat:1.7.1`
- `com.google.android.material:material:1.13.0`

### Architecture
- `androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0`
- `androidx.lifecycle:lifecycle-runtime-ktx:2.10.0`
- `androidx.activity:activity-ktx:1.12.2`

### Async
- `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1`

### Data Generation
- `net.datafaker:datafaker:2.5.3`

### Java 17 Support
- `com.android.tools:desugar_jdk_libs:2.1.5`

## Known Limitations

- Maximum 10,000 contacts per operation (can run multiple times)
- Generated data is random (may have duplicates)
- Requires storage space for contacts database
- Performance depends on device capabilities

## Troubleshooting

### Permission Denied
- Check that permissions are granted in Settings → Apps → Fake Contacts Avalanche → Permissions

### Slow Generation
- Close background apps
- Reduce the number of contacts per batch
- Device performance varies

### App Crashes
- Clear app data in Settings
- Reinstall the app
- Check device storage space

## Contributing

This is a testing utility. Feel free to fork and modify for your needs.

### Areas for Enhancement
- Export/Import contact data
- Custom contact templates
- Batch deletion
- Contact categories/groups
- Profile pictures
- Additional contact fields (email, address, etc.)

## License

This is a utility application for testing purposes.

## Author

Created for testing and development purposes.

## Version History

### 1.1 (Current)
- Android 14 (API 34) support
- 7 languages including RTL
- Modern MVVM architecture
- Kotlin Coroutines
- Material Design 3
- Real-time progress
- Cancellation support

### 1.0 (Initial)
- Basic contact generation
- Simple UI
- Android 13 support

## Disclaimer

This app is intended for **testing purposes only**. The contacts created are fake and randomly generated. Do not use for production purposes or with real user data.

---

**Happy Testing!** 🚀

