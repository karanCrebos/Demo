package com.example.testingdemo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration

@RunWith(AndroidJUnit4::class)
class AlternativeButtonTest {
    
    private lateinit var driver: AndroidDriver
    private lateinit var wait: WebDriverWait
    
    @Before
    fun setUp() {
        val options = UiAutomator2Options()
            .setPlatformName("android")
            .setAutomationName("UiAutomator2")
            .setDeviceName("Samsung Galaxy S23")
            .setPlatformVersion("13.0")
            .setApp("bs://1dc9668f548dbdb8988d772b46c952ac48349a89")
            .setAutoGrantPermissions(true)

        // BrowserStack specific capabilities
        options.setCapability("bstack:options", mapOf(
            "projectName" to "TestingDemo",
            "buildName" to "Alternative Button Test Build",
            "sessionName" to "Alternative Button Test Session",
            "networkLogs" to true,
            "deviceLogs" to true,
            "debug" to true,
            "appiumLogs" to true
        ))
        
        // Use minimal configuration to avoid HTTP client issues
        options.setCapability("appium:useNewWDA", false)
        options.setCapability("appium:shouldTerminateApp", true)
        options.setCapability("appium:noReset", false)
        
        // BrowserStack credentials
        val username = "crebosonlinesolu1"
        val accessKey = "De8AQLcmnCaCrEtF5yy7"
        
        val browserStackUrl = "https://$username:$accessKey@hub-cloud.browserstack.com/wd/hub"
        
        try {
            driver = AndroidDriver(URL(browserStackUrl), options)
            wait = WebDriverWait(driver, Duration.ofSeconds(30))
            
            // Wait for app to load
            Thread.sleep(5000)
            println("✅ Driver initialized successfully")
        } catch (e: Exception) {
            println("❌ Error initializing driver: ${e.message}")
            throw e
        }
    }
    
    @Test
    fun testSimpleButtonClick() {
        println("🧪 Starting simple button click test...")
        
        try {
            // Wait for the app to be fully loaded and find the count text
            val countElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@text, 'Count:')]")
            ))
            
            // Get initial count value
            val initialText = countElement.text
            val initialCount = extractCountFromText(initialText)
            println("📊 Initial count: $initialCount")
            
            // Find and click the "Add" button using multiple strategies
            val addButton = try {
                wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@text='Add']")
                ))
            } catch (e: Exception) {
                println("⚠️ First strategy failed, trying alternative...")
                wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(@text, 'Add')]")
                ))
            }
            
            println("🔘 Found Add button, clicking...")
            addButton.click()
            
            // Wait a moment for the UI to update
            Thread.sleep(2000)
            
            // Get the updated count value
            val updatedCountElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@text, 'Count:')]")
            ))
            val updatedText = updatedCountElement.text
            val updatedCount = extractCountFromText(updatedText)
            println("📊 Updated count: $updatedCount")
            
            // Verify the count increased by 1
            assertEquals("Count should increase by 1 after button click", initialCount + 1, updatedCount)
            println("✅ Simple button click test passed! Count increased from $initialCount to $updatedCount")
            
        } catch (e: Exception) {
            println("❌ Test failed with exception: ${e.message}")
            throw e
        }
    }
    
    /**
     * Helper function to extract count number from text like "Count: 5"
     */
    private fun extractCountFromText(text: String): Int {
        return try {
            text.split(":").last().trim().toInt()
        } catch (e: Exception) {
            println("⚠️ Could not extract count from text: '$text'")
            0
        }
    }
    
    @After
    fun tearDown() {
        try {
            driver?.quit()
            println("🧹 Test cleanup completed")
        } catch (e: Exception) {
            println("⚠️ Error during cleanup: ${e.message}")
        }
    }
} 