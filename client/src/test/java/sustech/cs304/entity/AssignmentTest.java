package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

    @Test
    void testConstructorAndGetters_Submitted() {
        Long id = 123L;
        String assignmentName = "HW1";
        String deadline = "2025-06-01T23:59";
        String courseId = "CS304";
        boolean visible = true;
        String address = "http://fileserver/submit/123";
        String attachmentName = "attachment.pdf";
        String attachmentaddress = "http://fileserver/attachments/456";

        Assignment assignment = new Assignment(id, assignmentName, deadline, courseId, visible, address, attachmentName, attachmentaddress);

        assertEquals(id, assignment.getId());
        assertEquals(assignmentName, assignment.getAssignmentName());
        assertEquals(deadline, assignment.getDeadline());
        assertEquals(courseId, assignment.getCourseId());
        assertTrue(assignment.getVisible());
        assertTrue(assignment.getWhetherSubmitted());
        assertEquals(address, assignment.getAddress());
        assertEquals(attachmentName, assignment.getAttachmentName());
        assertEquals(attachmentaddress, assignment.getAttachmentaddress());
    }

    @Test
    void testConstructorAndGetters_NotSubmitted() {
        Assignment assignment = new Assignment(
                11L,
                "HW2",
                "2025-07-01T23:59",
                "CS101",
                false,
                null,
                null,
                null
        );

        assertEquals(11L, assignment.getId());
        assertEquals("HW2", assignment.getAssignmentName());
        assertEquals("2025-07-01T23:59", assignment.getDeadline());
        assertEquals("CS101", assignment.getCourseId());
        assertFalse(assignment.getVisible());
        assertFalse(assignment.getWhetherSubmitted());
        assertNull(assignment.getAddress());
        assertNull(assignment.getAttachmentName());
        assertNull(assignment.getAttachmentaddress());
    }

    @Test
    void testAssignmentWithEmptyStrings() {
        Assignment assignment = new Assignment(
                99L,
                "",
                "",
                "",
                true,
                "",
                "",
                ""
        );

        assertEquals(99L, assignment.getId());
        assertEquals("", assignment.getAssignmentName());
        assertEquals("", assignment.getDeadline());
        assertEquals("", assignment.getCourseId());
        assertTrue(assignment.getVisible());
        // address is empty string, so getWhetherSubmitted() should return true
        assertTrue(assignment.getWhetherSubmitted());
        assertEquals("", assignment.getAddress());
        assertEquals("", assignment.getAttachmentName());
        assertEquals("", assignment.getAttachmentaddress());
    }

    @Test
    void testAssignmentNullFields() {
        Assignment assignment = new Assignment(
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null
        );

        assertNull(assignment.getId());
        assertNull(assignment.getAssignmentName());
        assertNull(assignment.getDeadline());
        assertNull(assignment.getCourseId());
        assertFalse(assignment.getVisible());
        assertFalse(assignment.getWhetherSubmitted());
        assertNull(assignment.getAddress());
        assertNull(assignment.getAttachmentName());
        assertNull(assignment.getAttachmentaddress());
    }
}