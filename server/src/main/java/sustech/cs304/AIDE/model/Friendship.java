package sustech.cs304.AIDE.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a friendship in the system.
 */
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

    /**
     * Constructor for Friendship
     *
     * @param userId1 the ID of the first user
     * @param userId2 the ID of the second user
     * @param status the status of the friendship
     */
    public Friendship(String userId1, String userId2, String status) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.status = status;
    }

    /**
     * get ID of the friendship
     *
     * @return the ID of the friendship
     */
    public String getUserId1() { return userId1; }

    /**
     * get ID of the second user
     *
     * @return the ID of the second user
     */
    public String getUserId2() { return userId2; }

    /**
     * get status of the friendship
     *
     * @return the status of the friendship
     */
    public String getStatus() { return status; }

    /**
     * set ID of the first user
     *
     * @param userId1 the ID of the first user
     */
    public void setUserId1(String userId1) { this.userId1 = userId1; }

    /**
     * set ID of the second user
     *
     * @param userId2 the ID of the second user
     */
    public void setUserId2(String userId2) { this.userId2 = userId2; }

    /**
     * set status of the friendship
     *
     * @param status the status of the friendship
     */
    public void setStatus(String status) { this.status = status; }
}
