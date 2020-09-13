package com.kalyn.messenger.controller

import com.kalyn.messenger.service.MessengerService
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Test
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
}