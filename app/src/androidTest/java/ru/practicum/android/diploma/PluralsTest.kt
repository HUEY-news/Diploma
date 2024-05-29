package ru.practicum.android.diploma

import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Locale

class PluralsTest {

    @Test
    fun testPlurals() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val resources = context.resources
        val config = resources.configuration

        val ruLocale = Locale("ru")
        Locale.setDefault(ruLocale)
        config.setLocale(ruLocale)
        context.createConfigurationContext(config)

        val expectedResults = arrayOf(
            "0 вакансий",
            "1 вакансия",
            "4 вакансии",
            "10 вакансий"
        )
        val quantities = intArrayOf(0, 1, 4, 10)
        for (i in quantities.indices) {
            val expectedResult = expectedResults[i]
            val actualResult =
                context.resources.getQuantityString(R.plurals.plurals_vacancy, quantities[i], quantities[i])
            assertEquals(expectedResult, actualResult)
        }
    }
}
