package sustech.cs304.service;

import java.util.List;
import sustech.cs304.entity.ChatMessage;

/*
 * ChatApi is an interface that defines methods for retrieving chat messages.
 * It provides methods to get messages between two users and messages in a group.
 */
public interface ChatApi {
    /**
     * Retrieves chat messages between two users.
     *
     * @param userId The ID of the user.
     * @param friendId The ID of the friend.
     * @return A list of chat messages between the two users.
     */
    List<ChatMessage> getChatMessages(String userId, String friendId);
    /**
     * Retrieves chat messages in a group.
     *
     * @param groupId The ID of the group.
     * @return A list of chat messages in the group.
     */
    List<ChatMessage> getGroupMessages(String groupId);
}
