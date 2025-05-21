package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sustech.cs304.AIDE.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT c FROM ChatMessage c WHERE (c.senderId = :userId1 AND c.receiverId = :userId2) OR (c.senderId = :userId2 AND c.receiverId = :userId1)")
    List<ChatMessage> findDialogMessages(@Param("userId1") String userId1, @Param("userId2") String userId2);
}
