package sustech.cs304.entity;

import java.util.ArrayList;

/**
 * Represents a user in the system, including profile information,
 * registration details, contact information, and course participation.
 */
public class User {
    private String userId;
    private String username;
    private String account;
    private String bio;
    private String avatarPath;
    private String registerDate;
    private String lastLogin;
    private ArrayList<Course> coursesAsTeacher;
    private ArrayList<Course> coursesAsStudent;
    private String email;
    private String phoneNumber;

    /**
     * Constructs a new User with the specified user ID.
     *
     * @param userId the unique identifier for the user
     */
    public User(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the list of courses where the user is a teacher.
     *
     * @return the list of teacher courses
     */
    public ArrayList<Course> getCoursesAsTeacher() {
        return coursesAsTeacher;
    }

    /**
     * Returns the list of courses where the user is a student.
     *
     * @return the list of student courses
     */
    public ArrayList<Course> getCoursesAsStudent() {
        return coursesAsStudent;
    }

    /**
     * Returns the user's unique identifier.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the user's display name.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's account name or login.
     *
     * @return the account name
     */
    public String getAccount() {
        return account;
    }

    /**
     * Returns the user's biography or personal description.
     *
     * @return the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Returns the date the user registered.
     *
     * @return the registration date
     */
    public String getRegisterDate() {
        return registerDate;
    }

    /**
     * Returns the last login time of the user.
     *
     * @return the last login time
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     * Returns the path to the user's avatar image.
     *
     * @return the avatar path
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
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
     * Sets the user's unique identifier.
     *
     * @param userId the user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Sets the user's display name.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the user's account name or login.
     *
     * @param account the account name
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Sets the user's biography or personal description.
     *
     * @param bio the bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Sets the path to the user's avatar image.
     *
     * @param avatarPath the avatar path
     */
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    /**
     * Sets the date the user registered.
     *
     * @param registerDate the registration date
     */
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * Sets the last login time of the user.
     *
     * @param lastLogin the last login time
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
