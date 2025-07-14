package com.example.testingdemo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleTest {
    
    @Test
    fun testAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.testingdemo", appContext.packageName)
    }
    
    @Test
    fun testBasicAssertion() {
        // Simple test to verify basic functionality
        val result = 2 + 2
        assertEquals(4, result)
    }
} 