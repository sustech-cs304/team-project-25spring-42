package sustech.cs304.entity;

import java.time.LocalDateTime;

/**
 * Represents a user entity on the server side, containing profile information,
 * registration and login times, and contact details.
 */
public class UserServerSide {
    private String platformId;
    private String username;
    private String avatarUrl;
    private String registerTime;
    private String lastLoginTime;
    private String phoneNumber;
    private String email;
    private String bio;

    /**
     * Constructs a new UserServerSide with the specified profile and contact information.
     *
     * @param platformId     the unique platform identifier for the user
     * @param username       the username of the user
     * @param avatarUrl      the URL of the user's avatar image
     * @param registerTime   the registration time of the user
     * @param lastLoginTime  the last login time of the user
     * @param phoneNumber    the phone number of the user
     * @param email          the email address of the user
     * @param bio            the biography or introduction of the user
     */
    public UserServerSide(String platformId, String username, String avatarUrl, String registerTime, String lastLoginTime, String phoneNumber, String email, String bio) {
        this.platformId = platformId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bio = bio;
    }
    /**
     * Returns the platform ID of the user.
     *
     * @return the platform ID
     */
    public String getPlatformId() {
        return platformId;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the URL of the user's avatar image.
     *
     * @return the avatar URL
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Returns the registration time of the user.
     *
     * @return the registration time
     */
    public String getRegisterTime() {
        return registerTime;
    }

    /**
     * Returns the last login time of the user.
     *
     * @return the last login time
     */
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * Returns the user's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the biography or personal introduction of the user.
     *
     * @return the bio
     */
    public String getBio() {
        return bio;
    }
}
