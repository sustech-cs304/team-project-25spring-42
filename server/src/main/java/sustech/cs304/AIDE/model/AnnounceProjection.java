package sustech.cs304.AIDE.model;

import java.time.LocalDateTime;

/**
 * This interface represents a projection for announcements in a course.
 * It provides methods to access the announcement's ID, course ID, name, upload time, and visibility status.
 */
public interface AnnounceProjection {

    /**
     * get id
     * @return id
     */
    Long getId();

    /**
     * get courseId
     * @return courseId
     */
    String getCourseId();

    /**
     * get announceName
     * @return announceName
     */
    String getAnnounceName();

    /**
     * get uploadTime
     * @return uploadTime
     */
    LocalDateTime getUpLoadTime();

    /**
     * get announceContent
     * @return announceContent
     */
    boolean isVisible(); 
}
