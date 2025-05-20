package sustech.cs304.entity;

public class Friend {
    private String name;
    private String status;
    private String avatar;

    public Friend(String name, String status, String avatar) {
        this.name = name;
        this.status = status;
        this.avatar = avatar;
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
