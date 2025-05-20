package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assignmentName;
    private LocalDateTime deadline;
    private String courseId;
    private boolean visible;
    public Assignment() {} 
    public Assignment(String assignmentName, LocalDateTime deadline, String courseId) {
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.courseId = courseId;
        this.visible = true;
    }
    public Long getId() { return id; }
    public String getAssignmentName() { return assignmentName; }
    public LocalDateTime getDeadline() { return deadline; }
    public String getCourseId() { return courseId; }
    public boolean getVisible() { return visible; }
    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

