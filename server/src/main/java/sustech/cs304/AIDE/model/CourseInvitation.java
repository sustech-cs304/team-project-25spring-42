package sustech.cs304.AIDE.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_invitation")
public class CourseInvitation {
    private Long id;
    private Long courseId;
    private String userId;

    public CourseInvitation() {}

    public CourseInvitation(Long courseId, String userId) {
        this.courseId = courseId;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public Long getCourseId() { return courseId; }
    public String getUserId() { return userId; }

    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setId(Long id) { this.id = id; }
}
