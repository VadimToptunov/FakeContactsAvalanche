package com.toptunov.fakecontactsavalanche

import org.junit.Assert.*
import org.junit.Test

class FakeDataGeneratorTest {

    @Test
    fun `generateFullName returns non-empty string`() {
        val name = FakeDataGenerator.generateFullName()
        assertNotNull(name)
        assertTrue(name.isNotEmpty())
    }

    @Test
    fun `generateFullName contains space between first and last name`() {
        val name = FakeDataGenerator.generateFullName()
        assertTrue(name.contains(" "))
        assertEquals(2, name.split(" ").size)
    }

    @Test
    fun `generatePhoneNumber has correct format`() {
        val phone = FakeDataGenerator.generatePhoneNumber()
        assertNotNull(phone)
        assertTrue(phone.startsWith("+1-"))
        assertTrue(phone.matches(Regex("\\+1-\\d{3}-\\d{3}-\\d{4}")))
    }

    @Test
    fun `generatePhoneNumber generates valid area codes`() {
        repeat(100) {
            val phone = FakeDataGenerator.generatePhoneNumber()
            val areaCode = phone.substringAfter("+1-").substringBefore("-").toInt()
            assertTrue("Area code $areaCode should be in range 200-999", areaCode in 200..999)
        }
    }

    @Test
    fun `generatePhoneNumber generates valid prefixes`() {
        repeat(100) {
            val phone = FakeDataGenerator.generatePhoneNumber()
            val parts = phone.removePrefix("+1-").split("-")
            val prefix = parts[1].toInt()
            assertTrue("Prefix $prefix should be in range 200-999", prefix in 200..999)
        }
    }

    @Test
    fun `generatePhoneNumber generates valid line numbers`() {
        repeat(100) {
            val phone = FakeDataGenerator.generatePhoneNumber()
            val lineNumber = phone.substringAfterLast("-").toInt()
            assertTrue("Line number $lineNumber should be in range 1000-9999", lineNumber in 1000..9999)
        }
    }

    @Test
    fun `generatePhoneNumber can generate values across full range`() {
        val generatedAreaCodes = mutableSetOf<Int>()
        val generatedLineNumbers = mutableSetOf<Int>()
        
        repeat(3000) {
            val phone = FakeDataGenerator.generatePhoneNumber()
            val areaCode = phone.substringAfter("+1-").substringBefore("-").toInt()
            val lineNumber = phone.substringAfterLast("-").toInt()
            generatedAreaCodes.add(areaCode)
            generatedLineNumbers.add(lineNumber)
        }
        
        assertTrue("Area codes should include values near 999 (got max: ${generatedAreaCodes.maxOrNull()})", 
            generatedAreaCodes.any { it >= 990 })
        assertTrue("Line numbers should include values near 9999 (got max: ${generatedLineNumbers.maxOrNull()})", 
            generatedLineNumbers.any { it >= 9990 })
        assertTrue("Area codes should be diverse (got ${generatedAreaCodes.size} unique values)", 
            generatedAreaCodes.size > 500)
        assertTrue("Line numbers should be diverse (got ${generatedLineNumbers.size} unique values)", 
            generatedLineNumbers.size > 2500)
    }

    @Test
    fun `generateCompany returns non-empty string`() {
        val company = FakeDataGenerator.generateCompany()
        assertNotNull(company)
        assertTrue(company.isNotEmpty())
    }

    @Test
    fun `generateJobTitle returns non-empty string`() {
        val jobTitle = FakeDataGenerator.generateJobTitle()
        assertNotNull(jobTitle)
        assertTrue(jobTitle.isNotEmpty())
    }

    @Test
    fun `generated data is random`() {
        val names = mutableSetOf<String>()
        val phones = mutableSetOf<String>()
        
        repeat(50) {
            names.add(FakeDataGenerator.generateFullName())
            phones.add(FakeDataGenerator.generatePhoneNumber())
        }
        
        assertTrue("Names should be diverse", names.size > 10)
        assertTrue("Phone numbers should be diverse", phones.size > 40)
    }

    @Test
    fun `all generation methods are thread-safe`() {
        val results = java.util.concurrent.CopyOnWriteArrayList<String>()
        
        val threads = List(10) {
            Thread {
                repeat(10) {
                    results.add(FakeDataGenerator.generateFullName())
                    results.add(FakeDataGenerator.generatePhoneNumber())
                    results.add(FakeDataGenerator.generateCompany())
                    results.add(FakeDataGenerator.generateJobTitle())
                }
            }
        }
        
        threads.forEach { it.start() }
        threads.forEach { it.join() }
        
        assertEquals(400, results.size)
        assertTrue(results.all { it.isNotEmpty() })
    }
}

