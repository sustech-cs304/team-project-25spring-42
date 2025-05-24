package sustech.cs304.AIDE.controller;

import org.springframework.web.bind.annotation.*;

import sustech.cs304.AIDE.repository.EnrollmentRepository;
import sustech.cs304.AIDE.repository.ResourceRepository;
import sustech.cs304.AIDE.repository.SubmissionRepository;
import sustech.cs304.AIDE.repository.UserRepository;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.model.Enrollment;
import sustech.cs304.AIDE.model.User;
import sustech.cs304.AIDE.repository.AnnounceRepository;
import sustech.cs304.AIDE.repository.AssignmentRepository;
import sustech.cs304.AIDE.repository.CourseInvitationRepository;
import sustech.cs304.AIDE.repository.CourseRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller for managing courses.
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AssignmentRepository assignmentRepository;
    private final ResourceRepository resourceRepository;
    private final AnnounceRepository announceRepository;
    private final SubmissionRepository submissionRepository;
    private final CourseInvitationRepository courseInvitationRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for CourseController.
     *
     * @param courseRepository           The repository for courses.
     * @param enrollmentRepository       The repository for enrollments.
     * @param assignmentRepository       The repository for assignments.
     * @param resourceRepository         The repository for resources.
     * @param announceRepository         The repository for announcements.
     * @param submissionRepository       The repository for submissions.
     * @param courseInvitationRepository The repository for course invitations.
     * @param userRepository             The repository for users.
     */
    public CourseController(
        CourseRepository courseRepository, 
        EnrollmentRepository enrollmentRepository, 
        AssignmentRepository assignmentRepository,
        ResourceRepository resourceRepository,
        AnnounceRepository announceRepository,
        SubmissionRepository submissionRepository,
        CourseInvitationRepository courseInvitationRepository,
        UserRepository userRepository
    ) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.assignmentRepository = assignmentRepository;
        this.resourceRepository = resourceRepository;
        this.announceRepository = announceRepository;
        this.submissionRepository = submissionRepository;
        this.courseInvitationRepository = courseInvitationRepository;
        this.userRepository = userRepository;
    }

    /**
     * Deletes a course by its ID.
     *
     * @param courseId The ID of the course to be deleted.
     * @param userId   The ID of the user requesting the deletion.
     * @return A response indicating whether the deletion was successful.
     */
    @GetMapping(value = "/deleteCourse", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> deleteCourse(@RequestParam String courseId, @RequestParam String userId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            if (course.getAdminId().equals(userId)) {
                courseRepository.delete(course);
                courseInvitationRepository.deleteByCourseId(Long.parseLong(courseId));
                enrollmentRepository.deleteByCourseId(Long.parseLong(courseId));
                resourceRepository.deleteByCourseId(Long.parseLong(courseId));
                announceRepository.deleteByCourseId(Long.parseLong(courseId));
                List<Long> assignmentIds = assignmentRepository.findAssignmentIdByCourseId(courseId);
                for (Long assignmentId : assignmentIds) {
                    submissionRepository.deleteByAssignmentId(assignmentId);
                }
                assignmentRepository.deleteByCourseId(Long.parseLong(courseId));
                return ResponseEntity.ok(new SetResponse(true));
            } else {
                return ResponseEntity.status(403).body(new SetResponse(false));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param courseId The ID of the course to be retrieved.
     * @return The course details if found, otherwise a not found response.
     */
    @GetMapping(value = "/getCourseById", produces = "application/json")
    @Transactional
    public ResponseEntity<ClientCourse> getCourseById(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        System.out.println("Course ID: " + courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println("Course found: " + course.getCourseName());
            ClientCourse clientCourse = new ClientCourse(course.getId(), course.getCourseName(), course.getAdminId(), course.getOpenTime(), course.getCloseTime(), course.getOpening());
            return ResponseEntity.ok(clientCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the course name by its ID.
     *
     * @param courseId The ID of the course to be retrieved.
     * @return The course name if found, otherwise a not found response.
     */
    @GetMapping(value = "/getCourseName", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getCourseName(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println("Course found: " + course.getCourseName());
            return ResponseEntity.ok(new UserResponse(course.getCourseName()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Retrieves the admin ID of a course by its ID.
     *
     * @param courseId The ID of the course to be retrieved.
     * @return The admin ID if found, otherwise a not found response.
     */
    @GetMapping(value = "/getAdminId", produces = "application/json")
    @Transactional
    public ResponseEntity<String> getAdminId(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println("Course found: " + course.getCourseName());
            return ResponseEntity.ok(course.getAdminId());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the open time of a course by its ID.
     *
     * @param courseId The ID of the course to be retrieved.
     * @return The open time if found, otherwise a not found response.
     */
    @GetMapping(value = "/getOpenTime", produces = "application/json")
    @Transactional
    public ResponseEntity<String> getOpenTime(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println("Course found: " + course.getCourseName());
            return ResponseEntity.ok(course.getOpenTime().toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the close time of a course by its ID.
     *
     * @param courseId The ID of the course to be retrieved.
     * @return The close time if found, otherwise a not found response.
     */
    @GetMapping(value = "/getCloseTime", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getCloseTime(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println("Course found: " + course.getCourseName());
            return ResponseEntity.ok(new UserResponse(course.getCloseTime() != null ? course.getCloseTime().toString() : null));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the opening status of a course by its ID.
     *
     * @param courseId The ID of the course to be retrieved.
     * @return The opening status if found, otherwise a not found response.
     */
    @GetMapping(value = "/getOpening", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getOpening(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println("Course found: " + course.getCourseName());
            return ResponseEntity.ok(new UserResponse(String.valueOf(course.getOpening())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new course.
     *
     * @param courseName The name of the course to be created.
     * @param adminId    The ID of the admin creating the course.
     * @return A response indicating whether the creation was successful.
     */
    @GetMapping(value = "/createCourse", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> createCourse(@RequestParam String courseName, @RequestParam String adminId) {
        Course course = new Course(courseName, adminId);
        courseRepository.save(course);
        System.out.println("Course created: " + course.getCourseName());
        Enrollment enrollment = new Enrollment(course.getId(), adminId);
        enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(new SetResponse(true));
    }

    /**
     * Sets the course name.
     *
     * @param courseId   The ID of the course to be updated.
     * @param courseName The new name of the course.
     * @param userId     The ID of the user requesting the update.
     * @return A response indicating whether the update was successful.
     */
    @GetMapping(value = "/setCourseName", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> setCourseName(@RequestParam String courseId, @RequestParam String courseName, @RequestParam String userId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            if (course.getAdminId().equals(userId)) {
                course.setCourseName(courseName);
                courseRepository.save(course);
                System.out.println("Course name updated: " + course.getCourseName());
                return ResponseEntity.ok(new SetResponse(true));
            } else {
                return ResponseEntity.status(403).body(new SetResponse(false));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Reopens a course.
     * @param courseId The ID of the course to be reopened.
     * @param userId   The ID of the user requesting the reopening.
     * @return A response indicating whether the reopening was successful.
     */
    @GetMapping(value = "/reOpenCourse", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> reOpenCourse(@RequestParam String courseId, @RequestParam String userId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            if (course.getAdminId().equals(userId)) {
                course.reOpenCourse();
                courseRepository.save(course);
                System.out.println("Course reopened: " + course.getCourseName());
                return ResponseEntity.ok(new SetResponse(true));
            } else {
                return ResponseEntity.status(403).body(new SetResponse(false));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Closes a course.
     *
     * @param courseId The ID of the course to be closed.
     * @param userId   The ID of the user requesting the closure.
     * @return A response indicating whether the closure was successful.
     */
    @GetMapping(value = "/closeCourse", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> closeCourse(@RequestParam String courseId, @RequestParam String userId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            if (course.getAdminId().equals(userId)) {
                course.closeCourse();
                courseRepository.save(course);
                System.out.println("Course closed: " + course.getCourseName());
                return ResponseEntity.ok(new SetResponse(true));
            } else {
                return ResponseEntity.status(403).body(new SetResponse(false));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Sets the admin ID of a course.
     *
     * @param courseId   The ID of the course to be updated.
     * @param newAdminId The new admin ID.
     * @param userId     The ID of the user requesting the update.
     * @return A response indicating whether the update was successful.
     */
    @GetMapping(value = "/setAdminId", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> setAdminId(@RequestParam String courseId, @RequestParam String newAdminId, @RequestParam String userId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            if (course.getAdminId().equals(userId)) {
                course.setAdminId(newAdminId);
                courseRepository.save(course);
                System.out.println("Course admin ID updated: " + course.getAdminId());
                return ResponseEntity.ok(new SetResponse(true));
            } else {
                return ResponseEntity.status(403).body(new SetResponse(false));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a list of users enrolled in a course by its ID.
     *
     * @param courseId The ID of the course.
     * @return A list of users enrolled in the course.
     */
    @GetMapping(value = "/getUserByCourseId", produces = "application/json")
    @Transactional
    public ResponseEntity<List<User>> getUserByCourseId(@RequestParam String courseId) {
        List<String> userIdList = enrollmentRepository.findUserIdByCourseId(Long.parseLong(courseId));
        List<User> userList = new ArrayList<>();
        for (String userId : userIdList) {
            Optional<User> userOptional = userRepository.findByPlatformId(userId);
            if (userOptional.isPresent()) {
                userList.add(userOptional.get());
            }
        }
        return ResponseEntity.ok(userList);
    }

    /**
     * Retrieves a list of course IDs for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of course IDs.
     */
    @PostMapping(value = "/getCourseIdList", produces = "application/json")
    public ResponseEntity<List<Long>> getCourseIdList(@RequestParam String userId) {
        List<Long> courseIdList = enrollmentRepository.findCourseIdByUserId(userId);
        System.out.println("Course ID list retrieved for user: " + userId);
        return ResponseEntity.ok(courseIdList);
    }

    /**
     * Retrieves a list of courses for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of courses.
     */
    @PostMapping(value = "/getCourseList", produces = "application/json")
    public ResponseEntity<List<ClientCourse>> getCourseList(@RequestParam String userId) {
        List<Long> courseIdList = enrollmentRepository.findCourseIdByUserId(userId);
        List<ClientCourse> clientCourseList = new ArrayList<>();
        for (Long courseId : courseIdList) {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                ClientCourse clientCourse = new ClientCourse(course.getId(), course.getCourseName(), course.getAdminId(), course.getOpenTime(), course.getCloseTime(), course.getOpening());
                clientCourseList.add(clientCourse);
            }
        }
        return ResponseEntity.ok(clientCourseList);
    } 

}
class ClientCourse {
    private Long id;
    private String courseName;
    private String adminId;
    private String openTime;
    private String closeTime;
    private boolean opening;

    /**
     * Constructor for ClientCourse.
     *
     * @param id        The ID of the course.
     * @param courseName The name of the course.
     * @param adminId   The ID of the admin.
     * @param openTime  The open time of the course.
     * @param closeTime The close time of the course.
     * @param opening   The opening status of the course.
     */
    public ClientCourse(Long id, String courseName, String adminId, LocalDateTime openTime, LocalDateTime closeTime, boolean opening) {
        this.id = id;
        this.courseName = courseName;
        this.adminId = adminId;
        this.openTime = openTime.toString();
        this.closeTime = closeTime != null ? closeTime.toString() : null;
        this.opening = opening;
    }

    /**
     * Get the id of the course.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the course name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Get the admin ID.
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * Get the open time of the course.
     */
    public String getOpenTime() {
        return openTime;
    }

    /**
     * Get the close time of the course.
     */
    public String getCloseTime() {
        return closeTime;
    }

    /**
     * Get the opening status of the course.
     */
    public boolean isOpening() {
        return opening;
    }
}    

class UserResponse {
    private String content;

    /**
     * Constructor for UserResponse.
     *
     * @param content The content of the response.
     */
    UserResponse(String content) {
        this.content = content;
    }

    /**
     * Get the content of the response.
     */
    public String getContent() {
        return content;
    }
}
class SetResponse {
    private boolean result;

    /**
     * Constructor for SetResponse.
     *
     * @param result The result of the operation.
     */
    SetResponse(boolean result) {
        this.result = result;
    }

    /**
     * Get the result of the operation.
     */
    public boolean getResult() {
        return result;
    }
}
