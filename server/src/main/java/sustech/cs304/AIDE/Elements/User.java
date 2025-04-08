package sustech.cs304.AIDE.Elements; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.validation.constraints.Email;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String platformId;
   
    private String username;
    private String avatarUrl;
    private LocalDateTime registerTime;
    private LocalDateTime lastLoginTime;
    private String phoneNumber;
    
    @Email(message = "Invalid email format")
    private String email;

    public User() {}

    public User(String platformId, String username, String avatarUrl) {
        this.platformId = platformId;
        this.username = username;
        this.avatarUrl = avatarUrl;   
        this.registerTime = LocalDateTime.now();
        this.lastLoginTime = LocalDateTime.now();
        this.phoneNumber = null;
        this.email = null;
    }

    public Long getId() { return id; }
    public String getPlatformId() { return platformId; }
    public String getUsername() { return username; }
    public String getAvatarUrl() { return avatarUrl; }
    public LocalDateTime getRegisterTime() { return registerTime; }
    public LocalDateTime getLastLoginTime() { return lastLoginTime; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }

    public void updateLastLoginTime() {
        this.lastLoginTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
    }
    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void updateEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

