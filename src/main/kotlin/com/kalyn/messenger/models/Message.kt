package com.kalyn.messenger.models

import com.kalyn.messenger.datastore.Database
import java.time.Instant

data class Message(
        val conversationId: String, // Conversation Id
        val sender: String, // Sender Id
        val recipient: String, // Recipient Id
        val content: String, // Message content
        val sentAt: Long = Instant.now().epochSecond, // Time in EpochSecond
        val id: String = Database().generateId() // Message Id
)