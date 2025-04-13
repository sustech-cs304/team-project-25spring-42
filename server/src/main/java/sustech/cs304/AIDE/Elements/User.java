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

    @Lob  
    @Column(length = 20000)
    private String bio;

    public User() {}

    public User(String platformId, String username, String avatarUrl) {
        this.platformId = platformId;
        this.username = username;
        this.avatarUrl = avatarUrl;   
        this.registerTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        this.lastLoginTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        this.phoneNumber = null;
        this.email = null;
        this.bio = "Empty Bio";
    }

    public Long getId() { return id; }
    public String getPlatformId() { return platformId; }
    public String getUsername() { return username; }
    public String getAvatarUrl() { return avatarUrl; }
    public LocalDateTime getRegisterTime() { return registerTime; }
    public LocalDateTime getLastLoginTime() { return lastLoginTime; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }

    public void updateLastLoginTime() {
        this.lastLoginTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
}

