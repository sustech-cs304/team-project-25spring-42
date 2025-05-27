package sustech.cs304.entity;

import java.time.LocalDateTime;

/**
 * Represents a resource entity, such as a file or document, with associated metadata.
 * Includes information like resource ID, address, name, type, upload time, group ID, size, and visibility.
 */
public class Resource{
    private Long id;
    private String address;
    private String resourceName;
    private String type;
    private String uploadTime;
    private Long groupId;
    private String size;
    private boolean visible;

    /**
     * Constructs a new Resource with the specified parameters.
     *
     * @param id           the unique identifier of the resource
     * @param address      the address or URL of the resource
     * @param resourceName the name of the resource
     * @param type         the type/category of the resource
     * @param uploadTime   the upload time of the resource
     * @param groupId      the group ID to which the resource belongs
     * @param size         the size of the resource
     * @param visible      the visibility status of the resource
     */
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

    /**
     * Returns the unique identifier of the resource.
     *
     * @return the resource ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the address or URL of the resource.
     *
     * @return the resource address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the name of the resource.
     *
     * @return the resource name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Returns the type or category of the resource.
     *
     * @return the resource type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the upload time of the resource.
     *
     * @return the upload time as a String
     */
    public String getUploadTime() {
        return uploadTime;
    }

    /**
     * Returns the group ID to which this resource belongs.
     *
     * @return the group ID
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * Returns the size of the resource.
     *
     * @return the resource size
     */
    public String getSize() {
        return size;
    }

    /**
     * Returns whether the resource is visible to users.
     *
     * @return true if visible, false otherwise
     */
    public boolean getVisible() {
        return visible;
    }
}
