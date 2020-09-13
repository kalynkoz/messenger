package com.kalyn.messenger.controller

import com.kalyn.messenger.models.Message
import com.kalyn.messenger.service.MessengerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
class MessengerController
@Autowired constructor(private val messengerService: MessengerService)
{
    @GetMapping("/hello")
    fun home(): String {
        return messengerService.hello()
    }

    // No more than 100 messages sent
    @GetMapping("/messages/{recipientId}")
    fun getMessagesForRecipient(
        @PathVariable recipientId: String
    ) : List<Message> {
        return messengerService.getMessagesByRecipient(recipientId)
    }

    @GetMapping("/messages/recent/{recipientId}")
    fun getRecentMessages(
            @PathVariable recipientId: String
    ) : List<Message> {
        TODO()
    }

    @PostMapping("/message")
    fun sendMessage(
            @RequestBody message: Message
    ) {
        TODO()
    }
}
