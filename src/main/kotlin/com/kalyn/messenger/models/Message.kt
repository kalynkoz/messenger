package com.kalyn.messenger.models

import java.time.Instant

data class Message(val id: String, val sender: String, val recipient: String, val content: String, val sentAt: Long = Instant.now().epochSecond)

class Database() {
    companion object {
        val messagesByRecipient = mutableMapOf<String, List<Message>>()
        private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        fun generateId(): String {
            return (1..20)
                    .map { kotlin.random.Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("");
        }
    }
}