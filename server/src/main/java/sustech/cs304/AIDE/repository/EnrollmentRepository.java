package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sustech.cs304.AIDE.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findById(Long id); 

    @Query("SELECT e.courseId FROM Enrollment e WHERE e.userId = :userId")
    List<Long> findCourseIdByUserId(@Param("userId") String userId);
}
