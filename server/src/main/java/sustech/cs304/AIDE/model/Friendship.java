package sustech.cs304.AIDE.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "friendships")
public class Friendship {

    @Id
    @GeneratedValue
    private Long id;
    
    private String userId1;
    private String userId2;
    private String status; // "pending", "accepted", "blocked"

    public Friendship() {}

    public Friendship(String userId1, String userId2, String status) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.status = status;
    }

    public String getUserId1() { return userId1; }
    public String getUserId2() { return userId2; }
    public String getStatus() { return status; }

    public void setUserId1(String userId1) { this.userId1 = userId1; }
    public void setUserId2(String userId2) { this.userId2 = userId2; }
    public void setStatus(String status) { this.status = status; }
}
