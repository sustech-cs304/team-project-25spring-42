package sustech.cs304.AIDE.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.model.CourseInvitation;
import sustech.cs304.AIDE.model.Enrollment;
import sustech.cs304.AIDE.repository.CourseInvitationRepository;
import sustech.cs304.AIDE.repository.CourseRepository;
import sustech.cs304.AIDE.repository.EnrollmentRepository;
import sustech.cs304.AIDE.repository.UserRepository;

/**
 * Controller for managing course invitations.
 *
 * This class handles the creation, acceptance, rejection, and retrieval of course invitations.
 */
@RestController
@RequestMapping("/course-invitation")
public class CourseInvitationController {
    private final CourseInvitationRepository courseInvitationRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    /**
     * Constructor for CourseInvitationController.
     *
     * @param courseInvitationRepository the repository for course invitations
     * @param userRepository the repository for users
     * @param enrollmentRepository the repository for enrollments
     * @param courseRepository the repository for courses
     */
    public CourseInvitationController(
        CourseInvitationRepository courseInvitationRepository,
        UserRepository userRepository,
        EnrollmentRepository enrollmentRepository,
        CourseRepository courseRepository
    ) {
        this.courseInvitationRepository = courseInvitationRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Creates invitations for a course to a list of users.
     *
     * @param courseId the ID of the course
     * @param userIds a comma-separated string of user IDs
     * @return a response entity indicating success or failure
     */
    @GetMapping("/createInvitation")
    @Transactional
    public ResponseEntity<String> createInvitation(@RequestParam Long courseId, @RequestParam String userIds) {
        String[] userIdArray = userIds.split(",");

        if (userIdArray == null || userIdArray.length == 0) {
            return ResponseEntity.badRequest().body("User IDs cannot be null or empty");
        }
        for (String userId : userIdArray) {
            if (userRepository.findByPlatformId(userId).isEmpty()) {
                continue;
            }
            Optional<Enrollment> enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
            if (enrollment.isPresent()) {
                continue;
            }
            CourseInvitation courseInvitation = new CourseInvitation(courseId, userId);
            courseInvitationRepository.save(courseInvitation);
        } 
        return ResponseEntity.ok("Invitations created successfully");
    }

    /**
     * Retrieves a list of course invitations for a specific user.
     *
     * @param courseId the ID of the course
     * @param userId the ID of the user
     * @return a response entity containing the list of course invitations
     */
    @GetMapping("/acceptInvitation")
    @Transactional
    public ResponseEntity<String> acceptInvitation(@RequestParam Long courseId, @RequestParam String userId) {
        Optional<CourseInvitation> courseInvitation = courseInvitationRepository.findByCourseIdAndUserId(courseId, userId);
        if (courseInvitation.isPresent()) {
            courseInvitationRepository.delete(courseInvitation.get());
            Enrollment enrollment = new Enrollment(courseId, userId);
            enrollmentRepository.save(enrollment);
        } else {
            return ResponseEntity.badRequest().body("No invitation found for this course and user");
        }

        return ResponseEntity.ok("Invitation accepted successfully");
    }

    /**
     * Rejects a course invitation for a specific user.
     *
     * @param courseId the ID of the course
     * @param userId the ID of the user
     * @return a response entity indicating success or failure
     */
    @GetMapping("/rejectInvitation")
    @Transactional
    public ResponseEntity<String> rejectInvitation(@RequestParam Long courseId, @RequestParam String userId) {
        Optional<CourseInvitation> courseInvitation = courseInvitationRepository.findByCourseIdAndUserId(courseId, userId);
        if (courseInvitation.isPresent()) {
            courseInvitationRepository.delete(courseInvitation.get());
        } else {
            return ResponseEntity.badRequest().body("No invitation found for this course and user");
        }

        return ResponseEntity.ok("Invitation rejected successfully");
    }

    /**
     * Retrieves a list of courses for which a user has been invited.
     *
     * @param userId the ID of the user
     * @return a response entity containing the list of courses
     */
    @GetMapping("/getInvitationCourses")
    @Transactional
    public ResponseEntity<List<Course>> getInvitationCourses(@RequestParam String userId) {
        List<Long> courseIds = courseInvitationRepository.findCourseIdByUserId(userId);
        if (courseIds.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<Course> courses = courseRepository.findAllById(courseIds);
        return ResponseEntity.ok(courses);
    }
}
