package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestBodyyTest {

    @Test
    void testConstructorAndFields() {
        RequestBodyy.Message msg1 = new RequestBodyy.Message("user", "Hello");
        RequestBodyy.Message msg2 = new RequestBodyy.Message("assistant", "Hi there!");
        List<RequestBodyy.Message> messages = Arrays.asList(msg1, msg2);

        RequestBodyy body = new RequestBodyy("gpt-3.5", messages);

        assertEquals("gpt-3.5", body.getModel());
        assertEquals(messages, body.getMessages());
        assertEquals(7, body.getModel().length());
        assertEquals("user", body.getMessages().get(0).getRole());
        assertEquals("Hello", body.getMessages().get(0).getContent());
        assertEquals("assistant", body.getMessages().get(1).getRole());
        assertEquals("Hi there!", body.getMessages().get(1).getContent());
    }

    @Test
    void testEmptyMessagesList() {
        RequestBodyy body = new RequestBodyy("test-model", Collections.emptyList());
        assertEquals("test-model", body.getModel());
        assertNotNull(body.getMessages());
        assertTrue(body.getMessages().isEmpty());
    }

    @Test
    void testNullFields() {
        RequestBodyy body = new RequestBodyy(null, null);
        assertNull(body.getModel());
        assertNull(body.getMessages());

        RequestBodyy.Message message = new RequestBodyy.Message(null, null);
        assertNull(message.getRole());
        assertNull(message.getContent());
    }

    @Test
    void testMessageContentAndRole() {
        RequestBodyy.Message message = new RequestBodyy.Message("system", "This is a system message");
        assertEquals("system", message.getRole());
        assertEquals("This is a system message", message.getContent());
    }
}