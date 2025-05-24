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

/**
 * Repository interface for managing Resource entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    /**
     * Find a resource by its ID.
     * @param id the ID of the resource
     * @return an Optional containing the Resource if found, or empty if not found
     */
    Optional<Resource> findById(Long id); 

    /**
     * Find a list of resource IDs by course ID.
     * @param courseId the ID of the course
     * @return a list of resource IDs associated with the course
     */
    @Query("SELECT r.id FROM Resource r WHERE r.courseId = :courseId")
    List<Long> findResourceIdByCourseId(@Param("courseId") String courseId);
    
    /**
     * Find a list of visible resource IDs by course ID.
     * @param courseId the ID of the course
     * @return a list of visible resource IDs associated with the course
     */
    @Query("SELECT r.id FROM Resource r WHERE r.courseId = :courseId AND r.visible = true")
    List<Long> findVisibleResourceIdByCourseId(@Param("courseId") String courseId);
    
    /**
     * Find a list of resource IDs by assignment ID.
     * @param assignmentId the ID of the assignment
     * @return a list of resource IDs associated with the assignment
     */
    @Query("SELECT r.id FROM Resource r WHERE r.assignmentId = :assignmentId")
    List<Long> findResourceIdByAssignmentId(@Param("assignmentId") String assignmentId);

    /**
     * Find a list of resources by course ID.
     * @param courseId the ID of the course
     * @return a list of Resource objects associated with the course
     */
    @Query("SELECT r FROM Resource r WHERE r.courseId = :courseId")
    List<Resource> findByCourseId(String courseId);

    /**
     * Find a list of visible resources by course ID.
     * @param courseId the ID of the course
     * @return a list of visible Resource objects associated with the course
     */
    @Query("SELECT r FROM Resource r WHERE r.courseId = :courseId AND r.visible = true")
    List<Resource> findVisibleByCourseId(String courseId);
    
    /**
     * Find the course ID of a resource by its ID.
     *
     * @param id the ID of the resource
     * @return the course ID of the resource
     */
    @Query("SELECT r.courseId FROM Resource r WHERE r.id = :id")
    String findCourseIdById(@Param("id") Long id);
    
    /**
     * Find the assignment ID of a resource by its ID.
     *
     * @param id the ID of the resource
     * @return the assignment ID of the resource
     */
    @Query("SELECT r.address FROM Resource r WHERE r.id = :id")
    String findAddressById(@Param("id") Long id); 
    
    /**
     * Find the resource name of a resource by its ID.
     *
     * @param id the ID of the resource
     * @return the resource name of the resource
     */
    @Query("SELECT r.resourceName FROM Resource r WHERE r.id = :id")
    String findResourceNameById(@Param("id") Long id);
    
    /**
     * Find the type of a resource by its ID.
     *
     * @param id the ID of the resource
     * @return the type of the resource
     */
    @Query("SELECT r.type FROM Resource r WHERE r.id = :id")
    String findResourceTypeById(@Param("id") Long id);
    
    /**
     * Find the upload time of a resource by its ID.
     *
     * @param id the ID of the resource
     * @return the upload time of the resource
     */
    @Query("SELECT r.uploadTime FROM Resource r WHERE r.id = :id")
    LocalDateTime findUploadTimeById(@Param("id") Long id);
    
    /**
     * Find the group ID of a resource by its ID.
     *
     * @param id the ID of the resource
     * @return the group ID of the resource
     */
    @Query("SELECT r.groupId FROM Resource r WHERE r.id = :id")
    Long findGroupIdById(@Param("id") Long id);
    
    /**
     * Find the size of a resource by its ID.
     *
     * @param id the ID of the resource
     * @return the size of the resource
     */
    @Query("SELECT r.size FROM Resource r WHERE r.id = :id")
    String findSizeById(@Param("id") Long id);
    
    /**
     * Find a list of resource IDs by assignment ID.
     *
     * @param assignmentId the ID of the assignment
     * @return a list of resource IDs associated with the assignment
     */
    @Query("SELECT r.id FROM Resource r WHERE r.assignmentId = :assignmentId")
    List<Long> findIdByAssignmentId(@Param("assignmentId") String assignmentId);

    /**
     * Find the visibility status of a resource by its ID.
     *
     * @param id the ID of the resource
     * @return the visibility status of the resource
     */
    @Query("SELECT r.visible FROM Resource r WHERE r.id = :id")
    boolean findVisibleById(@Param("id") Long id);

    /**
     * Update the visibility status of a resource by its ID.
     *
     * @param id the ID of the resource
     * @param visible the new visibility status
     * @return the number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.visible = :visible WHERE r.id = :id")
    int updateVisibilityById(@Param("id") Long id, @Param("visible") boolean visible);

    /**
     * Update the resource name by its ID.
     *
     * @param id the ID of the resource
     * @param resourceName the new resource name
     * @return the number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.resourceName = :resourceName WHERE r.id = :id")
    int updateResourceNameById(@Param("id") Long id, @Param("resourceName") String resourceName);

    /**
     * Update the type of a resource by its ID.
     *
     * @param id the ID of the resource
     * @param type the new type of the resource
     * @return the number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.type = :type WHERE r.id = :id")
    int updateTypeById(@Param("id") Long id, @Param("type") String type);

    /**
     * Update the upload time of a resource by its ID.
     *
     * @param id the ID of the resource
     * @param uploadTime the new upload time
     * @return the number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.uploadTime = :uploadTime WHERE r.id = :id")
    int updateUploadTimeById(@Param("id") Long id, @Param("uploadTime") String uploadTime);

    /**
     * Update the group ID of a resource by its ID.
     *
     * @param id the ID of the resource
     * @param groupId the new group ID
     * @return the number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.groupId = :groupId WHERE r.id = :id")
    int updateGroupIdById(@Param("id") Long id, @Param("groupId") Long groupId);

    /**
     * Update the size of a resource by its ID.
     *
     * @param id the ID of the resource
     * @param size the new size of the resource
     * @return the number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.size = :size WHERE r.id = :id")
    int updateSizeById(@Param("id") Long id, @Param("size") String size);

    /**
     * Update the address of a resource by its ID.
     *
     * @param id the ID of the resource
     * @param address the new address of the resource
     * @return the number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE Resource r SET r.address = :address WHERE r.id = :id")
    int updateAddressById(@Param("id") Long id, @Param("address") String address);

    /**
     * Delete resources by course ID.
     *
     * @param courseId the ID of the course
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Resource r WHERE r.courseId = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
