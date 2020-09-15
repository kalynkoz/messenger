package com.kalyn.messenger.controller

import com.kalyn.messenger.models.Message
import com.kalyn.messenger.service.MessengerService
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

internal class MessengerControllerTest {
    private val messengerService: MessengerService = mockk()
    private val messengerController = MessengerController(messengerService)

    @Test
    fun `home returns message from service`() {
        val response = "hello there"
        every { messengerService.health() } returns response

       assertEquals(response, messengerController.health())
    }

    @Test
    fun `sendMessage updates db`() {
        every { messengerService.addMessageByRecipient(any()) } just runs

        val message = Message("1", "2", "3", "hi")
        val response = messengerController.sendMessage(message)

        assertTrue(response.id.isNotEmpty())
    }

    @Test
    fun `getMessagesForRecipient returns a list of messages`() {
        val response = listOf(
               Message("a", "ab", "ac2", "knock knock", 1600030415),
               Message("a", "ab", "ac2",  "orange", 1600030445)
        )
        every { messengerService.getMessagesWithLimit(any()) } returns response

        assertEquals(response, messengerController.getMessagesForRecipient("ad3"))
    }

    @Test
    fun `getRecentMessagesByRecipient returns a list of messages`() {
        val response = listOf(
                Message("a", "ab", "ac2", "knock knock", 1600030415),
                Message("a", "ab", "ac2",  "orange", 1600030445)
        )

        every { messengerService.getRecentMessages(any()) } returns response

        assertEquals(response, messengerController.getRecentMessagesForRecipient("ad3"))
    }

    @Test
    fun `getMessages returns a list of messages`() {
        val response = listOf(
                Message("a", "ab", "ac2","knock knock", 1600030415),
                Message("a", "ab", "ac2",  "orange", 1600030445)
        )
        every { messengerService.getMessagesWithLimit() } returns response

        assertEquals(response, messengerController.getMessages())
    }

    @Test
    fun `getRecentMessages returns a list of messages`() {
        val response = listOf(
                Message("a", "ab", "ac2", "knock knock", 1600030415),
                Message("a4a", "ab", "ac2", "orange", 1600030445)
        )
        every { messengerService.getRecentMessages() } returns response

        assertEquals(response, messengerController.getRecentMessages())
    }
}