package sustech.cs304.AIDE.model; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.validation.constraints.Email;

/**
 * Represents a user in the system.
 *
 * This class is responsible for storing the platform ID, username, avatar URL, registration time,
 * last login time, phone number, email, and bio of the user.
 */
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

    /**
     * Constructor for User
     *
     * @param platformId the ID of the user on the platform
     * @param username the username of the user
     * @param avatarUrl the URL of the user's avatar
     */
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

    /**
     * get the ID of the user
     *
     * @return the ID of the user
     */
    public Long getId() { return id; }

    /**
     * get the ID of the user on the platform
     *
     * @return the ID of the user on the platform
     */
    public String getPlatformId() { return platformId; }

    /**
     * get the username of the user
     *
     * @return the username of the user
     */
    public String getUsername() { return username; }

    /**
     * get the URL of the user's avatar
     *
     * @return the URL of the user's avatar
     */
    public String getAvatarUrl() { return avatarUrl; }

    /**
     * get the registration time of the user
     *
     * @return the registration time of the user
     */
    public LocalDateTime getRegisterTime() { return registerTime; }

    /**
     * get the last login time of the user
     *
     * @return the last login time of the user
     */
    public LocalDateTime getLastLoginTime() { return lastLoginTime; }

    /**
     * get the phone number of the user
     *
     * @return the phone number of the user
     */
    public String getPhoneNumber() { return phoneNumber; }

    /**
     * get the email of the user
     *
     * @return the email of the user
     */
    public String getEmail() { return email; }

    /**
     * get the bio of the user
     *
     * @return the bio of the user
     */
    public String getBio() { return bio; }

    /**
     * update the last login time of the user
     *
     * @return the last login time of the user
     */
    public void updateLastLoginTime() {
        this.lastLoginTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
    }

    /**
     * set phone number of the user
     *
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * set email of the user
     *
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * set username of the user
     *
     * @param username the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * set avatar URL of the user
     *
     * @param avatarUrl the URL of the user's avatar
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * set the bio of the user
     *
     * @param bio the bio of the user
     */
    public void setBio(String bio) {
        this.bio = bio;
    }
}

