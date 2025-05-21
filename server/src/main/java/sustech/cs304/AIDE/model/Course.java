package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String adminId;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private boolean opening;
    
    public Course() {}

    public Course(String courseName, String adminId) {
        this.courseName = courseName;
        this.adminId = adminId;
        this.openTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        this.closeTime = null;
        this.opening = true;
    }

    public Long getId() { return id; }
    public String getCourseName() { return courseName; }
    public String getAdminId() { return adminId; }
    public LocalDateTime getOpenTime() { return openTime; }
    public LocalDateTime getCloseTime() { return closeTime; }
    public boolean getOpening() { return opening; }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public void setOpening(boolean opening) {
        this.opening = opening;
    }

    public void openCourse() {
        this.opening = true;
        this.openTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
    }

    public void reOpenCourse() {
        this.opening = true;
        this.closeTime = null;
    }

    public void closeCourse() {
        this.opening = false;
        this.closeTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
    }
}

