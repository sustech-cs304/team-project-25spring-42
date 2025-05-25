package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Represents an enrollment in the system.
 *
 * This class is responsible for storing the course ID and user ID of the enrollment.
 */
@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;
    private String userId;
    
    Enrollment() {}

    /**
     * Constructor for Enrollment
     *
     * @param courseId the ID of the course
     * @param userId the ID of the user
     */
    public Enrollment(Long courseId, String userId) {
        this.courseId = courseId;
        this.userId = userId;
    }

    /**
     * get the ID of the enrollment
     *
     * @return the ID of the enrollment
     */
    public Long getId() { return id; }

    /**
     * get the ID of the user
     *
     * @return the ID of the user
     */
    public Long getCourseId() { return courseId; }
}

