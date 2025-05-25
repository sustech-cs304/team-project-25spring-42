package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;

/**
 * Represents a submission in the system.
 *
 * This class is responsible for storing the assignment ID, user ID, and address of the submission.
 */
@Entity
@Table(name = "submission")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long assignmentId;
    private String userId;
    private String address;
    public Submission() {}

    /**
     * Constructor for Submission
     *
     * @param assignmentId the ID of the assignment
     * @param userId the ID of the user
     * @param address the address of the submission
     */
    public Submission(Long assignmentId, String userId, String address) {
        this.assignmentId = assignmentId;
        this.userId = userId;
        this.address = address;
    }

    /**
     * get ID
     *
     * @return the ID
     */
    public Long getId() { return id; }

    /**
     * get ID of the assignment
     *
     * @return the ID of the assignment
     */
    public Long getAssignmentId() { return assignmentId; }

    /**
     * get ID of the user
     *
     * @return the ID of the user
     */
    public String getUserId() { return userId; }

    /**
     * get address of the submission
     *
     * @return the address of the submission
     */
    public String getAddress() { return address; }

    /**
     * set ID of the assignment
     *
     * @param assignmentId the ID of the assignment
     */
    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    /**
     * set ID of the user
     *
     * @param userId the ID of the user
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * set address of the submission
     *
     * @param address the address of the submission
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

