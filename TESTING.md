# Testing Guide

## Overview

This project includes comprehensive test coverage with both **Unit Tests** and **UI Tests** to ensure code quality and reliability.

---

## Test Statistics

### Unit Tests (28 total)
- **FakeDataGeneratorTest**: 8 tests
- **UiStateTest**: 9 tests
- **MainViewModelTest**: 10 tests
- **ExampleUnitTest**: 1 test

### UI Tests (20 total)
- **MainActivityTest**: 11 tests
- **ValidationTest**: 6 tests
- **PermissionsTest**: 2 tests
- **ExampleInstrumentedTest**: 1 test

---

## Unit Tests

Unit tests are located in `app/src/test/` and test individual components in isolation.

### Running Unit Tests

```bash
# Run all unit tests
./gradlew testDebugUnitTest

# View test report
open app/build/reports/tests/testDebugUnitTest/index.html
```

### Test Coverage

#### 1. FakeDataGeneratorTest
Tests the custom fake data generation logic:
- ✅ Name generation format and diversity
- ✅ Phone number format validation (+1-XXX-XXX-XXXX)
- ✅ Area code validation (200-999)
- ✅ Company and job title generation
- ✅ Data randomness and distribution
- ✅ Thread safety

#### 2. UiStateTest
Tests the sealed class hierarchy for UI state management:
- ✅ Idle state singleton behavior
- ✅ Loading state progress calculation
- ✅ Zero total handling
- ✅ Success state data storage
- ✅ Warning state data storage
- ✅ Error state data storage
- ✅ State equality comparisons

#### 3. MainViewModelTest
Tests the ViewModel business logic:
- ✅ Initial state is Idle
- ✅ Input validation (zero, negative, exceeding max)
- ✅ MAX_CONTACTS constant (10,000)
- ✅ State reset functionality
- ✅ Cancellation behavior
- ✅ Progress calculation
- ✅ Job cancellation on consecutive calls

### Technologies Used
- **JUnit 4**: Test framework
- **MockK**: Mocking library for Kotlin
- **Coroutines Test**: Testing coroutines
- **Arch Core Testing**: LiveData and ViewModel testing
- **Robolectric**: Android framework simulation

---

## UI Tests (Kaspresso)

UI tests are located in `app/src/androidTest/` and test the app's user interface and user flows.

### Running UI Tests

```bash
# Install on device/emulator
./gradlew installDebugAndroidTest

# Run all UI tests
./gradlew connectedDebugAndroidTest

# View test report
open app/build/reports/androidTests/connected/index.html
```

### Test Coverage

#### 1. MainActivityTest (11 tests)
Tests basic UI functionality:
- ✅ App launches successfully
- ✅ Input field is visible and functional
- ✅ Generate button is visible
- ✅ Helper text is displayed
- ✅ Empty input shows error
- ✅ Input accepts numbers
- ✅ Large number shows error
- ✅ Progress card initially hidden
- ✅ Cancel button initially hidden
- ✅ Input field is clearable
- ✅ Title text displays correctly

#### 2. ValidationTest (6 tests)
Tests input validation logic:
- ✅ Zero input shows error
- ✅ Negative number handling
- ✅ Maximum limit validation (10,001+)
- ✅ Valid number in range
- ✅ Maximum allowed number (10,000)
- ✅ Only numbers accepted

#### 3. PermissionsTest (2 tests)
Tests permission handling:
- ✅ App works with permissions granted
- ✅ Small number generation flow with permissions

### Page Object Pattern

The UI tests use the **Page Object Pattern** with Kaspresso's `KScreen`:

```kotlin
object MainScreen : KScreen<MainScreen>() {
    val titleText = KTextView { withId(R.id.titleText) }
    val inputField = KEditText { withId(R.id.contactsQuantity) }
    val generateButton = KButton { withId(R.id.generate_button) }
    val cancelButton = KButton { withId(R.id.cancel_button) }
    val progressCard = KTextView { withId(R.id.progressCard) }
    val statusText = KTextView { withId(R.id.statusText) }
}
```

