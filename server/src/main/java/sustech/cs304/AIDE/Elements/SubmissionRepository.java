package sustech.cs304.AIDE.Elements;

import sustech.cs304.AIDE.Elements.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findById(Long id); 

    @Query("SELECT s.id FROM Submission s WHERE s.assignmentId = :assignmentId AND s.userId = :userId")
    List<Long> findSubmissionIdByAssignmentIdAndUserId(
        @Param("assignmentId") Long assignmentId, 
        @Param("userId") String userId
    );

    @Query("SELECT s.address FROM Submission s WHERE s.id = :id")
    String findAddressById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Submission s SET s.address = :address WHERE s.id = :id")
    int updateAddressById(@Param("id") Long id, @Param("address") String address);
}
