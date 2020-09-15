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
    val LIMIT = 100
    private val STALE_DAYS: Long = 30

    val logger = Logger.getLogger(this::class.java.canonicalName)

    // non-private methods could be extracted to interface
    fun health(): String {
        return "Hello from the messaging service!"
    }

    fun addMessageByRecipient(message: Message) {
        val rId = message.recipient
        val allMessages = getMessages(rId)

        db.messagesByRecipient[rId] = allMessages.plus(message)
    }

    fun getMessagesWithLimit(rId: String? = null): List<Message> {
        val allMessages = getMessages(rId)

        if(allMessages.size > LIMIT) {
            logger.warning("$rId has ${allMessages.size} messages, returning first $LIMIT")
            return allMessages.subList(0, LIMIT)
        }

        return allMessages
    }

    fun getRecentMessages(rId: String? = null): List<Message> {
        val allMessages = getMessages(rId)

        return allMessages.filter { it.sentAt > Instant.now().minus(STALE_DAYS, ChronoUnit.DAYS).epochSecond }
    }

    private fun getMessages(rId: String? = null): List<Message> {
        val allMessages = if(rId.isNullOrEmpty()) {
            db.messagesByRecipient.values.flatten()
        } else {
            db.messagesByRecipient.getOrDefault(rId, emptyList())
        }

        if (allMessages.isEmpty()) {
            logger.warning("$rId was not found in the database")
        }

        return allMessages
    }
}