package sustech.cs304.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FriendshipTest {

    @Test
    void testConstructorAndGetters() {
        Friendship friendship = new Friendship("userA", "userB");
        assertEquals("userA", friendship.getId1());
        assertEquals("userB", friendship.getId2());
        assertFalse(friendship.getStatus(), "Status should be false by default");
    }

    @Test
    void testDifferentUsers() {
        Friendship friendship = new Friendship("alice", "bob");
        assertNotEquals(friendship.getId1(), friendship.getId2());
        assertEquals("alice", friendship.getId1());
        assertEquals("bob", friendship.getId2());
    }

    @Test
    void testNullIds() {
        Friendship friendship = new Friendship(null, null);
        assertNull(friendship.getId1());
        assertNull(friendship.getId2());
        assertFalse(friendship.getStatus());
    }
}