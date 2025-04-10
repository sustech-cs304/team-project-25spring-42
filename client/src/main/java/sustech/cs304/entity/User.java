package sustech.cs304.entity;

import java.util.ArrayList;

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

    public User(String userId) {
        this.userId = userId;
    }

    public ArrayList<Course> getCoursesAsTeacher() {
        return coursesAsTeacher;
    }

    public ArrayList<Course> getCoursesAsStudent() {
        return coursesAsStudent;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    public String getBio() {
        return bio;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
