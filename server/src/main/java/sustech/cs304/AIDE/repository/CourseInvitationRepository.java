package sustech.cs304.AIDE.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import sustech.cs304.AIDE.model.CourseInvitation;

/**
 * Repository interface for managing CourseInvitation entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 */
public interface CourseInvitationRepository extends JpaRepository<CourseInvitation, Long> {

    /**
     * Find a course invitation by its ID.
     * @param id the ID of the course invitation
     * @return an Optional containing the CourseInvitation if found, or empty if not found
     */
    Optional<CourseInvitation> findById(Long id); 

    /**
     * Find list of course invitations by user ID.
     * @param userId the ID of the user
     * @return a list of CourseInvitation objects associated with the user
     */
    @Query("SELECT ci.courseId FROM CourseInvitation ci WHERE ci.userId = :userId")
    List<Long> findCourseIdByUserId(@Param("userId") String userId);

    /**
     * delete course invitations by course ID.
     * @param courseId the ID of the course
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM CourseInvitation ci WHERE ci.courseId = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);

    /**
     * Find a course invitation by course ID and user ID.
     * @param courseId the ID of the course
     * @param userId the ID of the user
     * @return an Optional containing the CourseInvitation if found, or empty if not found
     */
    @Query("SELECT ci FROM CourseInvitation ci WHERE ci.courseId = :courseId AND ci.userId = :userId")
    Optional<CourseInvitation> findByCourseIdAndUserId(@Param("courseId") Long courseId, @Param("userId") String userId);
}
