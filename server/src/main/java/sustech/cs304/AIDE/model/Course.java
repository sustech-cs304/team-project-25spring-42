package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Represents a course in the system.
 */
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

    /**
     * Constructor for Course
     *
     * @param courseName the name of the course
     * @param adminId    ID of the admin
     */
    public Course(String courseName, String adminId) {
        this.courseName = courseName;
        this.adminId = adminId;
        this.openTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        this.closeTime = null;
        this.opening = true;
    }

    /**
     * get Id
     * @return id
     */
    public Long getId() { return id; }

    /**
     * get courseName
     * @return courseName
     */
    public String getCourseName() { return courseName; }

    /**
     * get adminId
     * @return adminId
     */
    public String getAdminId() { return adminId; }

    /**
     * get openTime
     * @return openTime
     */
    public LocalDateTime getOpenTime() { return openTime; }

    /**
     * get closeTime
     * @return closeTime
     */
    public LocalDateTime getCloseTime() { return closeTime; }

    /**
     * get opening
     * @return opening
     */
    public boolean getOpening() { return opening; }

    /**
     * set courseName
     * @param courseName the name of the course
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * set adminId
     * @param adminId the ID of the admin
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    /**
     * set openTime
     * @param openTime the time the course was opened
     */
    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    /**
     * set closeTime
     * @param closeTime the time the course was closed
     */
    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * set opening
     * @param opening the status of the course
     */
    public void setOpening(boolean opening) {
        this.opening = opening;
    }

    /**
     * open the course
     */
    public void openCourse() {
        this.opening = true;
        this.openTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
    }

    /**
     * close the course
     */
    public void reOpenCourse() {
        this.opening = true;
        this.closeTime = null;
    }

    /**
     * close the course
     */
    public void closeCourse() {
        this.opening = false;
        this.closeTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
    }
}

