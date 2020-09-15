package com.kalyn.messenger.controller

import com.kalyn.messenger.models.Message
import com.kalyn.messenger.service.MessengerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class MessengerController
@Autowired constructor(private val messengerService: MessengerService) {
    @GetMapping("/health")
    fun health(): String {
        return messengerService.health()
    }

    @PostMapping("/messages")
    fun sendMessage(
            @RequestBody message: Message
    ): Message {
        messengerService.addMessageByRecipient(message)
        return message
    }

    @GetMapping("/messages/{recipientId}")
    fun getMessagesForRecipient(
            @PathVariable recipientId: String
    ): List<Message> {
        return messengerService.getMessagesWithLimit(recipientId)
    }

    @GetMapping("/messages/recent/{recipientId}")
    fun getRecentMessagesForRecipient(
            @PathVariable recipientId: String
    ): List<Message> {
        return messengerService.getRecentMessages(recipientId)
    }

    @GetMapping("/messages")
    fun getMessages(): List<Message> {
        return messengerService.getMessagesWithLimit()
    }

    @GetMapping("/messages/recent")
    fun getRecentMessages(): List<Message> {
        return messengerService.getRecentMessages()
    }
}
