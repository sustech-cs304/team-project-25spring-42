package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;
    private String userId;
    
    Enrollment() {}
    public Enrollment(Long courseId, String userId) {
        this.courseId = courseId;
        this.userId = userId;
    }
    public Long getId() { return id; }
    public Long getCourseId() { return courseId; }
}

