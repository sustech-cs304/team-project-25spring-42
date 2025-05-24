package sustech.cs304.AIDE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sustech.cs304.AIDE.model.Friendship;

/**
 * Repository interface for managing Friendship entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 */
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    /**
     * Find a friendship by the applicant ID and target ID.
     * @param applicantId the ID of the applicant
     * @param targetId the ID of the target
     * @return a Friendship object if found, or null if not found
     */
    @Query("SELECT f FROM Friendship f WHERE (f.userId1 = ?1 AND f.userId2 = ?2) OR (f.userId1 = ?2 AND f.userId2 = ?1)")
    Friendship findByApplicantIdAndTargetId(String applicantId, String targetId);

    /**
     * Find all friendships where the user is either userId1 or userId2.
     * @param userId the ID of the user
     * @return a list of Friendship objects associated with the user
     */
    @Query("SELECT f FROM Friendship f WHERE f.userId1 = ?1 OR f.userId2 = ?1")
    List<Friendship> findByUserId(String userId);
}
