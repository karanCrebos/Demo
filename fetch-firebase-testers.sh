#!/bin/bash

# Fetch Firebase Testers Script
# This script fetches testers from Firebase Console and formats them for distribution

echo "🔍 Fetching Firebase testers..."

# Check if Firebase CLI is installed
if ! command -v firebase &> /dev/null; then
    echo "❌ Firebase CLI not found. Installing..."
    npm install -g firebase-tools
fi

# Check if credentials are set
if [ -z "$GOOGLE_APPLICATION_CREDENTIALS" ]; then
    echo "❌ GOOGLE_APPLICATION_CREDENTIALS not set"
    exit 1
fi

# Create service account file if needed
if [ ! -f "firebase.json" ] && [ ! -z "$FIREBASE_SERVICE_ACCOUNT" ]; then
    echo "📝 Creating service account file..."
    echo "$FIREBASE_SERVICE_ACCOUNT" > firebase.json
fi

# Fetch testers from Firebase Console
echo "📋 Fetching testers from Firebase Console..."

# Method 1: Use Firebase CLI to list testers (if available)
if firebase appdistribution:testers:list --app 1:162093106925:android:088dfebbbdfb1d9de22ee4 &> /dev/null; then
    echo "✅ Found testers via Firebase CLI"
    TESTERS=$(firebase appdistribution:testers:list --app 1:162093106925:android:088dfebbbdfb1d9de22ee4 --format=json | jq -r '.[] | .email' | tr '\n' ',')
    echo "📧 Testers: $TESTERS"
    echo "TESTERS=$TESTERS" >> $GITHUB_ENV
else
    echo "⚠️  Could not fetch testers via CLI, using fallback..."
    
    # Method 2: Use predefined testers from environment
    if [ ! -z "$FIREBASE_TESTERS" ]; then
        echo "📧 Using testers from FIREBASE_TESTERS: $FIREBASE_TESTERS"
        echo "TESTERS=$FIREBASE_TESTERS" >> $GITHUB_ENV
    else
        echo "❌ No testers found. Please set FIREBASE_TESTERS environment variable"
        exit 1
    fi
fi

echo "✅ Testers fetched successfully!" 