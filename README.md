# TestingDemo - BrowserStack Automation Testing

This Android project demonstrates how to set up and run automated tests using BrowserStack's cloud testing platform.

## Prerequisites

1. **BrowserStack Account**: Sign up for a free account at [BrowserStack](https://www.browserstack.com/)
2. **Android Studio**: Latest version with Android SDK
3. **Java 11**: Required for the project
4. **Gradle**: Included in the project

## Setup Instructions

### 1. Get BrowserStack Credentials

1. Log in to your BrowserStack account
2. Go to **Account Settings** → **Access Keys**
3. Copy your **Username** and **Access Key**

### 2. Configure Credentials

Update the `gradle.properties` file with your BrowserStack credentials:

```properties
BROWSERSTACK_USERNAME=your_actual_username
BROWSERSTACK_ACCESS_KEY=your_actual_access_key
```

### 3. Upload Your App to BrowserStack

1. Build your APK:
   ```bash
   ./gradlew assembleDebug
   ```

2. Upload the APK to BrowserStack:
   - Go to **App Automate** → **Upload App**
   - Upload the APK from `app/build/outputs/apk/debug/app-debug.apk`
   - Copy the `app_id` (starts with `bs://`)

3. Update the app ID in `BrowserStackTest.kt`:
   ```kotlin
   .setApp("bs://YOUR_ACTUAL_APP_ID")
   ```

### 4. Run Tests

#### Option 1: Run from Android Studio
1. Open the project in Android Studio
2. Navigate to `app/src/androidTest/java/com/example/testingdemo/BrowserStackTest.kt`
3. Right-click on the test class and select "Run 'BrowserStackTest'"

#### Option 2: Run from Command Line
```bash
./gradlew connectedAndroidTest
```

#### Option 3: Run Specific Test
```bash
# Run specific test method
./gradlew connectedAndroidTest --tests BrowserStackTest.testAppLaunch

# Run button click tests
./gradlew connectedAndroidTest --tests ButtonClickTest

# Run specific button click test
./gradlew connectedAndroidTest --tests ButtonClickTest.testButtonClickIncrementsCounter
```

## Test Structure

The project includes the following test classes:

- **BrowserStackTest.kt**: Main test class with BrowserStack integration
  - `testAppLaunch()`: Verifies app launches successfully
  - `testUIElements()`: Tests UI element visibility
  - `testAppFunctionality()`: Tests app state and transitions

- **ButtonClickTest.kt**: Specialized test class for button interaction testing
  - `testButtonClickIncrementsCounter()`: Tests single button click functionality
  - `testMultipleButtonClicks()`: Tests multiple consecutive button clicks
  - `testButtonIsClickable()`: Verifies button is present and clickable

## Configuration Files

- **browserstack.json**: BrowserStack configuration with device settings
- **gradle.properties**: Contains BrowserStack credentials
- **app/build.gradle.kts**: Project dependencies and build configuration
- **run-browserstack-tests.sh**: Script to run all BrowserStack tests
- **run-button-click-test.sh**: Script to run button click tests specifically

## Supported Devices

The configuration includes tests for:
- Samsung Galaxy S23 (Android 13)
- Google Pixel 7 (Android 13)
- OnePlus 9 (Android 12)

## Viewing Test Results

1. **BrowserStack Dashboard**: Go to **App Automate** → **Builds** to see test results
2. **Video Recording**: Each test session is recorded for debugging
3. **Logs**: Access device logs, network logs, and test logs from the dashboard

## Troubleshooting

### Common Issues

1. **Authentication Error**: Verify your BrowserStack credentials in `gradle.properties`
2. **App Not Found**: Ensure the app is uploaded and the app ID is correct
3. **Device Unavailable**: Check device availability in BrowserStack dashboard
4. **Test Timeout**: Increase wait times in test methods if needed

### Debug Mode

Enable debug mode in the test configuration:
```kotlin
.setDebug(true)
.setNetworkLogs(true)
.setDeviceLogs(true)
```

## Best Practices

1. **Environment Variables**: Use environment variables for sensitive data in production
2. **Test Isolation**: Each test should be independent and not rely on other tests
3. **Error Handling**: Implement proper exception handling in tests
4. **Wait Strategies**: Use explicit waits instead of Thread.sleep() when possible
5. **Test Data**: Use test data that doesn't conflict with other test runs

## Additional Resources

- [BrowserStack App Automate Documentation](https://www.browserstack.com/app-automate)
- [Appium Java Client Documentation](http://appium.github.io/java-client/)
- [Android Testing Guide](https://developer.android.com/training/testing)

## Support

For issues related to:
- **BrowserStack**: Contact BrowserStack support
- **Project Setup**: Check the troubleshooting section above
- **Android Testing**: Refer to Android developer documentation 