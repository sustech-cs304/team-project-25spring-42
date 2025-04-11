package sustech.cs304.entity;

public class Course {
    private Long id;

    private String courseName;
    private String adminId;
    private String openTime;
    private String closeTime;
    private boolean opening;

    public Course() {}

    public Course(Long id, String courseName, String adminId, String openTime, String closeTime, boolean opening) {
        this.id = id;
        this.courseName = courseName;
        this.adminId = adminId;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.opening = opening;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public boolean isOpening() {
        return opening;
    }

    public void setOpening(boolean opening) {
        this.opening = opening;
    }
}
