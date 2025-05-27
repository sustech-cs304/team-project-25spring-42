package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnnounceTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 1L;
        String courseId = "CS304";
        String announceName = "Exam Info";
        String upLoadTime = "2025-05-27T10:00:00";
        String announceContent = "Final exam at 8:00 AM in Room 101.";
        boolean visible = true;

        Announce announce = new Announce(id, courseId, announceName, upLoadTime, announceContent, visible);

        assertEquals(id, announce.getId());
        assertEquals(courseId, announce.getCourseId());
        assertEquals(announceName, announce.getAnnounceName());
        assertEquals(upLoadTime, announce.getUpLoadTime());
        assertEquals(announceContent, announce.getAnnounceContent());
        assertTrue(announce.isVisible());
    }

    @Test
    void testInvisibleAnnouncement() {
        Announce announce = new Announce(2L, "CS101", "Hidden", "2025-01-01T09:00:00", "Not shown", false);
        assertFalse(announce.isVisible());
    }

    @Test
    void testNullValue() {
        Announce announce = new Announce(null, null, null, null, null, false);
        assertNull(announce.getId());
        assertNull(announce.getCourseId());
        assertNull(announce.getAnnounceName());
        assertNull(announce.getUpLoadTime());
        assertNull(announce.getAnnounceContent());
        assertFalse(announce.isVisible());
    }
}