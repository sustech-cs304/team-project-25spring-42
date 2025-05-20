package sustech.cs304.AIDE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sustech.cs304.AIDE.model.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f FROM Friendship f WHERE (f.userId1 = ?1 AND f.userId2 = ?2) OR (f.userId1 = ?2 AND f.userId2 = ?1)")
    Friendship findByApplicantIdAndTargetId(String applicantId, String targetId);

    @Query("SELECT f FROM Friendship f WHERE f.userId1 = ?1 OR f.userId2 = ?1")
    List<Friendship> findByUserId(String userId);
}
