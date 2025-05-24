package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * This class represents an assignment in a course.
 * It stores the assignment name, deadline, course ID, and visibility status.
 */
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

    /**
     * Constructor for Assignment.
     *
     * @param assignmentName the name of the assignment
     * @param deadline the deadline for the assignment
     * @param courseId the ID of the course
     */
    public Assignment(String assignmentName, LocalDateTime deadline, String courseId) {
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.courseId = courseId;
        this.visible = true;
    }

    /**
     * get Id 
     * @return id
     */
    public Long getId() { return id; }

    /**
     * get assignmentName
     * @return assignmentName
     */
    public String getAssignmentName() { return assignmentName; }

    /**
     * get deadline
     * @return deadline
     */
    public LocalDateTime getDeadline() { return deadline; }

    /**
     * get courseId
     * @return courseId
     */
    public String getCourseId() { return courseId; }

    /**
     * get visible
     * @return visible
     */
    public boolean getVisible() { return visible; }

    /**
     * Set the name of the assignment.
     *
     * @param assignmentName the name of the assignment
     */
    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    /**
     * Set the deadline for the assignment.
     *
     * @param deadline the deadline for the assignment
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * Set the course ID for the assignment.
     *
     * @param courseId the ID of the course
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Set the visibility of the assignment.
     *
     * @param visible the visibility of the assignment
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

