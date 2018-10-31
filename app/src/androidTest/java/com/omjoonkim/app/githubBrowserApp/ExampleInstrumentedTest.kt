package com.omjoonkim.app.githubBrowserApp

import androidx.test.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test

class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        Assert.assertEquals("com.omjoonkim.app.githubBrowserApp", appContext.packageName)
    }
}
