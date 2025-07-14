plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.testingdemo"
    compileSdk = 35
    
    configurations.all {
        resolutionStrategy {
            force("org.seleniumhq.selenium:selenium-api:3.141.59")
            force("org.seleniumhq.selenium:selenium-support:3.141.59")
            force("org.seleniumhq.selenium:selenium-remote-driver:3.141.59")
            force("org.seleniumhq.selenium:selenium-chrome-driver:3.141.59")
        }
    }

    defaultConfig {
        applicationId = "com.example.testingdemo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // BrowserStack configuration
        manifestPlaceholders["BROWSERSTACK_USERNAME"] = project.findProperty("BROWSERSTACK_USERNAME") ?: ""
        manifestPlaceholders["BROWSERSTACK_ACCESS_KEY"] = project.findProperty("BROWSERSTACK_ACCESS_KEY") ?: ""
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packagingOptions {
        resources {
            excludes += setOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/*.kotlin_module",
                "META-INF/versions/9/previous-compilation-data.bin"
            )
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    
    // BrowserStack dependencies - using Selenium 3.x for Android compatibility
    androidTestImplementation("io.appium:java-client:7.6.0") {
        exclude(group = "commons-logging", module = "commons-logging")
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    androidTestImplementation("org.seleniumhq.selenium:selenium-java:3.141.59") {
        exclude(group = "commons-logging", module = "commons-logging")
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    androidTestImplementation("io.github.bonigarcia:webdrivermanager:4.5.3") {
        exclude(group = "commons-logging", module = "commons-logging")
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    
    // Force specific versions to resolve conflicts
    androidTestImplementation("org.seleniumhq.selenium:selenium-api:3.141.59")
    androidTestImplementation("org.seleniumhq.selenium:selenium-support:3.141.59")
    androidTestImplementation("org.seleniumhq.selenium:selenium-remote-driver:3.141.59")
    
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}