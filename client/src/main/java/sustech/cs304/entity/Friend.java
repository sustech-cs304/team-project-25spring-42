package sustech.cs304.entity;

/**
 * Represents a friend entity with ID, name, status, and avatar.
 * Used to store information about a user's friend in the application.
 */
public class Friend {
    private String id;
    private String name;
    private String status;
    private String avatar;

    public Friend(String id, String name, String status, String avatar) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.avatar = avatar;
    }

    /**
     * Returns the unique identifier of the friend.
     *
     * @return the friend ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the friend.
     *
     * @param id the friend ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the name of the friend.
     *
     * @return the friend's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the friend.
     *
     * @param name the friend's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the status of the friend.
     *
     * @return the friend's status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the friend.
     *
     * @param status the friend's status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the avatar image URL or path of the friend.
     *
     * @return the friend's avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Sets the avatar image URL or path of the friend.
     *
     * @param avatar the friend's avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
