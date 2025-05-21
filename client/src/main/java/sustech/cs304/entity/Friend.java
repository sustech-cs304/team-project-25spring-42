package sustech.cs304.entity;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
