package sustech.cs304.entity;

/**
 * Represents a friendship relationship between two users.
 * Contains the IDs of both users and a status indicating whether the friendship is active.
 */
public class Friendship {
    String id1;
    String id2;
    boolean status;

    /**
     * Constructs a new Friendship between two users.
     * The friendship status is set to false (inactive) by default.
     *
     * @param id1 the ID of the first user
     * @param id2 the ID of the second user
     */
    public Friendship(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
        this.status = false;
    }

    /**
     * Returns the ID of the first user in the friendship.
     *
     * @return the first user's ID
     */
    public String getId1() {
        return id1;
    }

    /**
     * Returns the ID of the second user in the friendship.
     *
     * @return the second user's ID
     */
    public String getId2() {
        return id2;
    }

    /**
     * Returns the status of the friendship.
     *
     * @return true if the friendship is active/accepted, false otherwise
     */
    public boolean getStatus() {
        return status;
    }
}