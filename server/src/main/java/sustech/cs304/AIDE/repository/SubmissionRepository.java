package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sustech.cs304.AIDE.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findById(Long id); 

    @Query("SELECT s.id FROM Submission s WHERE s.assignmentId = :assignmentId AND s.userId = :userId")
    List<Long> findSubmissionIdByAssignmentIdAndUserId(
        @Param("assignmentId") Long assignmentId, 
        @Param("userId") String userId
    );

    @Query("SELECT s.address FROM Submission s WHERE s.id = :id")
    String findAddressById(@Param("id") Long id);

    @Query("SELECT s FROM Submission s WHERE s.assignmentId = :assignmentId")
    List<Submission> findByAssignmentId(@Param("assignmentId") Long assignmentId);

    @Modifying
    @Transactional
    @Query("UPDATE Submission s SET s.address = :address WHERE s.id = :id")
    int updateAddressById(@Param("id") Long id, @Param("address") String address);

    // delete by assignmentid
    @Modifying
    @Transactional
    @Query("DELETE FROM Submission s WHERE s.assignmentId = :assignmentId")
    void deleteByAssignmentId(@Param("assignmentId") Long assignmentId);

}
