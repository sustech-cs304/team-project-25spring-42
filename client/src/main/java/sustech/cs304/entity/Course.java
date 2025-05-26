package sustech.cs304.entity;

/**
 * Represents a course with its properties such as ID, name, administrator, open/close times, and status.
 */
public class Course {
    private Long id;

    private String courseName;
    private String adminId;
    private String openTime;
    private String closeTime;
    private boolean opening;

    public Course() {}

    /**
     * Constructs a Course with the specified values.
     *
     * @param id         the unique identifier for the course
     * @param courseName the name/title of the course
     * @param adminId    the administrator's user ID
     * @param openTime   the start/open time of the course
     * @param closeTime  the end/close time of the course
     * @param opening    the open status of the course
     */
    public Course(Long id, String courseName, String adminId, String openTime, String closeTime, boolean opening) {
        this.id = id;
        this.courseName = courseName;
        this.adminId = adminId;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.opening = opening;
    }

    /**
     * Returns the unique identifier of the course.
     * @return the course ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the course.
     * @param id the course ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name/title of the course.
     * @return the course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name/title of the course.
     * @param courseName the course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Returns the administrator's user ID of the course.
     * @return the admin user ID
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * Sets the administrator's user ID of the course.
     * @param adminId the admin user ID
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    /**
     * Returns the opening time of the course as a String.
     * @return the open time
     */
    public String getOpenTime() {
        return openTime;
    }

    /**
     * Sets the opening time of the course.
     * @param openTime the open time
     */
    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    /**
     * Returns the closing time of the course as a String.
     * @return the close time
     */
    public String getCloseTime() {
        return closeTime;
    }

    /**
     * Sets the closing time of the course.
     * @param closeTime the close time
     */
    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * Returns whether the course is currently open.
     * @return true if the course is open, false otherwise
     */
    public boolean isOpening() {
        return opening;
    }

    /**
     * Sets whether the course is currently open.
     * @param opening true if the course should be open, false otherwise
     */
    public void setOpening(boolean opening) {
        this.opening = opening;
    }
}
