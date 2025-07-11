package com.example.testingdemo

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration

class ButtonClickTest {
    
    private lateinit var driver: AndroidDriver
    private lateinit var wait: WebDriverWait
    
    @Before
    fun setUp() {
        // Get app ID from environment variable or use default
        val appId = System.getenv("APP_ID") ?: "bs://1dc9668f548dbdb8988d772b46c952ac48349a89"
        
        val options = UiAutomator2Options()
            .setPlatformName("android")
            .setAutomationName("UiAutomator2")
            .setDeviceName("Samsung Galaxy S23")
            .setPlatformVersion("13.0")
            .setApp(appId)
            .setAutoGrantPermissions(true)

        // BrowserStack specific capabilities
        options.setCapability("bstack:options", mapOf(
            "projectName" to "TestingDemo",
            "buildName" to "Button Click Test Build",
            "sessionName" to "Button Click Test Session",
            "networkLogs" to true,
            "deviceLogs" to true,
            "debug" to true
        ))
        
        // BrowserStack credentials
        val username = "crebosonlinesolu1"
        val accessKey = "De8AQLcmnCaCrEtF5yy7"
        
        val browserStackUrl = "https://$username:$accessKey@hub-cloud.browserstack.com/wd/hub"
        
        driver = AndroidDriver(URL(browserStackUrl), options)
        wait = WebDriverWait(driver, Duration.ofSeconds(30))
        
        // Wait for app to load
        Thread.sleep(3000)
    }
    
    @Test
    fun testButtonClickIncrementsCounter() {
        println("🧪 Starting button click test...")
        
        // Wait for the app to be fully loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.widget.TextView[contains(@text, 'Count:')]")
        ))
        
        // Get initial count value
        val initialCountElement = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.widget.TextView[contains(@text, 'Count:')]")
        ))
        val initialText = initialCountElement.text
        val initialCount = extractCountFromText(initialText)
        println("📊 Initial count: $initialCount")
        
        // Find and click the "Add" button
        val addButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//android.widget.Button[@text='Add']")
        ))
        println("🔘 Found Add button, clicking...")
        addButton.click()
        
        // Wait a moment for the UI to update
        Thread.sleep(1000)
        
        // Get the updated count value
        val updatedCountElement = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.widget.TextView[contains(@text, 'Count:')]")
        ))
        val updatedText = updatedCountElement.text
        val updatedCount = extractCountFromText(updatedText)
        println("📊 Updated count: $updatedCount")
        
        // Verify the count increased by 1
        assertEquals("Count should increase by 1 after button click", initialCount + 1, updatedCount)
        println("✅ Button click test passed! Count increased from $initialCount to $updatedCount")
    }
    
    @Test
    fun testMultipleButtonClicks() {
        println("🧪 Starting multiple button clicks test...")
        
        // Wait for the app to be fully loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.widget.TextView[contains(@text, 'Count:')]")
        ))
        
        // Get initial count
        val initialCountElement = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.widget.TextView[contains(@text, 'Count:')]")
        ))
        val initialCount = extractCountFromText(initialCountElement.text)
        println("📊 Initial count: $initialCount")
        
        // Find the Add button
        val addButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//android.widget.Button[@text='Add']")
        ))
        
        // Click the button multiple times
        val clickCount = 3
        for (i in 1..clickCount) {
            println("🔘 Clicking Add button (click $i of $clickCount)...")
            addButton.click()
            Thread.sleep(500) // Small delay between clicks
        }
        
        // Get final count
        val finalCountElement = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.widget.TextView[contains(@text, 'Count:')]")
        ))
        val finalCount = extractCountFromText(finalCountElement.text)
        println("📊 Final count: $finalCount")
        
        // Verify the count increased by the number of clicks
        assertEquals("Count should increase by $clickCount after multiple clicks", 
                    initialCount + clickCount, finalCount)
        println("✅ Multiple clicks test passed! Count increased from $initialCount to $finalCount")
    }
    
    @Test
    fun testButtonIsClickable() {
        println("🧪 Testing button clickability...")
        
        // Wait for the app to be fully loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//android.widget.TextView[contains(@text, 'Count:')]")
        ))
        
        // Verify the Add button is present and clickable
        val addButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//android.widget.Button[@text='Add']")
        ))
        
        // Verify button properties
        assert(addButton.isDisplayed) { "Add button should be displayed" }
        assert(addButton.isEnabled) { "Add button should be enabled" }
        assertEquals("Add", addButton.text) { "Button text should be 'Add'" }
        
        println("✅ Button clickability test passed!")
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