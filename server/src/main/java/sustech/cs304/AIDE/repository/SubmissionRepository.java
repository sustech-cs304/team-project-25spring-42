package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sustech.cs304.AIDE.model.Submission;

/**
 * Repository interface for managing Submission entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 */
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    /**
     * Find a submission by its ID.
     * @param id the ID of the submission
     * @return an Optional containing the Submission if found, or empty if not found
     */
    Optional<Submission> findById(Long id); 

    /**
     * Find a list of submission IDs by assignment ID and user ID.
     * @param assignmentId the ID of the assignment
     * @param userId the ID of the user
     * @return a list of submission IDs associated with the assignment and user
     */
    @Query("SELECT s.id FROM Submission s WHERE s.assignmentId = :assignmentId AND s.userId = :userId")
    List<Long> findSubmissionIdByAssignmentIdAndUserId(
        @Param("assignmentId") Long assignmentId, 
        @Param("userId") String userId
    );

    /**
     * Find the address of a submission by its ID.
     *
     * @param id the ID of the submission
     * @return the address of the submission
     */
    @Query("SELECT s.address FROM Submission s WHERE s.id = :id")
    String findAddressById(@Param("id") Long id);

    /**
     * Find a list of submission IDs by assignment ID.
     * @param assignmentId the ID of the assignment
     * @return a list of submission IDs associated with the assignment
     */
    @Query("SELECT s FROM Submission s WHERE s.assignmentId = :assignmentId")
    List<Submission> findByAssignmentId(@Param("assignmentId") Long assignmentId);

    /**
     * Update address of a submission by its ID.
     *
     * @param id the ID of the submission
     * @param address the new address to set
     * @return the number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Submission s SET s.address = :address WHERE s.id = :id")
    int updateAddressById(@Param("id") Long id, @Param("address") String address);

    /**
     * Delete submission by assignment ID and user ID.
     *
     * @param assignmentId the ID of the assignment
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Submission s WHERE s.assignmentId = :assignmentId")
    void deleteByAssignmentId(@Param("assignmentId") Long assignmentId);

}
