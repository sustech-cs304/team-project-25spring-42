package sustech.cs304.service;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class FriendApiImplTest extends TestCase {
    private FriendApi friendApi = new FriendApiImpl();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Initialize any necessary resources or mock data here
    }

    @Test
    void testApplyFriendship() {
        String userId = "testUser";
        String friendId = "testFriend";
        friendApi.applyFriendship(userId, friendId);
    }

    @Test
    void testAcceptFriendship() {
        String userId = "testUser";
        String friendId = "testFriend";
        friendApi.acceptFriendship(userId, friendId);
    }

    @Test
    void testRejectFriendship() {
        String userId = "testUser";
        String friendId = "testFriend";
        friendApi.rejectFriendship(userId, friendId);
    }

    @Test
    void testGetFriendList() {
        String userId = "testUser";
        var friends = friendApi.getFriendList(userId);
    }

    @Test
    void testGetFriendRequestList() {
        String userId = "testUser";
        var requests = friendApi.getFriendRequestList(userId);
    }
}
