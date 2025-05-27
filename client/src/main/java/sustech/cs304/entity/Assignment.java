package sustech.cs304.entity;

/**
 * Represents an assignment associated with a course.
 * Contains information such as assignment ID, name, deadline, course ID, visibility,
 * submission status, resource address, attachment name, and attachment address.
 */
public class Assignment {
    private Long id;
    private String assignmentName;
    private String deadline;
    private String courseId;
    private boolean visible;
    private boolean whetherSubmitted;
    private String address;
    private String attachmentaddress;
    private String attachmentName;

    /**
     * Constructs a new Assignment object with the specified parameters.
     *
     * @param id                the unique identifier of the assignment
     * @param assignmentName    the name or title of the assignment
     * @param deadline          the deadline for the assignment
     * @param courseId          the associated course ID
     * @param visible           the visibility status of the assignment
     * @param address           the address or URL of the submission/resource
     * @param attachmentName    the name of the attachment (if any)
     * @param attachmentaddress the address or URL of the attachment (if any)
     */
    public Assignment(Long id, String assignmentName, String deadline, String courseId, boolean visible, String address, String attachmentName, String attachmentaddress) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.courseId = courseId;
        this.visible = visible;
        if (address == null) {
            this.whetherSubmitted = false;
        } else {
            this.whetherSubmitted = true;
        }
        this.address = address;
        this.attachmentName = attachmentName;
        this.attachmentaddress = attachmentaddress;
    }

    /**
     * Returns the unique identifier of the assignment.
     *
     * @return the assignment ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the name or title of the assignment.
     *
     * @return the assignment name
     */
    public String getAssignmentName() {
        return assignmentName;
    }

    /**
     * Returns the deadline for the assignment.
     *
     * @return the deadline as a String
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Returns the course ID associated with this assignment.
     *
     * @return the course ID
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Returns whether the assignment is visible to users.
     *
     * @return true if visible, false otherwise
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Returns whether the assignment has been submitted (address is not null).
     *
     * @return true if submitted, false otherwise
     */
    public boolean getWhetherSubmitted() {
        return whetherSubmitted;
    }

    /**
     * Returns the address or URL of the submission or resource.
     *
     * @return the address as a String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the name of the assignment's attachment.
     *
     * @return the attachment name
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * Returns the address or URL of the assignment's attachment.
     *
     * @return the attachment address as a String
     */
    public String getAttachmentaddress() {
        return attachmentaddress;
    }
}
