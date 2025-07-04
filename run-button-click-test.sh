#!/bin/bash

# Button Click Test Runner Script
# This script runs the button click test on BrowserStack

echo "🔘 Button Click Test Runner"
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

# Ask user if they want to run the button click test
echo ""
echo "Do you want to run the Button Click Test on BrowserStack? (y/n)"
read -r response

if [[ "$response" =~ ^([yY][eE][sS]|[yY])$ ]]; then
    echo "🔘 Running Button Click Test on BrowserStack..."
    
    # Run the specific button click test
    ./gradlew connectedAndroidTest --tests ButtonClickTest
    
    if [ $? -eq 0 ]; then
        echo "✅ Button Click Test passed!"
    else
        echo "❌ Button Click Test failed. Check the logs above."
        exit 1
    fi
else
    echo "⏭️  Skipping test execution"
    echo ""
    echo "To run the button click test manually:"
    echo "  ./gradlew connectedAndroidTest --tests ButtonClickTest"
    echo ""
    echo "To run all tests:"
    echo "  ./gradlew connectedAndroidTest"
fi

echo ""
echo "🎉 Done! Check BrowserStack dashboard for detailed results." 