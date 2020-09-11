package com.kalyn.messenger.controller

import com.kalyn.messenger.service.MessengerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
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
}
