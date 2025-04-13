package sustech.cs304.AIDE.Elements; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

