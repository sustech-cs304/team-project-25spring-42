package sustech.cs304.service;

import java.util.List;

import sustech.cs304.entity.User;

/**
 * FriendApi is an interface that defines methods for managing friendships.
 * It provides methods to apply for friendship, accept or reject friendship requests,
 * and retrieve lists of friends and friend requests.
 */
public interface FriendApi {
    /**
     * Applies for friendship with a target user.
     *
     * @param applicantId The ID of the user who is applying for friendship.
     * @param targetId The ID of the user who is being applied to.
     */
    void applyFriendship(String applicantId, String targetId);
    /**
     * Accepts a friendship request from a user.
     *
     * @param applicantId The ID of the user who applied for friendship.
     * @param targetId The ID of the user who is accepting the friendship.
     */
    void acceptFriendship(String applicantId, String targetId);
    /**
     * Rejects a friendship request from a user.
     *
     * @param applicantId The ID of the user who applied for friendship.
     * @param targetId The ID of the user who is rejecting the friendship.
     */
    void rejectFriendship(String applicantId, String targetId);
    /**
     * Retrieves a list of friends for a given user ID.
     *
     * @param userId The ID of the user.
     * @return A list of User objects representing the user's friends.
     */
    List<User> getFriendList(String userId);
    /**
     * Retrieves a list of friend requests for a given user ID.
     *
     * @param userId The ID of the user.
     * @return A list of User objects representing the user's friend requests.
     */
    List<User> getFriendRequestList(String userId);
}
