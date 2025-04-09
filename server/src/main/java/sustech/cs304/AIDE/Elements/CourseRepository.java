package sustech.cs304.AIDE.Elements;

import sustech.cs304.AIDE.Elements.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long id);
}
