package sustech.cs304.AIDE.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import sustech.cs304.AIDE.model.CourseInvitation;

public interface CourseInvitationRepository extends JpaRepository<CourseInvitation, Long> {
    Optional<CourseInvitation> findById(Long id); 

    @Query("SELECT ci.courseId FROM CourseInvitation ci WHERE ci.userId = :userId")
    List<Long> findCourseIdByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CourseInvitation ci WHERE ci.courseId = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);

    //find by courseId and userId
    @Query("SELECT ci FROM CourseInvitation ci WHERE ci.courseId = :courseId AND ci.userId = :userId")
    Optional<CourseInvitation> findByCourseIdAndUserId(@Param("courseId") Long courseId, @Param("userId") String userId);
}
