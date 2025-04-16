package sustech.cs304.entity;

import java.time.LocalDateTime;

public class Resource{
    private Long id;
    private String address;
    private String resourceName;
    private String type;
    private String uploadTime;
    private Long groupId;
    private String size;
    private boolean visible;

    public Resource(Long id, String address, String resourceName, String type, String uploadTime, Long groupId, String size, boolean visible) {
        this.id = id;
        this.address = address;
        this.resourceName = resourceName;
        this.type = type;
        this.uploadTime = uploadTime;
        this.groupId = groupId;
        this.size = size;
        this.visible = visible;
    }

    public Long getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getResourceName() {
        return resourceName;
    }
    public String getType() {
        return type;
    }
    public String getUploadTime() {
        return uploadTime;
    }
    public Long getGroupId() {
        return groupId;
    }
    public String getSize() {
        return size;
    }
    public boolean getVisible() {
        return visible;
    }
}
