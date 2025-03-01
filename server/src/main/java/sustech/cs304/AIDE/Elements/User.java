package sustech.cs304.AIDE.Elements; 

import jakarta.persistence.*;

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

    public User() {}

    public User(String platformId, String username, String avatarUrl) {
        this.platformId = platformId;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() { return id; }
    public String getPlatformId() { return platformId; }
    public String getUsername() { return username; }
    public String getAvatarUrl() { return avatarUrl; }
}

