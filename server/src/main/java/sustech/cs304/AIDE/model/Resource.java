package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseId;
    private String resourceName;
    private String type;
    private String address;
    private LocalDateTime uploadTime;
    private Long groupId;
    private String size;
    private boolean visible;
    private String assignmentId;
    
    public Resource() {}
    public Resource(String address, String courseId, String resourceName, String type, Long groupId, String size) {
        this.courseId = courseId;
        this.resourceName = resourceName;
        this.type = type;
        this.groupId = groupId;
        this.size = size;
        this.uploadTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        this.visible = true;
        this.assignmentId = null;
        this.address = address;
    }
    public Resource(String address, String resourceName, String assignmentId) {
        this.courseId = null;
        this.resourceName = resourceName;
        this.type = null;
        this.groupId = null;
        this.size = null;
        this.uploadTime = null;
        this.visible = false;
        this.assignmentId = assignmentId;
        this.address = address;
    }
    public String getAddress() { return address; }
    public String getCourseId() { return courseId; }
    public Long getId() { return id; }
    public String getResourceName() { return resourceName; }
    public String getType() { return type; }
    public LocalDateTime getUpLoadTime() { return uploadTime; }
    public Long getGroupId() { return groupId; }
    public String getSize() { return size; }
    public boolean getVisible() { return visible; }
    public String getAssignmentId() { return assignmentId; }
    public LocalDateTime getUploadTime() {        
        return uploadTime;
    }
    public void setAddress(String address) { this.address = address; }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setUpLoadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }
}

