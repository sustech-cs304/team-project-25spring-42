package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sustech.cs304.AIDE.model.Announce;
import sustech.cs304.AIDE.model.AnnounceProjection;

/**
 * Repository interface for managing Announce entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 */
public interface AnnounceRepository extends JpaRepository<Announce, Long> {

    /**
     * find project by courseId
     *
     * @param courseId the ID of the course
     * @return the list of announces
     */
    List<AnnounceProjection> findProjectByCourseId(String courseId);

    /**
     * find project by courseId and visible
     *
     * @param courseId the ID of the course
     * @param visible the visibility of the announce
     * @return the list of announces
     */
    List<AnnounceProjection> findProjectByCourseIdAndVisible(String courseId, boolean visible);

    /**
     * find the announcement by id 
     *
     * @param id the ID of the announce
     * @return the announce
     */
    @Transactional(readOnly = true)
    Optional<Announce> findById(Long id);

    /**
     * find the projects by ID
     *
     * @param id the ID of the announce
     * @return the announce
     */
    Optional<AnnounceProjection> findProjectById(Long id);

    /**
     * find the announce by courseId and visible
     *
     * @param courseId the ID of the course
     * @param visible the visibility of the announce
     * @return the list of announces
     */
    @Transactional(readOnly = true)
    List<Announce> findByCourseIdAndVisible(String courseId, boolean visible);

    /**
     * update the announce by id
     *
     * @param id the ID of the announce
     * @param visible the visibility of the announce
     * @return the status of the update
     */
    @Modifying
    @Transactional
    @Query("UPDATE Announce a SET a.visible = :visible WHERE a.id = :id")
    int updateVisibilityById(@Param("id") String id, @Param("visible") boolean visible);

    /**
     * update the announce name by id 
     *
     * @param id the ID of the announce
     * @param announceName the name of the announce
     * @return the status of the update
     */
    @Modifying
    @Transactional
    @Query("UPDATE Announce a SET a.announceName = :announceName WHERE a.id = :id")
    int updateAnnounceNameById(@Param("id") String id, @Param("announceName") String announceName);

    /**
     * update the announce content by id 
     *
     * @param id the ID of the announce
     * @param announceContent the content of the announce
     * @return the status of the update
     */
    @Modifying
    @Transactional
    @Query("UPDATE Announce a SET a.announceContent = :announceContent WHERE a.id = :id")
    int updateAnnounceContentById(@Param("id") String id, @Param("announceContent") String announceContent);

    /** 
     * delete the announce by id
     *
     * @param courseId the ID of the announce
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Announce a WHERE a.courseId = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
