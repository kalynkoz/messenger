package com.kalyn.messenger.datastore

import com.kalyn.messenger.models.Message
import org.springframework.stereotype.Component

@Component
class Database() {
    val messagesByRecipient = mutableMapOf<String, List<Message>>()

    fun generateId(): String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..20)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");
    }
}