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

public interface AnnounceRepository extends JpaRepository<Announce, Long> {

    List<AnnounceProjection> findProjectByCourseId(String courseId);

    List<AnnounceProjection> findProjectByCourseIdAndVisible(String courseId, boolean visible);

    @Transactional(readOnly = true)
    Optional<Announce> findById(Long id);

    Optional<AnnounceProjection> findProjectById(Long id);

    @Transactional(readOnly = true)
    List<Announce> findByCourseIdAndVisible(String courseId, boolean visible);

    @Modifying
    @Transactional
    @Query("UPDATE Announce a SET a.visible = :visible WHERE a.id = :id")
    int updateVisibilityById(@Param("id") String id, @Param("visible") boolean visible);

    @Modifying
    @Transactional
    @Query("UPDATE Announce a SET a.announceName = :announceName WHERE a.id = :id")
    int updateAnnounceNameById(@Param("id") String id, @Param("announceName") String announceName);

    @Modifying
    @Transactional
    @Query("UPDATE Announce a SET a.announceContent = :announceContent WHERE a.id = :id")
    int updateAnnounceContentById(@Param("id") String id, @Param("announceContent") String announceContent);
}
