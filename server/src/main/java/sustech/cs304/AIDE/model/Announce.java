package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    public Announce(String courseId, String announceName, String announceContent) {
        this.courseId = courseId;
        this.announceName = announceName;
        this.upLoadTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        this.announceContent = announceContent;
        this.visible = true;
    }

    public Long getId() { return id; }
    public String getCourseId() { return courseId; }
    public String getAnnounceName() { return announceName; }
    public LocalDateTime getUpLoadTime() { return upLoadTime; }
    public String getAnnounceContent() { return announceContent; }
    public boolean getVisible() { return visible; }

    public void setAnnounceName(String announceName) {
        this.announceName = announceName;
    }
    public void setannounceContent(String announceContent) {
        this.announceContent = announceContent;
    }
    public void closeAnnounce() {
        this.visible = false;
    }
    public void reOpenAnnounce() {
        this.visible = true;
    }
}

