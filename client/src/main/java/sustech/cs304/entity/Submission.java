package sustech.cs304.entity;

/**
 * Represents a submission for an assignment by a user.
 * Contains information such as submission ID, assignment ID, user ID, and the submission address.
 */
public class Submission {

    private Long id;
    private Long assignmentId;
    private String userId;
    private String address;
    public Submission() {}

    /**
     * Constructs a new Submission with the specified assignment ID, user ID, and address.
     *
     * @param assignmentId the ID of the assignment
     * @param userId       the ID of the user submitting the assignment
     * @param address      the address or URL of the submission
     */
    public Submission(Long assignmentId, String userId, String address) {
        this.assignmentId = assignmentId;
        this.userId = userId;
        this.address = address;
    }
    /**
     * Returns the unique identifier of the submission.
     *
     * @return the submission ID
     */
    public Long getId() { return id; }

    /**
     * Returns the ID of the assignment associated with this submission.
     *
     * @return the assignment ID
     */
    public Long getAssignmentId() { return assignmentId; }

    /**
     * Returns the ID of the user who made the submission.
     *
     * @return the user ID
     */
    public String getUserId() { return userId; }

    /**
     * Returns the address or URL of the submission.
     *
     * @return the submission address
     */
    public String getAddress() { return address; }

    /**
     * Sets the ID of the assignment associated with this submission.
     *
     * @param assignmentId the assignment ID
     */
    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    /**
     * Sets the ID of the user who made the submission.
     *
     * @param userId the user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Sets the address or URL of the submission.
     *
     * @param address the submission address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

