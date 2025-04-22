package sustech.cs304.entity;

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
    public Long getId() {
        return id;
    }
    public String getAssignmentName() {
        return assignmentName;
    }
    public String getDeadline() {
        return deadline;
    }
    public String getCourseId() {
        return courseId;
    }
    public boolean getVisible() {
        return visible;
    }
    public boolean getWhetherSubmitted() {
        return whetherSubmitted;
    }
    public String getAddress() {
        return address;
    }
    public String getAttachmentName() {
        return attachmentName;
    }
    public String getAttachmentaddress() {
        return attachmentaddress;
    }
}
