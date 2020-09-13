package com.kalyn.messenger.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MessengerServiceTest {
    private val service = MessengerService()

    @Test
    fun hello() {
        assertEquals("Hello from service", service.hello())
    }
}