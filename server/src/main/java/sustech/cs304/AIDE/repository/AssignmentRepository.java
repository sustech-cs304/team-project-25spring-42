package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sustech.cs304.AIDE.model.Assignment;

import java.time.LocalDateTime;

/**
 * Repository interface for managing Assignment entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    /**
     * find assignment by ID
     *
     * @param id the ID of the assignment
     * @return the assignment
     */
    Optional<Assignment> findById(Long id); 
    
    /**
     * find the assignmentID by assignment name and the deadline
     *
     * @param assignmentName the name of the assignment
     * @param deadline the deadline of the assignment
     * @return the ID of the assignment
     */
    @Query("SELECT a.id FROM Assignment a WHERE a.deadline = :deadline AND a.assignmentName = :assignmentName")
    Long findAssignmentIdByAssignmentNameAndDeadline(String assignmentName, LocalDateTime deadline);

    /**
     * find the assignmentID by courseId
     *
     * @param courseId the ID of the course
     * @return the list of assignment IDs
     */
    @Query("SELECT a.id FROM Assignment a WHERE a.courseId = :courseId")
    List<Long> findAssignmentIdByCourseId(@Param("courseId") String courseId);

    /**
     * find the assignmentID by courseId and visible
     *
     * @param courseId the ID of the course
     * @return the list of assignment IDs
     */
    @Query("SELECT a.id FROM Assignment a WHERE a.courseId = :courseId AND a.visible = true")
    List<Long> findVisibleAssignmentIdByCourseId(@Param("courseId") String courseId);

    /** 
     * find the assignment name by ID
     *
     * @param id the ID of the assignment
     * @return the name of the assignment
     */
    @Query("SELECT a.assignmentName FROM Assignment a WHERE a.id = :id")
    String findAssignmentNameById(@Param("id") Long id);

    /**
     * find the deadline by ID
     *
     * @param id the ID of the assignment
     * @return the deadline of the assignment
     */
    @Query("SELECT a.deadline FROM Assignment a WHERE a.id = :id")
    LocalDateTime findDeadlineById(@Param("id") Long id);

    /**
     * find the courseId by ID
     *
     * @param id the ID of the assignment
     * @return the courseId of the assignment
     */
    @Query("SELECT a.courseId FROM Assignment a WHERE a.id = :id")
    String findCourseIdById(@Param("id") Long id);

    /**
     * delete the assignment by courseId
     *
     * @param courseId the ID of the assignment
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Assignment a WHERE a.courseId = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
