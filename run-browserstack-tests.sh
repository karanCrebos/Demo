#!/bin/bash

# BrowserStack Test Runner Script
# This script helps you run BrowserStack tests easily

echo "🚀 BrowserStack Test Runner"
echo "=========================="

# Check if credentials are set
if [ -z "$BROWSERSTACK_USERNAME" ] || [ -z "$BROWSERSTACK_ACCESS_KEY" ]; then
    echo "❌ BrowserStack credentials not found in environment variables"
    echo "Please set BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY"
    echo "Or update gradle.properties with your credentials"
    exit 1
fi

echo "✅ BrowserStack credentials found"

# Build the project
echo "📦 Building project..."
./gradlew assembleDebug

if [ $? -ne 0 ]; then
    echo "❌ Build failed"
    exit 1
fi

echo "✅ Build successful"

# Check if APK exists
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
if [ ! -f "$APK_PATH" ]; then
    echo "❌ APK not found at $APK_PATH"
    exit 1
fi

echo "📱 APK found: $APK_PATH"
echo "📏 APK size: $(du -h "$APK_PATH" | cut -f1)"

# Ask user if they want to run tests
echo ""
echo "Do you want to run BrowserStack tests? (y/n)"
read -r response

if [[ "$response" =~ ^([yY][eE][sS]|[yY])$ ]]; then
    echo "🧪 Running BrowserStack tests..."
    
    # Run all tests
    ./gradlew connectedAndroidTest
    
    if [ $? -eq 0 ]; then
        echo "✅ All tests passed!"
    else
        echo "❌ Some tests failed. Check the logs above."
        exit 1
    fi
else
    echo "⏭️  Skipping test execution"
    echo ""
    echo "To run tests manually:"
    echo "  ./gradlew connectedAndroidTest"
    echo ""
    echo "To run a specific test:"
    echo "  ./gradlew connectedAndroidTest --tests BrowserStackTest.testAppLaunch"
fi

echo ""
echo "🎉 Done! Check BrowserStack dashboard for detailed results." 