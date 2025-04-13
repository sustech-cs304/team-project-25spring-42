package sustech.cs304.AIDE.Elements;

import sustech.cs304.AIDE.Elements.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long id);
    @Query("SELECT c.adminId FROM Course c WHERE c.id = :id")
    String findAdminIdById(@Param("id") Long id);
}
