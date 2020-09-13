package com.kalyn.messenger.service

import com.kalyn.messenger.datastore.Database
import com.kalyn.messenger.models.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.logging.Logger

@Service
class MessengerService(
        @Autowired val db: Database
) {
    private val LIMIT = 100
    private val STALE_DAYS: Long = 30

    val logger = Logger.getLogger(this::class.java.canonicalName)

    fun hello(): String {
        return "Hello from the messaging service!"
    }

    fun getMessagesByRecipient(rId: String): List<Message> {
        val allMessages = getMessages(rId)
        if(allMessages.size > LIMIT) {
            logger.warning("$rId has ${allMessages.size} returning first $LIMIT")
            return allMessages.subList(0, LIMIT)
        }

        return allMessages
    }

    fun getRecentMessagesByRecipient(rId: String): List<Message> {
        val allMessages = getMessages(rId)

        return allMessages.filter { it.sentAt > Instant.now().minus(STALE_DAYS, ChronoUnit.DAYS).epochSecond }
    }

    private fun getMessages(rId: String): List<Message>{
        val allMessages = db.messagesByRecipient.getOrDefault(rId, emptyList())

        if (allMessages.isEmpty()) {
            logger.warning("$rId was not found in the database")
        }

        return allMessages
    }
}