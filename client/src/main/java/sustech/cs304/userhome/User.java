package sustech.cs304.userhome;

import sustech.cs304.classroom.Course;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ArrayList;
import java.time.LocalDate;
import sustech.cs304.utils.ServerUtils;
import sustech.cs304.userhome.UserServerSide;

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
    private String email;
    private String phoneNumber;

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

    public static String getSavedUserId() {
        // it is the only string saved in the ./savedUserId.txt
        File file = new File("./savedUserId.txt");
        if (!file.exists()) {
            return null;
        }
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadUserData() {
        UserServerSide serverSideUser = ServerUtils.serverLoadUserData("google103481236524820488484");
        this.userId = serverSideUser.getPlatformId();
        this.username = serverSideUser.getUsername();
        this.avatarPath = serverSideUser.getAvatarUrl();
        this.registerDate = serverSideUser.getRegisterTime();
        this.lastLogin = serverSideUser.getLastLoginTime();
        this.phoneNumber = serverSideUser.getPhoneNumber();
        this.email = serverSideUser.getEmail();
        this.bio = "empty bio";
        this.account = "user@example.com";
        this.password = "encryptedPassword123";
        this.coursesAsTeacher = new ArrayList<>();
        this.coursesAsStudent = new ArrayList<>();
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

    public void createAssignment(Course course, String name, LocalDate dueDate, String status, String discription) {
        boolean isSucess = course.addAssignment(this, name, dueDate, status, discription);
        if (isSucess) {
            System.out.println("Assignment created");
        } else {
            System.out.println("Assignment not created");
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
