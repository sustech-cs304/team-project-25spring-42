package sustech.cs304.service;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class ChatApiImplTest extends TestCase {
    private ChatApi chatApi = new ChatApiImpl();

    @Test
    void testGetChatMessages() {
        String userId = "testUser";
        String friendId = "testFriend";
        var messages = chatApi.getChatMessages(userId, friendId);
        assertNotNull(messages);
    }

    @Test
    void testGetGroupMessages() {
        String groupId = "testGroup";
        var messages = chatApi.getGroupMessages(groupId);
        assertNotNull(messages);
    }
}
