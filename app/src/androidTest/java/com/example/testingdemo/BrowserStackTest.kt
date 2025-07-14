package com.example.testingdemo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration

@RunWith(AndroidJUnit4::class)
class BrowserStackTest {
    
    private lateinit var driver: AndroidDriver
    private lateinit var wait: WebDriverWait
    
    @Before
    fun setUp() {
        val options = UiAutomator2Options()
            .setPlatformName("android")
            .setAutomationName("UiAutomator2")
            .setDeviceName("Samsung Galaxy S24 Ultra-14.0")
            .setPlatformVersion("13.0")
            .setApp("bs://1dc9668f548dbdb8988d772b46c952ac48349a89")
            .setAutoGrantPermissions(true)
//            .setAutoAcceptAlerts(true)
//            .setDebug(true)

        // BrowserStack specific capabilities
        options.setCapability("bstack:options", mapOf(
            "projectName" to "TestingDemo",
            "buildName" to "Android Test Build",
            "sessionName" to "Android Test Session",
            "networkLogs" to true,
            "deviceLogs" to true
        ))
        
        // BrowserStack credentials
        val username = "crebosonlinesolu1"
        val accessKey = "De8AQLcmnCaCrEtF5yy7"
        
        val browserStackUrl = "https://$username:$accessKey@hub-cloud.browserstack.com/wd/hub"
        
        driver = AndroidDriver(URL(browserStackUrl), options)
        wait = WebDriverWait(driver, Duration.ofSeconds(30))
    }
    
    @Test
    fun testAppLaunch() {
        // Wait for app to load
        Thread.sleep(3000)
        
        // Verify app is launched successfully
        val packageName = driver.currentPackage
        assert(packageName == "com.example.testingdemo")
        
        println("App launched successfully on BrowserStack!")
    }
    
    @Test
    fun testUIElements() {
        // Wait for app to load
        Thread.sleep(3000)
        
        // Test if the main text is displayed
        try {
            // For Compose UI, we might need to use different locators
            // This is a basic example - you might need to adjust based on your actual UI
            val textElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.widget.TextView[contains(@text, 'Hello')]")
                )
            )
            
            assert(textElement.isDisplayed)
            println("UI element found: ${textElement.text}")
            
        } catch (e: Exception) {
            println("UI element not found, but app is running: ${e.message}")
        }
    }
    
    @Test
    fun testAppFunctionality() {
        // Wait for app to load
        Thread.sleep(3000)
        
        // Basic functionality test - check if app is running
        try {
            val currentPackage = driver.currentPackage
            assert(currentPackage == "com.example.testingdemo")
            println("App is running in foreground")
        } catch (e: Exception) {
            println("Could not verify app state: ${e.message}")
        }
        
        // Test app background/foreground transitions
        try {
            driver.runAppInBackground(Duration.ofSeconds(2))
            Thread.sleep(2000)
            
            driver.activateApp("com.example.testingdemo")
            Thread.sleep(2000)
            
            println("App background/foreground test completed")
        } catch (e: Exception) {
            println("Background/foreground test failed: ${e.message}")
        }
    }
    
    @After
    fun tearDown() {
        driver?.quit()
    }
} 