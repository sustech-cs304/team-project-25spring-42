package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * This class represents an announcement in a course.
 * It stores the course ID, announcement name, content, upload time, and visibility status.
 */
@Entity
@Table(name = "announce")
public class Announce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) 
    private String courseId; 
    private String announceName;
    private LocalDateTime upLoadTime;
    @Lob  
    @Column(length = 20000)
    private String announceContent;
    private boolean visible;

    public Announce() {}

    /**
     * Constructor for Announce.
     *
     * @param courseId the ID of the course
     * @param announceName the name of the announcement
     * @param announceContent the content of the announcement
     */
    public Announce(String courseId, String announceName, String announceContent) {
        this.courseId = courseId;
        this.announceName = announceName;
        this.upLoadTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        this.announceContent = announceContent;
        this.visible = true;
    }

    /**
     * get id
     * @return id
     */
    public Long getId() { return id; }

    /**
     * get courseId
     * @return courseId
     */
    public String getCourseId() { return courseId; }

    /**
     * get announceName
     * @return announceName
     */
    public String getAnnounceName() { return announceName; }

    /**
     * get upLoadTime
     * @return upLoadTime
     */
    public LocalDateTime getUpLoadTime() { return upLoadTime; }

    /**
     * get announceContent
     * @return announceContent
     */
    public String getAnnounceContent() { return announceContent; }

    /**
     * get visible
     * @return visible
     */
    public boolean getVisible() { return visible; }

    /**
     * set announceName
     * @param announceName the name of the announcement
     */
    public void setAnnounceName(String announceName) {
        this.announceName = announceName;
    }

    /**
     * set announceContent
     * @param announceContent the content of the announcement
     */
    public void setannounceContent(String announceContent) {
        this.announceContent = announceContent;
    }

    /**
     * close the announce
     */
    public void closeAnnounce() {
        this.visible = false;
    }

    /**
     * reopen the announce
     */
    public void reOpenAnnounce() {
        this.visible = true;
    }
}

