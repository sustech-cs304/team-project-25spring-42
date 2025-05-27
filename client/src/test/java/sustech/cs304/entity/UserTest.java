package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetUserId() {
        User user = new User("u123");
        assertEquals("u123", user.getUserId());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User("init");
        user.setUserId("u456");
        user.setUsername("Tom");
        user.setAccount("tomcat");
        user.setBio("Hello world");
        user.setAvatarPath("/images/tom.png");
        user.setRegisterDate("2025-05-01");
        user.setLastLogin("2025-05-27 09:00:00");
        user.setEmail("tom@example.com");
        user.setPhoneNumber("12345678901");

        assertEquals("u456", user.getUserId());
        assertEquals("Tom", user.getUsername());
        assertEquals("tomcat", user.getAccount());
        assertEquals("Hello world", user.getBio());
        assertEquals("/images/tom.png", user.getAvatarPath());
        assertEquals("2025-05-01", user.getRegisterDate());
        assertEquals("2025-05-27 09:00:00", user.getLastLogin());
        assertEquals("tom@example.com", user.getEmail());
        assertEquals("12345678901", user.getPhoneNumber());
    }

    @Test
    void testNullAndEmptyFields() {
        User user = new User(null);

        user.setUsername(null);
        user.setAccount("");
        user.setBio(null);
        user.setAvatarPath("");
        user.setRegisterDate(null);
        user.setLastLogin("");
        user.setEmail(null);
        user.setPhoneNumber("");

        assertNull(user.getUserId());
        assertNull(user.getUsername());
        assertEquals("", user.getAccount());
        assertNull(user.getBio());
        assertEquals("", user.getAvatarPath());
        assertNull(user.getRegisterDate());
        assertEquals("", user.getLastLogin());
        assertNull(user.getEmail());
        assertEquals("", user.getPhoneNumber());
    }

    @Test
    void testCoursesAsTeacherAndStudent() {
        User user = new User("u789");
        assertNull(user.getCoursesAsTeacher());
        assertNull(user.getCoursesAsStudent());

        // set and get courses
        ArrayList<Course> teacherCourses = new ArrayList<>();
        teacherCourses.add(new Course(1L, "Java", "u789", "2025-01-01", "2025-06-01", true));
        ArrayList<Course> studentCourses = new ArrayList<>();
        studentCourses.add(new Course(2L, "Python", "u999", "2025-02-01", "2025-07-01", false));

        // Use Reflection to set private fields since there are no setters for the courses lists
        try {
            java.lang.reflect.Field tField = User.class.getDeclaredField("coursesAsTeacher");
            tField.setAccessible(true);
            tField.set(user, teacherCourses);

            java.lang.reflect.Field sField = User.class.getDeclaredField("coursesAsStudent");
            sField.setAccessible(true);
            sField.set(user, studentCourses);

            assertEquals(1, user.getCoursesAsTeacher().size());
            assertEquals("Java", user.getCoursesAsTeacher().get(0).getCourseName());
            assertEquals(1L, user.getCoursesAsTeacher().get(0).getId());

            assertEquals(1, user.getCoursesAsStudent().size());
            assertEquals("Python", user.getCoursesAsStudent().get(0).getCourseName());
            assertEquals(2L, user.getCoursesAsStudent().get(0).getId());
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
        }
    }
}