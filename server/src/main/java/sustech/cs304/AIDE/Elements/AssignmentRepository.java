package sustech.cs304.AIDE.Elements;

import sustech.cs304.AIDE.Elements.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment> findById(Long id); 

    @Query("SELECT a.id FROM Assignment a WHERE a.courseId = :courseId")
    List<Long> findAssignmentIdByCourseId(@Param("courseId") String courseId);

    @Query("SELECT a.id FROM Assignment a WHERE a.courseId = :courseId AND a.visible = true")
    List<Long> findVisibleAssignmentIdByCourseId(@Param("courseId") String courseId);

    @Query("SELECT a.assignmentName FROM Assignment a WHERE a.id = :id")
    String findAssignmentNameById(@Param("id") Long id);

    @Query("SELECT a.deadline FROM Assignment a WHERE a.id = :id")
    LocalDateTime findDeadlineById(@Param("id") Long id);

    @Query("SELECT a.courseId FROM Assignment a WHERE a.id = :id")
    String findCourseIdById(@Param("id") Long id);
}
