package com.kalyn.messenger.service

import com.kalyn.messenger.datastore.Database
import com.kalyn.messenger.models.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class MessengerService(
        @Autowired val db: Database
) {
    val logger = Logger.getLogger(this::class.java.canonicalName)

    fun hello(): String {
        return "Hello from the messaging service!"
    }

    fun getMessagesByRecipient(rId: String): List<Message> {
        val allMessages = db.messagesByRecipient.getOrDefault(rId, emptyList())

        return allMessages
    }
}