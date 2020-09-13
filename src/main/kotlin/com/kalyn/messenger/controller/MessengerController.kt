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
    val logger = Logger.getLogger(this::class.java.canonicalName)

    @GetMapping("/hello")
    fun home(): String {
        return messengerService.hello()
    }

    @GetMapping("/messages/{userId}")
    fun getMessages(
        @PathVariable userId: String
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
