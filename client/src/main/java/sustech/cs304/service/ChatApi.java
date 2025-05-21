package sustech.cs304.service;

import java.util.List;
import sustech.cs304.entity.ChatMessage;

public interface ChatApi {
    List<ChatMessage> getChatMessages(String userId, String friendId);
}
