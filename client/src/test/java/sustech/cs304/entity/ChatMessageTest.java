package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    @Test
    void testDefaultConstructorAndSetters() {
        ChatMessage msg = new ChatMessage();

        assertNull(msg.getSenderId());
        assertNull(msg.getReceiverId());
        assertNull(msg.getMessage());
        assertNull(msg.getTimestamp());

        msg.setSenderId("userA");
        msg.setReceiverId("userB");
        msg.setMessage("Hello!");
        LocalDateTime time = LocalDateTime.of(2025, 5, 27, 10, 0, 0);
        msg.setTimestamp(time);

        assertEquals("userA", msg.getSenderId());
        assertEquals("userB", msg.getReceiverId());
        assertEquals("Hello!", msg.getMessage());
        assertEquals(time, msg.getTimestamp());
    }

    @Test
    void testParameterizedConstructor() {
        LocalDateTime time = LocalDateTime.of(2025, 5, 27, 15, 30, 0);
        ChatMessage msg = new ChatMessage("user1", "user2", "Test message", time);

        assertEquals("user1", msg.getSenderId());
        assertEquals("user2", msg.getReceiverId());
        assertEquals("Test message", msg.getMessage());
        assertEquals(time, msg.getTimestamp());
    }

    @Test
    void testNullFields() {
        ChatMessage msg = new ChatMessage(null, null, null, null);

        assertNull(msg.getSenderId());
        assertNull(msg.getReceiverId());
        assertNull(msg.getMessage());
        assertNull(msg.getTimestamp());
    }

    @Test
    void testSettersOverwriteFields() {
        ChatMessage msg = new ChatMessage("a", "b", "msg", LocalDateTime.now());

        msg.setSenderId("x");
        msg.setReceiverId("y");
        msg.setMessage("new message");
        LocalDateTime newTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        msg.setTimestamp(newTime);

        assertEquals("x", msg.getSenderId());
        assertEquals("y", msg.getReceiverId());
        assertEquals("new message", msg.getMessage());
        assertEquals(newTime, msg.getTimestamp());
    }
}