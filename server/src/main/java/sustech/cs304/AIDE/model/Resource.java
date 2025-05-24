package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Represents a resource in the system.
 *
 * This class is responsible for storing the course ID, resource name, type, address, upload time,
 * group ID, size, visibility, and assignment ID of the resource.
 */
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

    /**
     * Constructor for Resource
     *
     * @param address the address of the resource
     * @param courseId the ID of the course
     * @param resourceName the name of the resource
     * @param type the type of the resource
     * @param groupId the ID of the group
     * @param size the size of the resource
     */
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

    /**
     * Constructor for Resource
     *
     * @param address the address of the resource
     * @param resourceName the name of the resource
     * @param assignmentId the ID of the assignment
     */
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

    /**
     * get the address of the resource
     *
     * @return the address of the resource
     */
    public String getAddress() { return address; }

    /**
     * get the ID of the course
     *
     * @return the ID of the course
     */
    public String getCourseId() { return courseId; }

    /**
     * get the ID of the resource
     *
     * @return the ID of the resource
     */
    public Long getId() { return id; }

    /**
     * get the name of the resource
     *
     * @return the name of the resource
     */
    public String getResourceName() { return resourceName; }

    /**
     * get the type of the resource
     *
     * @return the type of the resource
     */
    public String getType() { return type; }

    /**
     * get the upload time of the resource
     *
     * @return the upload time of the resource
     */
    public LocalDateTime getUpLoadTime() { return uploadTime; }


    /**
     * get the ID of the group
     *
     * @return the ID of the group
     */
    public Long getGroupId() { return groupId; }

    /**
     * get the size of the resource
     *
     * @return the size of the resource
     */
    public String getSize() { return size; }

    /**
     * get the visibility of the resource
     *
     * @return the visibility of the resource
     */
    public boolean getVisible() { return visible; }

    /**
     * get the ID of the assignment
     *
     * @return the ID of the assignment
     */
    public String getAssignmentId() { return assignmentId; }

    /**
     * get the upload time of the resource
     *
     * @return the upload time of the resource
     */
    public LocalDateTime getUploadTime() {        
        return uploadTime;
    }

    /**
     * set the address of the resource
     *
     * @param address the address of the resource
     */
    public void setAddress(String address) { this.address = address; }

    /**
     * set the ID of the course
     *
     * @param courseId the ID of the course
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * set the resource name 
     *
     * @param resourceName the name of the resource
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * set the type of the resource
     *
     * @param type the type of the resource
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * set the upload time of the resource
     *
     * @param uploadTime the upload time of the resource
     */
    public void setUpLoadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * set the ID of the group
     *
     * @param groupId the ID of the group
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * set the size of the resource
     *
     * @param size the size of the resource
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * set the visibility of the resource
     *
     * @param visible the visibility of the resource
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * set the ID of the assignment
     *
     * @param assignmentId the ID of the assignment
     */
    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }
}

