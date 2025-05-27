package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServerSideTest {

    @Test
    void testConstructorAndGetters() {
        String platformId = "p001";
        String username = "testuser";
        String avatarUrl = "http://example.com/avatar.png";
        String registerTime = "2025-01-01T12:00:00";
        String lastLoginTime = "2025-05-27T10:00:00";
        String phoneNumber = "12345678901";
        String email = "testuser@example.com";
        String bio = "This is a test user.";

        UserServerSide user = new UserServerSide(
                platformId, username, avatarUrl, registerTime, lastLoginTime,
                phoneNumber, email, bio
        );

        assertEquals(platformId, user.getPlatformId());
        assertEquals(username, user.getUsername());
        assertEquals(avatarUrl, user.getAvatarUrl());
        assertEquals(registerTime, user.getRegisterTime());
        assertEquals(lastLoginTime, user.getLastLoginTime());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(email, user.getEmail());
        assertEquals(bio, user.getBio());
    }

    @Test
    void testNullFields() {
        UserServerSide user = new UserServerSide(
                null, null, null, null, null, null, null, null
        );

        assertNull(user.getPlatformId());
        assertNull(user.getUsername());
        assertNull(user.getAvatarUrl());
        assertNull(user.getRegisterTime());
        assertNull(user.getLastLoginTime());
        assertNull(user.getPhoneNumber());
        assertNull(user.getEmail());
        assertNull(user.getBio());
    }

    @Test
    void testEmptyStrings() {
        UserServerSide user = new UserServerSide(
                "", "", "", "", "", "", "", ""
        );

        assertEquals("", user.getPlatformId());
        assertEquals("", user.getUsername());
        assertEquals("", user.getAvatarUrl());
        assertEquals("", user.getRegisterTime());
        assertEquals("", user.getLastLoginTime());
        assertEquals("", user.getPhoneNumber());
        assertEquals("", user.getEmail());
        assertEquals("", user.getBio());
    }
}