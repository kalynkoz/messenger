package com.kalyn.messenger.service

import com.kalyn.messenger.datastore.Database
import com.kalyn.messenger.models.Message
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.time.temporal.ChronoUnit

internal class MessengerServiceTest {
    private val db = Database()
    private val service = MessengerService(db)

    private val RID = "af132a4gga"

    @BeforeEach
    fun before() {
        db.messagesByRecipient.clear()
    }

    @Test
    fun hello() {
        assertNotNull(service.hello())
    }

    @Test
    fun `getMessagesByRecipient returns a list of messages`() {
        val messages = addMessagesToDB()

        val response = service.getMessagesByRecipient(RID)
        assertEquals(messages.size, response.size)
        assertEquals(messages, response)
    }

    @Test
    fun `getMessagesByRecipient returns empty list when recipient is not found`() {
        val response = service.getMessagesByRecipient(RID)
        assertEquals(emptyList<Message>(), response)
    }

    @Test
    fun `getMessagesByRecipient returns a list of 100 messages`() {
        addMoreThanLimit()

        val response = service.getMessagesByRecipient(RID)
        assertEquals(100, response.size)
    }

    fun addMessagesToDB(): List<Message> {
        val listToAdd = mutableListOf<Message>()

        for(i in 1..15) {
            listToAdd.add(
                Message(
                    id= db.generateId(),
                    convoId = "123",
                    sender = "ag4a6a3g4dfg",
                    recipient = "agagh5a3h",
                    content = "HI!".repeat(i),
                    sentAt = Instant.now().minus(2, ChronoUnit.DAYS).epochSecond
                ))
        }

        db.messagesByRecipient[RID] = listToAdd
        return listToAdd
    }

    fun addMoreThanLimit(): List<Message> {
        val listToAdd = mutableListOf<Message>()

        for(i in 1..105) {
            listToAdd.add(
                    Message(
                            id= db.generateId(),
                            convoId = "123",
                            sender = "ag4a6a3g4dfg",
                            recipient = "agagh5a3h",
                            content = "hey ".repeat(i),
                            sentAt = Instant.now().minus(32, ChronoUnit.DAYS).epochSecond
                    ))
        }

        db.messagesByRecipient[RID] = listToAdd
        return listToAdd
    }
}