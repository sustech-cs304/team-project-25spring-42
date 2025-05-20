package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sustech.cs304.AIDE.model.Resource;

import java.time.LocalDateTime;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findById(Long id); 

    @Query("SELECT r.id FROM Resource r WHERE r.courseId = :courseId")
    List<Long> findResourceIdByCourseId(@Param("courseId") String courseId);
    
    @Query("SELECT r.id FROM Resource r WHERE r.courseId = :courseId AND r.visible = true")
    List<Long> findVisibleResourceIdByCourseId(@Param("courseId") String courseId);
    
    @Query("SELECT r.id FROM Resource r WHERE r.assignmentId = :assignmentId")
    List<Long> findResourceIdByAssignmentId(@Param("assignmentId") String assignmentId);

    @Query("SELECT r FROM Resource r WHERE r.courseId = :courseId")
    List<Resource> findByCourseId(String courseId);

    @Query("SELECT r FROM Resource r WHERE r.courseId = :courseId AND r.visible = true")
    List<Resource> findVisibleByCourseId(String courseId);
    
    @Query("SELECT r.courseId FROM Resource r WHERE r.id = :id")
    String findCourseIdById(@Param("id") Long id);
    
    @Query("SELECT r.address FROM Resource r WHERE r.id = :id")
    String findAddressById(@Param("id") Long id); 
    
    @Query("SELECT r.resourceName FROM Resource r WHERE r.id = :id")
    String findResourceNameById(@Param("id") Long id);
    
    @Query("SELECT r.type FROM Resource r WHERE r.id = :id")
    String findResourceTypeById(@Param("id") Long id);
    
    @Query("SELECT r.uploadTime FROM Resource r WHERE r.id = :id")
    LocalDateTime findUploadTimeById(@Param("id") Long id);
    
    @Query("SELECT r.groupId FROM Resource r WHERE r.id = :id")
    Long findGroupIdById(@Param("id") Long id);
    
    @Query("SELECT r.size FROM Resource r WHERE r.id = :id")
    String findSizeById(@Param("id") Long id);
    
    @Query("SELECT r.visible FROM Resource r WHERE r.id = :id")
    boolean findVisibleById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.visible = :visible WHERE r.id = :id")
    int updateVisibilityById(@Param("id") Long id, @Param("visible") boolean visible);

    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.resourceName = :resourceName WHERE r.id = :id")
    int updateResourceNameById(@Param("id") Long id, @Param("resourceName") String resourceName);

    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.type = :type WHERE r.id = :id")
    int updateTypeById(@Param("id") Long id, @Param("type") String type);

    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.uploadTime = :uploadTime WHERE r.id = :id")
    int updateUploadTimeById(@Param("id") Long id, @Param("uploadTime") String uploadTime);

    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.groupId = :groupId WHERE r.id = :id")
    int updateGroupIdById(@Param("id") Long id, @Param("groupId") Long groupId);

    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.size = :size WHERE r.id = :id")
    int updateSizeById(@Param("id") Long id, @Param("size") String size);

    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.address = :address WHERE r.id = :id")
    int updateAddressById(@Param("id") Long id, @Param("address") String address);
}
