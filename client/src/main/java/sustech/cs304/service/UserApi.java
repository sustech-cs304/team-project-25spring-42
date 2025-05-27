package sustech.cs304.service;

import sustech.cs304.entity.User;

/**
 * UserApi is an interface that defines methods for managing user information.
 * It provides methods to retrieve user details, update user information, and manage user accounts.
 */
public interface UserApi {
    /**
     * Retrieves a User object by its ID.
     *
     * @param userId The ID of the user.
     * @return A User object containing user details.
     */
    User getUserById(String userId);
    /**
     * Retrieves a User object by its username.
     *
     * @param userId The username of the user.
     * @return A User object containing user details.
     */
    String getUsernameById(String userId);
    /**
     * Retrieves the avatar URL of a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The URL of the user's avatar.
     */
    String getUserAvatarById(String userId);
    /**
     * Retrieves the bio of a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The bio of the user.
     */
    String getUserBioById(String userId);
    /**
     * Retrieves the email of a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The email of the user.
     */
    String getUserEmailById(String userId);
    /**
     * Retrieves the phone number of a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The phone number of the user.
     */
    String getUserPhoneById(String userId);
    /**
     * Retrieves the last login time of a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The last login time of the user.
     */
    String getUserLastLoginTimeById(String userId);
    /**
     * Retrieves the registration time of a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The registration time of the user.
     */
    String getUserRegisterTimeById(String userId);
    /**
     * Updates the username of a user by their ID.
     *
     * @param userId The ID of the user.
     * @param newUsername The new username to set.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateUsernameById(String userId, String newUsername);
    /**
     * Updates the avatar URL of a user by their ID.
     *
     * @param userId The ID of the user.
     * @param newAvatarUrl The new avatar URL to set.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateAvatarById(String userId, String newAvatarUrl);
    /**
     * Updates the email of a user by their ID.
     *
     * @param userId The ID of the user.
     * @param newMail The new email to set.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateMailById(String userId, String newMail);
    /**
     * Updates the phone number of a user by their ID.
     *
     * @param userId The ID of the user.
     * @param newPhone The new phone number to set.
     * @return true if the update was successful, false otherwise.
     */
    boolean updatePhoneById(String userId, String newPhone);
    /**
     * Updates the bio of a user by their ID.
     *
     * @param userId The ID of the user.
     * @param newBio The new bio to set.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateBioById(String userId, String newBio);
}
