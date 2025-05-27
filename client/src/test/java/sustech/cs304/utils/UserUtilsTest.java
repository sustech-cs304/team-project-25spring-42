package sustech.cs304.utils;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import sustech.cs304.entity.User;
import sustech.cs304.entity.UserServerSide;
import sustech.cs304.service.UserApi;
import sustech.cs304.service.UserApiImpl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUtilsTest {

    private static Path tempDir;
    private static String originalAppDataPath;

    @Test
    void testLoadUserFromServerSide() {
        String platformId = "p001";
        String username = "testuser";
        String avatarUrl = "http://example.com/avatar.png";
        String registerTime = "2025-01-01T12:00:00";
        String lastLoginTime = "2025-05-27T10:00:00";
        String phoneNumber = "12345678901";
        String email = "testuser@example.com";
        String bio = "This is a test user.";
        UserServerSide serverSide = new UserServerSide(
                platformId, username, avatarUrl, registerTime, lastLoginTime,
                phoneNumber, email, bio
        );
        User user = UserUtils.loadUser(serverSide);

        assertEquals(platformId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(avatarUrl, user.getAvatarPath());
        assertEquals(registerTime, user.getRegisterDate());
        assertEquals(lastLoginTime, user.getLastLogin());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(email, user.getEmail());
        assertEquals(bio, user.getBio());
    }
}