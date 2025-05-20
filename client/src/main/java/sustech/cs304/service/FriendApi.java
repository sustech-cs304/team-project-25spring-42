package sustech.cs304.service;

import java.util.List;

import sustech.cs304.entity.User;

public interface FriendApi {
    void applyFriendship(String applicantId, String targetId);
    void acceptFriendship(String applicantId, String targetId);
    void rejectFriendship(String applicantId, String targetId);
    List<User> getFriendList(String userId);
    List<User> getFriendRequestList(String userId);
}
