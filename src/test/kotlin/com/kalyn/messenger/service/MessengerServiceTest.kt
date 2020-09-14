package com.kalyn.messenger.service

import com.kalyn.messenger.datastore.Database
import com.kalyn.messenger.models.Message
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.Instant
import java.time.temporal.ChronoUnit

internal class MessengerServiceTest {
    private val db = Database()
    private val service = MessengerService(db)

    private val RID = "af132a4gga"
    private val RID2 = "h2a3g4asn3t"

    @BeforeEach
    fun before() {
        db.messagesByRecipient.clear()
    }

    @Test
    fun hello() {
        assertNotNull(service.hello())
    }

    @Test
    fun `add message for new recipient`() {
        val message = Message(
                convoId = "123",
                sender = "ag4a6a3g4dfg",
                recipient = RID,
                content = "doctor who?",
                sentAt = 1600039924
        )
        assertFalse(db.messagesByRecipient.containsKey(RID))

        service.addMessageByRecipient(message)
        assertTrue(db.messagesByRecipient.containsKey(RID))
        assertEquals(listOf(message), db.messagesByRecipient[RID])
    }

    @Test
    fun `add message for current recipient`() {
        val messages = getMessagesForDB()
        addToDB(messages)
        assertTrue(db.messagesByRecipient.containsKey(RID))

        val message = Message(
                convoId = "123",
                sender = "ag4a6a3g4dfg",
                recipient = RID,
                content = "doctor who?",
                sentAt = 1600039924
        )

        service.addMessageByRecipient(message)
        assertTrue(db.messagesByRecipient.containsKey(RID))
        assertEquals(messages.plus(message), db.messagesByRecipient[RID])
    }

    @Test
    fun `getMessagesByRecipient returns empty list when recipient is not found`() {
        val response = service.getMessagesByRecipient(RID)
        assertEquals(emptyList<Message>(), response)
    }

    @Test
    fun `getMessagesByRecipient returns a list of messages`() {
        val messages = getMessagesForDB()
        addToDB(messages)

        val response = service.getMessagesByRecipient(RID)
        assertEquals(messages.size, response.size)
        assertEquals(messages, response)
    }

    @Test
    fun `getMessagesByRecipient returns a list of limited messages`() {
        val messages = getMoreThanLimitMessages()
        addToDB(messages)

        val response = service.getMessagesByRecipient(RID)
        assertEquals(service.LIMIT, response.size)
    }

    @Test
    fun `getAllMessages returns empty list when no recipient is not found`() {
        val response = service.getAllMessages()
        assertEquals(emptyList<Message>(), response)
    }

    @Test
    fun `getAllMessages returns list of messages`() {
        val messages = getMessagesForDB().plus(getMessagesForDB(RID2))
        addToDB(messages)

        val response = service.getAllMessages()
        assertEquals(messages.size, response.size)
        assertEquals(messages, response)
    }

    @Test
    fun `getAllMessages returns a list of limited messages`() {
        val messages = getMoreThanLimitMessages(RID2).plus(getMessagesForDB())
        addToDB(messages)

        val response = service.getAllMessages()
        assertEquals(service.LIMIT, response.size)
    }

    @Test
    fun `getRecentMessagesByRecipient returns a list of messages`() {
        val messages = getMessagesForDB()
        addToDB(messages)

        val response = service.getRecentMessagesByRecipient(RID)
        assertEquals(messages.size, response.size)
        assertEquals(messages, response)
    }

    @Test
    fun `getRecentMessagesByRecipient removes messages older than 30 days`() {
        val recentMessages = getMessagesForDB()
        val oldMessage = Message(
                convoId = "123",
                sender = "ag4a6a3g4dfg",
                recipient = RID,
                content = "doctor who?",
                sentAt = Instant.now().minus(32, ChronoUnit.DAYS).epochSecond
        )
        addToDB(recentMessages.plus(oldMessage))

        val response = service.getRecentMessagesByRecipient(RID)
        assertEquals(recentMessages.size, response.size)
        assertFalse(response.contains(oldMessage))
    }

    @Test
    fun `getRecentMessages returns a list of messages`() {
        val messages = getMessagesForDB().plus(getMessagesForDB(RID2))
        addToDB(messages)

        val response = service.getRecentMessages()
        assertEquals(messages.size, response.size)
        assertEquals(messages, response)
    }

    @Test
    fun `getRecentMessages removes messages older than 30 days`() {
        val recentMessages = getMessagesForDB().plus(getMessagesForDB(RID2))
        val oldMessage = Message(
                convoId = "123",
                sender = "ag4a6a3g4dfg",
                recipient = RID,
                content = "doctor who?",
                sentAt = Instant.now().minus(32, ChronoUnit.DAYS).epochSecond
        )
        addToDB(recentMessages.plus(oldMessage))

        val response = service.getRecentMessagesByRecipient(RID)
        assertEquals(recentMessages.size, response.size)
        assertFalse(response.contains(oldMessage))
    }

    private fun getMessagesForDB(rid: String = RID): List<Message> {
        val listToAdd = mutableListOf<Message>()

        for(i in 1..15) {
            listToAdd.add(
                Message(
                    convoId = "123",
                    sender = "ag4a6a3g4dfg",
                    recipient = rid,
                    content = "HI!".repeat(i),
                    sentAt = Instant.now().minus(2, ChronoUnit.DAYS).epochSecond
                ))
        }

        return listToAdd
    }

    private fun getMoreThanLimitMessages(rid: String = RID): List<Message> {
        val listToAdd = mutableListOf<Message>()

        for(i in 1..105) {
            listToAdd.add(
                    Message(
                            convoId = "123",
                            sender = "ag4a6a3g4dfg",
                            recipient = rid,
                            content = "hey ".repeat(i),
                            sentAt = Instant.now().epochSecond
                    ))
        }

        return listToAdd
    }

    private fun addToDB(messages: List<Message>) {
        db.messagesByRecipient[RID] = messages
    }
}