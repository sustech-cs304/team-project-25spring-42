package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 1001L;
        String name = "CS304";
        String adminId = "admin123";
        String openTime = "2025-01-01T09:00";
        String closeTime = "2025-06-01T16:00";
        boolean opening = true;

        Course course = new Course(id, name, adminId, openTime, closeTime, opening);

        assertEquals(id, course.getId());
        assertEquals(name, course.getCourseName());
        assertEquals(adminId, course.getAdminId());
        assertEquals(openTime, course.getOpenTime());
        assertEquals(closeTime, course.getCloseTime());
        assertTrue(course.isOpening());
    }

    @Test
    void testSettersAndGetters() {
        Course course = new Course();

        course.setId(12345L);
        assertEquals(12345L, course.getId());

        course.setCourseName("Software Engineering");
        assertEquals("Software Engineering", course.getCourseName());

        course.setAdminId("admin789");
        assertEquals("admin789", course.getAdminId());

        course.setOpenTime("2024-09-01T08:00");
        assertEquals("2024-09-01T08:00", course.getOpenTime());

        course.setCloseTime("2025-01-15T18:00");
        assertEquals("2025-01-15T18:00", course.getCloseTime());

        course.setOpening(true);
        assertTrue(course.isOpening());

        course.setOpening(false);
        assertFalse(course.isOpening());
    }
}