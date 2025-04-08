package sustech.cs304.userhome; 
import java.time.LocalDateTime;
public class UserServerSide {
    private String platformId;
    private String username;
    private String avatarUrl;
    private String registerTime;
    private String lastLoginTime;
    private String phoneNumber;
    private String email;

    public UserServerSide(String platformId, String username, String avatarUrl, String registerTime, String lastLoginTime, String phoneNumber, String email) {
        this.platformId = platformId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public String getPlatformId() {
        return platformId;
    }
    public String getUsername() {
        return username;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public String getRegisterTime() {
        return registerTime;
    }
    public String getLastLoginTime() {
        return lastLoginTime;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
}
