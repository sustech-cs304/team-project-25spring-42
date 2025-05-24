package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sustech.cs304.AIDE.model.Enrollment;

/**
 * Repository interface for managing Enrollment entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 */
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    /**
     * Find an enrollment by its ID.
     * @param id the ID of the enrollment
     * @return an Optional containing the Enrollment if found, or empty if not found
     */
    Optional<Enrollment> findById(Long id); 

    /**
     * Find list of course IDs by user ID.
     * @param userId the ID of the user
     * @return a list of course IDs associated with the user
     */
    @Query("SELECT e.courseId FROM Enrollment e WHERE e.userId = :userId")
    List<Long> findCourseIdByUserId(@Param("userId") String userId);

    // get by userId and courseid
    /**
     * Find an enrollment by user ID and course ID.
     * @param userId the ID of the user
     * @param courseId the ID of the course
     * @return an Optional containing the Enrollment if found, or empty if not found
     */
    @Query("SELECT e FROM Enrollment e WHERE e.userId = :userId AND e.courseId = :courseId")
    Optional<Enrollment> findByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") Long courseId);

    /**
     * Find user IDs by course ID.
     * @param courseId the ID of the course
     * @return a list of user IDs associated with the course
     */
    @Query("SELECT e.userId FROM Enrollment e WHERE e.courseId = :courseId")
    List<String> findUserIdByCourseId(@Param("courseId") Long courseId);

    /**
     * Delete enrollments by course ID.
     * @param courseId the ID of the course
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Enrollment e WHERE e.courseId = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
