package sustech.cs304.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class CourseApiImplTest extends TestCase {
    private CourseApi courseApi = new CourseApiImpl();

    @Test
    void testGetCourseIdByUserId() {
        String userId = "testUser";
        var courseIds = courseApi.getCourseIdByUserId(userId);
        assertNotNull(courseIds);
    }

    @Test
    void testGetCourseByUserId() {
        String userId = "testUser";
        var courses = courseApi.getCourseByUserId(userId);
        assertNotNull(courses);
    }

    @Test
    void testGetCourseById() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        var course = courseApi.getCourseById(courseId);
        assertNotNull(course);
    }

    @Test
    void testGetAnnounceByCourseId() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        var announces = courseApi.getAnnounceByCourseId(courseId);
        assertNotNull(announces);
    }

    @Test
    void testCreateAnnouncment() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        String announceName = "Test Announcement";
        String announceContent = "This is a test announcement.";
        String userId = "testUser";
        Boolean result = courseApi.createAnnouncment(courseId, announceName, announceContent, userId);
        assertFalse(result);
    }

    @Test
    void testGetResourceByCourseId() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        var resources = courseApi.getResourceByCourseId(courseId);
        assertNotNull(resources);
    }

    @Test
    void testUploadResource() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        String address = "http://example.com/resource";
        String userId = "testUser";
        courseApi.uploadResource(courseId, address, userId);
        // No return value to assert, just checking for exceptions
    }

    @Test
    void testGetAssignmentByCourseId() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        String userId = "testUser";
        var assignments = courseApi.getAssignmentByCourseId(courseId, userId);
        assertNull(assignments);
    }

    @Test
    void testCreateAssignment() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        String assignmentName = "Test Assignment";
        String assignmentContent = "This is a test assignment.";
        String userId = "testUser";
        String address = "http://example.com/assignment";
        Boolean result = courseApi.createAssignment(courseId, assignmentName, assignmentContent, userId, address);
        assertFalse(result);
    }

    @Test
    void testGetAttachmentByAssignmentId() {
        Long assignmentId = 1L; // Assuming an assignment with ID 1 exists
        var attachment = courseApi.getAttachmentByAssignmentId(assignmentId);
        assertNull(attachment);
    }

    @Test
    void testDownloadResource() {
        String address = "http://example.com/resource";
        String userId = "testUser";
        courseApi.downloadResource(address, userId);
        // No return value to assert, just checking for exceptions
    }

    @Test
    void testSubmitAssignment() {
        Long assignmentId = 1L; // Assuming an assignment with ID 1 exists
        String userId = "testUser";
        String address = "http://example.com/assignment";
        courseApi.submitAssignment(assignmentId, userId, address);
        // No return value to assert, just checking for exceptions
    }

    @Test
    void testCreateCourse() {
        String courseName = "Test Course";
        String userId = "testUser";
        courseApi.createCourse(courseName, userId);
    }

    @Test
    void testGetAdminIdByCourseId() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        String adminId = courseApi.getAdminIdByCourseId(courseId);
        assertNotNull(adminId);
    }

    @Test
    void testDeleteCourse() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        String adminId = "testAdmin";
        courseApi.deleteCourse(courseId, adminId);
        // No return value to assert, just checking for exceptions
    }

    @Test
    void testGetUserByCourseId() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        var users = courseApi.getUserByCourseId(courseId);
        assertNotNull(users);
    }

    @Test
    void testCreateCourseInvitation() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        List<String> userIds = List.of("testFriend1", "testFriend2");
        courseApi.createCourseInvitation(courseId, userIds);
    }

    @Test
    void testAcceptCourseInvitation() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        String userId = "testUser";
        courseApi.acceptCourseInvitation(courseId, userId);
        // No return value to assert, just checking for exceptions
    }

    @Test
    void testRejectCourseInvitation() {
        Long courseId = 1L; // Assuming a course with ID 1 exists
        String userId = "testUser";
        courseApi.rejectCourseInvitation(courseId, userId);
        // No return value to assert, just checking for exceptions
    }

    @Test
    void testGetCourseInvitationByUserId() {
        String userId = "testUser";
        var invitations = courseApi.getCourseInvitationByUserId(userId);
        assertNotNull(invitations);
    }

    @Test
    void testGetSubmissionByAssignmentId() {
        Long assignmentId = 1L; // Assuming an assignment with ID 1 exists
        var submissions = courseApi.getSubmissionByAssignmentId(assignmentId);
        assertNotNull(submissions);
    }
}
