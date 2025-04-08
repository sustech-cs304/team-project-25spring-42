package sustech.cs304.userhome;

import sustech.cs304.classroom.Course;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ArrayList;

public class User {
    // 用户数据模型
    private String userId;
    private String username;
    private String account;
    private String password;
    private String bio;
    private String avatarPath;
    private String registerDate;
    private String lastLogin;
    private ArrayList<Course> coursesAsTeacher;
    private ArrayList<Course> coursesAsStudent;

    private static User instance;

    private User() {
        loadUserData();
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void loadUserData() {
        // 模拟从数据库加载用户数据
        userId = "U10000000";
        username = "User";
        account = "user@example.com";
        password = "encryptedPassword123"; // 实际应用中应该是加密后的密码
        bio = "input self introduction";
        avatarPath = null; // 默认使用内置头像
        registerDate = "2000-10-10";
        lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void createCourse() {
        Course course = new Course();
        course.setTeacher(this);
        this.coursesAsTeacher.add(course);
    }

    public void joinCourse(Course course) {
        if (!this.coursesAsStudent.contains(course)) {
            course.addStudent(this);
            this.coursesAsStudent.add(course);
        }
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
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
}