### Technologies Used
- **Kaspresso 1.5.5**: Modern Kotlin-first UI testing framework
- **Kakao**: Type-safe DSL for Espresso
- **AndroidX Test**: Testing foundation
- **Espresso**: UI testing framework

---

## Kaspresso Features Used

### 1. Flaky Safety
```kotlin
flakySafely(timeoutMs = 5000) {
    progressCard.isDisplayed()
}
```

### 2. Step-by-Step Execution
```kotlin
step("Verify app launches with permissions") {
    MainScreen {
        titleText.isDisplayed()
    }
}
```

### 3. Allure Support
Kaspresso includes Allure report support for beautiful test reports.

---

## CI/CD Integration

### GitHub Actions Example

```yaml
name: Tests

on: [push, pull_request]

jobs:
  unit-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Run Unit Tests
        run: ./gradlew testDebugUnitTest
      
  ui-tests:
    runs-on: macos-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Run UI Tests
        run: ./gradlew connectedDebugAndroidTest
```

---

## Test Dependencies

```gradle
// Unit Testing
testImplementation 'junit:junit:4.13.2'
testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1'
testImplementation 'androidx.arch.core:core-testing:2.2.0'
testImplementation 'io.mockk:mockk:1.13.8'
testImplementation 'org.robolectric:robolectric:4.11.1'

// UI Testing (Kaspresso)
androidTestImplementation 'androidx.test.ext:junit:1.3.0'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.7.0'
androidTestImplementation 'androidx.test:runner:1.6.2'
androidTestImplementation 'androidx.test:rules:1.6.1'
androidTestImplementation 'com.kaspersky.android-components:kaspresso:1.5.5'
androidTestImplementation 'com.kaspersky.android-components:kaspresso-allure-support:1.5.5'
androidTestImplementation 'com.kaspersky.android-components:kaspresso-compose-support:1.5.5'
```

---

## Best Practices

### Unit Tests
1. ✅ Test one thing per test
2. ✅ Use descriptive test names with backticks
3. ✅ Follow Arrange-Act-Assert pattern
4. ✅ Mock external dependencies
5. ✅ Use coroutine test dispatchers

### UI Tests
1. ✅ Use Page Object Pattern
2. ✅ Add flaky safety for timing issues
3. ✅ Use descriptive step names
4. ✅ Clean up after tests
5. ✅ Test real user flows

---

## Writing New Tests

### Adding a Unit Test

```kotlin
@Test
fun `descriptive test name`() {
    // Arrange
    val input = 10
    
    // Act
    val result = someFunction(input)
    
    // Assert
    assertEquals(expected, result)
}
```

### Adding a UI Test

```kotlin
@Test
fun testNewFeature() = run {
    step("Setup") {
        MainScreen {
            // Arrange
        }
    }
    
    step("Action") {
        MainScreen {
            // Act
        }
    }
    
    step("Verification") {
        MainScreen {
            // Assert
        }
    }
}
```

---

## Troubleshooting

### Unit Tests Failing
- Clear build cache: `./gradlew clean`
- Check mock configurations
- Verify test dispatcher setup

### UI Tests Failing
- Ensure device/emulator is running
- Check permissions are granted
- Increase timeout for flaky operations
- Verify UI elements have correct IDs

---

## Resources

- [Kaspresso Documentation](https://github.com/KasperskyLab/Kaspresso)
- [JUnit 4 Documentation](https://junit.org/junit4/)
- [MockK Documentation](https://mockk.io/)
- [Android Testing Guide](https://developer.android.com/training/testing)

---

## Summary

✅ **28 Unit Tests** - Fast, isolated component testing  
✅ **20 UI Tests** - End-to-end user flow testing  
✅ **Kaspresso Framework** - Modern, stable UI testing  
✅ **Page Object Pattern** - Maintainable test code  
✅ **Comprehensive Coverage** - Core functionality verified  

**All tests passing!** 🎉

