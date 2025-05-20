package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sustech.cs304.AIDE.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findById(Long id); 

    @Query("SELECT e.courseId FROM Enrollment e WHERE e.userId = :userId")
    List<Long> findCourseIdByUserId(@Param("userId") String userId);

    // get by userId and courseid
    @Query("SELECT e FROM Enrollment e WHERE e.userId = :userId AND e.courseId = :courseId")
    Optional<Enrollment> findByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") Long courseId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Enrollment e WHERE e.courseId = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
