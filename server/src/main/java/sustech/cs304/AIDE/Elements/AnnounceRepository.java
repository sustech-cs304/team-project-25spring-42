package sustech.cs304.AIDE.Elements;

import sustech.cs304.AIDE.Elements.Announce;
import sustech.cs304.AIDE.Elements.AnnounceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AnnounceRepository extends JpaRepository<Announce, Long> {
    List<AnnounceProjection> findProjectByCourseId(String courseId);
    List<AnnounceProjection> findProjectByCourseIdAndVisible(String courseId, boolean visible);
    Optional<Announce> findById(Long id);
    Optional<AnnounceProjection> findProjectById(Long id);
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
    int updateAnnounceContentById(@Param("courseId") String id, @Param("announceContent") String announceContent);
}

