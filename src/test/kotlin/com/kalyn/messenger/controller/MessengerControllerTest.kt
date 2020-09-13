package com.kalyn.messenger.controller

import com.kalyn.messenger.models.Message
import com.kalyn.messenger.service.MessengerService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
internal class MessengerControllerTest {
    private val messengerService: MessengerService = mockk()
    private val messengerController = MessengerController(messengerService)

    @Test
    fun `home returns message from service`() {
        val response = "hello there"
        every { messengerService.hello() } returns response

       assertEquals(response, messengerController.home())
    }

    @Test
    fun `getMessagesForRecipient returns a list of messages`() {
        val response = listOf(
               Message("a", "ab", "ac2", "ad3", "knock knock", 1600030415),
               Message("a4a", "ab", "ac2", "ad3", "orange", 1600030445)
        )
        every { messengerService.getMessagesByRecipient(any()) } returns response

        assertEquals(response, messengerController.getMessagesForRecipient("ad3"))
    }

    @Test
    fun `getRecentMessages returns a list of messages`() {
        val response = listOf(
                Message("a", "ab", "ac2", "ad3", "knock knock", 1600030415),
                Message("a4a", "ab", "ac2", "ad3", "orange", 1600030445)
        )

        every { messengerService.getRecentMessagesByRecipient(any()) } returns response

        assertEquals(response, messengerController.getRecentMessagesForRecipient("ad3"))
    }

    @Test
    fun `sendMessage updates db`() {
        assertDoesNotThrow {
            messengerController.sendMessage(Message("1", "2", "3", "4", "hi"))
        }
    }
}