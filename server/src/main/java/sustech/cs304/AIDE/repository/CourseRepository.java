package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sustech.cs304.AIDE.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findById(Long id);

    @Query("SELECT c.adminId FROM Course c WHERE c.id = :id")
    String findAdminIdById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Course c WHERE c.id = :id")
    void deleteById(@Param("courseId") Long id);
}
