package com.kalyn.messenger.controller

import com.kalyn.messenger.models.Database
import com.kalyn.messenger.service.MessengerService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
internal class MessengerControllerTest {
    private val db = Database.messagesByRecipient

    private val messengerService: MessengerService = mockk()
    private val messengerController = MessengerController(messengerService)

    @Test
    fun `home returns message from service`() {
        val response = "hello there"
        every { messengerService.hello() } returns response

       assertEquals(response, messengerController.home())
    }

    @Test
    fun `getMessages returns a list of messages`() {
        assertDoesNotThrow {
            messengerController.getMessages("000000")
        }
    }

    @Test
    fun `sendMessage updates db`() {
        
    }
}