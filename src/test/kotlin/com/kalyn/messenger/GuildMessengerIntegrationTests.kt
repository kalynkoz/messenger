package com.kalyn.messenger

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.TestContextManager

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GuildMessengerIntegrationTests {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Before
    fun before() {
        TestContextManager(javaClass).prepareTestInstance(this)
    }

    @Test
    fun `hello is successful`() {
        val response = restTemplate.getForEntity("/hello", String::class.java)

        assertTrue(response.statusCode.is2xxSuccessful)
        assertEquals("Hello from service", response.body)
    }
}