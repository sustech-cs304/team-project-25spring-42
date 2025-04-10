package sustech.cs304.entity;

public class Announce {
    private Long id;
    private String courseId;
    private String announceName;
    private String upLoadTime;
    private String announceContent;
    private boolean visible;

    public Announce(Long id, String courseId, String announceName, String upLoadTime, String announceContent, boolean visible) {
        this.id = id;
        this.courseId = courseId;
        this.announceName = announceName;
        this.upLoadTime = upLoadTime;
        this.announceContent = announceContent;
        this.visible = visible;
    }
    public Long getId() {
        return id;
    }
    public String getCourseId() {
        return courseId;
    }
    public String getAnnounceName() {
        return announceName;
    }
    public String getUpLoadTime() {
        return upLoadTime;
    }
    public String getAnnounceContent() {
        return announceContent;
    }
    public boolean isVisible() {
        return visible;
    }
}
