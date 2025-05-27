package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendTest {

    @Test
    void testConstructorAndGetters() {
        Friend friend = new Friend("f123", "Alice", "online", "/avatars/alice.png");

        assertEquals("f123", friend.getId());
        assertEquals("Alice", friend.getName());
        assertEquals("online", friend.getStatus());
        assertEquals("/avatars/alice.png", friend.getAvatar());
    }

    @Test
    void testSetters() {
        Friend friend = new Friend("f1", "Bob", "offline", "/avatars/bob.png");

        friend.setId("f2");
        assertEquals("f2", friend.getId());

        friend.setName("Charlie");
        assertEquals("Charlie", friend.getName());

        friend.setStatus("busy");
        assertEquals("busy", friend.getStatus());

        friend.setAvatar("/avatars/charlie.png");
        assertEquals("/avatars/charlie.png", friend.getAvatar());
    }

    @Test
    void testNullValues() {
        Friend friend = new Friend(null, null, null, null);

        assertNull(friend.getId());
        assertNull(friend.getName());
        assertNull(friend.getStatus());
        assertNull(friend.getAvatar());

        friend.setId("f3");
        friend.setName("Dana");
        friend.setStatus("away");
        friend.setAvatar("/avatars/dana.png");

        assertEquals("f3", friend.getId());
        assertEquals("Dana", friend.getName());
        assertEquals("away", friend.getStatus());
        assertEquals("/avatars/dana.png", friend.getAvatar());
    }
}