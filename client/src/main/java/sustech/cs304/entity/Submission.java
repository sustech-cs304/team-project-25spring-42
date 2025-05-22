package sustech.cs304.entity;

public class Submission {

    private Long id;
    private Long assignmentId;
    private String userId;
    private String address;
    public Submission() {}
    public Submission(Long assignmentId, String userId, String address) {
        this.assignmentId = assignmentId;
        this.userId = userId;
        this.address = address;
    }
    public Long getId() { return id; }
    public Long getAssignmentId() { return assignmentId; }
    public String getUserId() { return userId; }
    public String getAddress() { return address; }
    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}

