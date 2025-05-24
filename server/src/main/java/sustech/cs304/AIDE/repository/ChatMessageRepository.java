package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sustech.cs304.AIDE.model.ChatMessage;

/**
 * Repository interface for managing ChatMessage entities.
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * Find messages between two users and sort by timestamp
     *
     * @param userId1 the ID of the first user
     * @param userId2 the ID of the second user
     * @return the list of chat messages
     */
    @Query("SELECT cm FROM ChatMessage cm WHERE (cm.senderId = :userId1 AND cm.receiverId = :userId2) OR (cm.senderId = :userId2 AND cm.receiverId = :userId1) ORDER BY cm.timestamp ASC")
    List<ChatMessage> findDialogMessages(@Param("userId1") String userId1, @Param("userId2") String userId2);

    /**
     * Find messages in a group and sort by timestamp
     *
     * @param groupId the ID of the group
     * @return the list of chat messages
     */
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.receiverId = :groupId ORDER BY cm.timestamp ASC")
    List<ChatMessage> findGroupMessages(@Param("groupId") String groupId);
}
