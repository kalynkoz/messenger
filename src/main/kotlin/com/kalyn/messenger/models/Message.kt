package com.kalyn.messenger.models

import java.time.Instant

data class Message(
        val id: String, // Message Id
        val convoId: String, // Conversation Id
        val sender: String, // Sender Id
        val recipient: String, // Recipient Id
        val content: String, // Message content
        val sentAt: Long = Instant.now().epochSecond // Time in EpochSecond
)