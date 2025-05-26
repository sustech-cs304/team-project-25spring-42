package sustech.cs304.entity;

/**
 * Represents an announcement associated with a course.
 * Contains information such as the announcement's ID, course ID, name, upload time,
 * content, and visibility status.
 */
public class Announce {
    private Long id;
    private String courseId;
    private String announceName;
    private String upLoadTime;
    private String announceContent;
    private boolean visible;

    /**
     * Constructs a new Announce object with the given parameters.
     *
     * @param id              the unique identifier of the announcement
     * @param courseId        the associated course ID
     * @param announceName    the title of the announcement
     * @param upLoadTime      the upload time of the announcement
     * @param announceContent the content of the announcement
     * @param visible         the visibility status of the announcement
     */
    public Announce(Long id, String courseId, String announceName, String upLoadTime, String announceContent, boolean visible) {
        this.id = id;
        this.courseId = courseId;
        this.announceName = announceName;
        this.upLoadTime = upLoadTime;
        this.announceContent = announceContent;
        this.visible = visible;
    }
    /**
     * Returns the unique identifier of the announcement.
     *
     * @return the announcement ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the course ID associated with this announcement.
     *
     * @return the course ID
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Returns the name or title of the announcement.
     *
     * @return the announcement name
     */
    public String getAnnounceName() {
        return announceName;
    }

    /**
     * Returns the upload time of the announcement.
     *
     * @return the upload time as a String
     */
    public String getUpLoadTime() {
        return upLoadTime;
    }

    /**
     * Returns the content of the announcement.
     *
     * @return the announcement content
     */
    public String getAnnounceContent() {
        return announceContent;
    }

    /**
     * Returns whether the announcement is visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean isVisible() {
        return visible;
    }
}
