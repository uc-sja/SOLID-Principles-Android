package com.codetutor.countryinfoapp

import com.codetutor.countryinfoapp.database.Converters
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val converters = Converters<Any>()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testFromJsonStringToList() {
        val jsonString = "[\"India\",\"USA\",\"UK\"]"
        val expectedList = listOf("India", "USA", "UK")
        val actualList = converters.fromJsonStringToList(jsonString)
        assertEquals(expectedList, actualList)
    }

    @Test
    fun testFromStringListToJson() {
        val list = listOf("India", "USA", "UK")
        val expectedJsonString = "[\"India\",\"USA\",\"UK\"]"
        val actualJsonString = converters.fromStringListToJson(list)
        assertEquals(expectedJsonString, actualJsonString)
    }
}