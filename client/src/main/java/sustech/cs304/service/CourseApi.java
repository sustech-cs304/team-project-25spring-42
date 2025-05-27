package sustech.cs304.service;

import java.util.List;

import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Assignment;
import sustech.cs304.entity.Course;
import sustech.cs304.entity.Resource;
import sustech.cs304.entity.Submission;
import sustech.cs304.entity.User;

/**
 * CourseApi is an interface that defines methods for managing courses.
 * It provides methods to retrieve course information, create announcements,
 * manage resources, assignments, and handle course invitations.
 */
public interface CourseApi {
    /**
     * Retrieves a list of course IDs associated with a given user ID.
     *
     * @param userId The ID of the user.
     * @return A list of course IDs.
     */
    List<Long> getCourseIdByUserId(String userId);
    /**
     * Retrieves a list of courses associated with a given user ID.
     *
     * @param userId The ID of the user.
     * @return A list of Course objects.
     */
    List<Course> getCourseByUserId(String userId);
    /**
     * Retrieves a course by its ID.
     *
     * @param courseId The ID of the course.
     * @return A Course object.
     */
    Course getCourseById(Long courseId);
    /**
     * Retrieves a list of announcements for a given course ID.
     *
     * @param courseId The ID of the course.
     * @return A list of Announce objects.
     */
    List<Announce> getAnnounceByCourseId(Long courseId);
    /**
     * Creates an announcement for a given course.
     *
     * @param courseId The ID of the course.
     * @param announceName The name of the announcement.
     * @param announceContent The content of the announcement.
     * @param userId The ID of the user creating the announcement.
     * @return true if the announcement was created successfully, false otherwise.
     */
    Boolean createAnnouncment(Long courseId, String announceName, String announceContent, String userId);
    /**
     * Retrieves a list of resources associated with a given course ID.
     *
     * @param courseId The ID of the course.
     * @return A list of Resource objects.
     */
    List<Resource> getResourceByCourseId(Long courseId);
    /**
     * Uploads a resource to a course.
     *
     * @param courseId The ID of the course.
     * @param address The address of the resource.
     * @param userId The ID of the user uploading the resource.
     */
    void uploadResource(Long courseId, String address, String userId);
    /**
     * Retrieves a list of assignments for a given course ID.
     *
     * @param courseId The ID of the course.
     * @param userId The ID of the user.
     * @return A list of Assignment objects.
     */
    List<Assignment> getAssignmentByCourseId(Long courseId, String userId);
    /**
     * Creates an assignment for a given course.
     *
     * @param courseId The ID of the course.
     * @param assignmentName The name of the assignment.
     * @param deadline The deadline for the assignment.
     * @param userId The ID of the user creating the assignment.
     * @param address The address of the assignment resource.
     * @return true if the assignment was created successfully, false otherwise.
     */
    Boolean createAssignment(Long courseId, String assignmentName, String deadline, String userId, String address);
    /**
     * Retrieves a resource by its assignment ID.
     *
     * @param assignmentId The ID of the assignment.
     * @return A Resource object associated with the assignment.
     */
    Resource getAttachmentByAssignmentId(Long assignmentId);
    /**
     * Downloads a resource from a given address and saves it to a specified path.
     *
     * @param address The address of the resource.
     * @param savePath The path where the resource will be saved.
     */
    void downloadResource(String address, String savePath);
    /**
     * Submits an assignment for a given assignment ID and user ID.
     *
     * @param assignmentId The ID of the assignment.
     * @param userId The ID of the user submitting the assignment.
     * @param address The address where the assignment is submitted.
     */
    void submitAssignment(Long assignmentId, String userId, String address);
    /**
     * Creates a course with a given name and user ID.
     *
     * @param courseName The name of the course.
     * @param userId The ID of the user creating the course.
     */
    void createCourse(String courseName, String userId);
    /**
     * Retrieves the admin ID for a given course ID.
     *
     * @param courseId The ID of the course.
     * @return The ID of the admin associated with the course.
     */
    String getAdminIdByCourseId(Long courseId);
    /**
     * Deletes a course by its ID.
     *
     * @param courseId The ID of the course to be deleted.
     * @param adminId The ID of the admin performing the deletion.
     */
    void deleteCourse(Long courseId, String adminId);
    /**
     * Retrieves a list of users associated with a given course ID.
     *
     * @param courseId The ID of the course.
     * @return A list of User objects associated with the course.
     */
    List<User> getUserByCourseId(Long courseId);
    /**
     * Creates a course invitation for a given course ID and a list of user IDs.
     *
     * @param courseId The ID of the course.
     * @param userIds A list of user IDs to be invited to the course.
     */
    void createCourseInvitation(Long courseId, List<String> userIds);
    /**
     * Accepts a course invitation for a given course ID and user ID.
     *
     * @param courseId The ID of the course.
     * @param userId The ID of the user accepting the invitation.
     */
    void acceptCourseInvitation(Long courseId, String userId);
    /**
     * Rejects a course invitation for a given course ID and user ID.
     *
     * @param courseId The ID of the course.
     * @param userId The ID of the user rejecting the invitation.
     */
    void rejectCourseInvitation(Long courseId, String userId);
    /**
     * Retrieves a list of course invitations for a given user ID.
     *
     * @param userId The ID of the user.
     * @return A list of Course objects representing the invitations.
     */
    List<Course> getCourseInvitationByUserId(String userId);
    /**
     * Retrieves a list of submissions for a given assignment ID.
     *
     * @param assignmentId The ID of the assignment.
     * @return A list of Submission objects associated with the assignment.
     */
    List<Submission> getSubmissionByAssignmentId(Long assignmentId);

}
