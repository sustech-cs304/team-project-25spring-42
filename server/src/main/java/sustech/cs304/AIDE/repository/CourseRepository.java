package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sustech.cs304.AIDE.model.Course;

/**
 * Repository interface for managing Course entities.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Find a course by its ID.
     * @param id the ID of the course
     * @return an Optional containing the Course if found, or empty if not found
     */
    Optional<Course> findById(Long id);

    /**
     * Find the admin ID of a course by its ID.
     * @param id the ID of the course
     * @return the admin ID of the course
     */
    @Query("SELECT c.adminId FROM Course c WHERE c.id = :id")
    String findAdminIdById(@Param("id") Long id);

    /**
     * Delete a course by its ID.
     * @param id the ID of the course
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Course c WHERE c.id = :id")
    void deleteById(@Param("courseId") Long id);
}
