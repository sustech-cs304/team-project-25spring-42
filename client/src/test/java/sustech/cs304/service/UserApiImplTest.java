package sustech.cs304.service;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class UserApiImplTest extends TestCase {
    private UserApi userApi = new UserApiImpl();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Initialize any necessary resources or mock data here
    }

    @Test
    void testGetUserById() {
        String userId = "testUser";
        var user = userApi.getUserById(userId);
    }

    @Test
    void testGetUsernameById() {
        String userName = "testUser";
        var username = userApi.getUsernameById(userName);
    }

    @Test
    void testGetUserAvatarById() {
        String userId = "testUser";
        var avatar = userApi.getUserAvatarById(userId);
    }

    @Test
    void testGetUserBioById() {
        String userId = "testUser";
        var bio = userApi.getUserBioById(userId);
    }

    @Test
    void testGetUserEmailById() {
        String userId = "testUser";
        var email = userApi.getUserEmailById(userId);
    }

    @Test
    void testGetUserPhoneById() {
        String userId = "testUser";
        var phone = userApi.getUserPhoneById(userId);
    }

    @Test
    void testGetUserLastLoginTimeById() {
        String userId = "testUser";
        var lastLoginTime = userApi.getUserLastLoginTimeById(userId);
    }

    @Test
    void testGetUserRegisterTimeById() {
        String userId = "testUser";
        var registerTime = userApi.getUserRegisterTimeById(userId);
    }

    @Test
    void testUpdateUsernameById() {
        String userId = "testUser";
        String newUsername = "newTestUser";
        var result = userApi.updateUsernameById(userId, newUsername);
        assertFalse(result);
    }

    @Test
    void testUpdateAvatarById() {
        String userId = "testUser";
        String newAvatar = "http://example.com/newAvatar.png";
        var result = userApi.updateAvatarById(userId, newAvatar);
        assertFalse(result);
    }

    @Test
    void testUpdateBioById() {
        String userId = "testUser";
        String newBio = "This is a new bio.";
        var result = userApi.updateBioById(userId, newBio);
        assertFalse(result);
    }

    @Test
    void testUpdateEmailById() {
        String userId = "testUser";
        String newEmail = "hello@mail.com";
        var result = userApi.updateMailById(userId, newEmail);
        assertFalse(result);
    }

    @Test
    void testUpdatePhoneById() {
        String userId = "testUser";
        String newPhone = "1234567890";
        var result = userApi.updatePhoneById(userId, newPhone);
        assertFalse(result);
    }
}
