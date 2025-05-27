package sustech.cs304.AIDE.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a course invitation in the system.
 *
 * This class is responsible for storing the course ID and user ID of the invitation.
 */
@Entity
@Table(name = "course_invitation")
public class CourseInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long courseId;
    private String userId;

    public CourseInvitation() {}

    /**
     * Constructor for CourseInvitation
     *
     * @param courseId the ID of the course
     * @param userId the ID of the user
     */
    public CourseInvitation(Long courseId, String userId) {
        this.courseId = courseId;
        this.userId = userId;
    }

    /**
     * get ID of the course invitation
     *
     * @return the ID of the course invitation
     */
    public Long getId() { return id; }

    /**
     * get ID of the course
     *
     * @return the ID of the course
     */
    public Long getCourseId() { return courseId; }

    /**
     * get ID of the user
     *
     * @return the ID of the user
     */
    public String getUserId() { return userId; }

    /**
     * set ID of the course invitation
     *
     * @param courseId the ID of the course invitation
     */
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    /**
     * set ID of the user
     *
     * @param userId the ID of the user
     */
    public void setUserId(String userId) { this.userId = userId; }

    /**
     * set ID of the course
     *
     * @param id the ID of the course
     */
    public void setId(Long id) { this.id = id; }
}
