package com.kalyn.messenger.service

import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class MessengerService {
    val logger = Logger.getLogger(this::class.java.canonicalName)

    fun hello(): String {
        logger.info("HELLO CALLED")
        return "Hello from service"
    }
}