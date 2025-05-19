package sustech.cs304.service;

import java.util.List;

public interface FriendApi {
    /**
     * Get the list of friends for a user.
     *
     * @param userId The ID of the user.
     * @return A list of friends for the user.
     */
    List<String> getFriends(String userId);

    /**
     * Add a friend for a user.
     *
     * @param userId The ID of the user.
     * @param friendId The ID of the friend to add.
     * @return True if the friend was added successfully, false otherwise.
     */
    boolean addFriend(String userId, String friendId);

    /**
     * Remove a friend for a user.
     *
     * @param userId The ID of the user.
     * @param friendId The ID of the friend to remove.
     * @return True if the friend was removed successfully, false otherwise.
     */
    boolean removeFriend(String userId, String friendId);
}
