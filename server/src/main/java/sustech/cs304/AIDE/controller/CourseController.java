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


    @PostMapping(value = "/getCourseIdList", produces = "application/json")
    public ResponseEntity<List<Long>> getCourseIdList(@RequestParam String userId) {
        List<Long> courseIdList = enrollmentRepository.findCourseIdByUserId(userId);
        System.out.println("Course ID list retrieved for user: " + userId);
        return ResponseEntity.ok(courseIdList);
    }

    @GetMapping(value = "/getCourseList", produces = "application/json")
    @Transactional
    public ResponseEntity<List<ClientCourse>> getCourseList(@RequestParam String userId) {
        List<Long> courseIdList = enrollmentRepository.findCourseIdByUserId(userId);
        List<ClientCourse> clientCourses = new ArrayList<>();
        for (Long courseId : courseIdList) {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                ClientCourse clientCourse = new ClientCourse(course.getId(), course.getCourseName(), course.getAdminId(), course.getOpenTime(), course.getCloseTime(), course.getOpening());
                clientCourses.add(clientCourse);
            }
        }
        return ResponseEntity.ok(clientCourses);
    }
}
class ClientCourse {
    private Long id;
    private String courseName;
    private String adminId;
    private String openTime;
    private String closeTime;
    private boolean opening;
    public ClientCourse(Long id, String courseName, String adminId, LocalDateTime openTime, LocalDateTime closeTime, boolean opening) {
        this.id = id;
        this.courseName = courseName;
        this.adminId = adminId;
        this.openTime = openTime.toString();
        this.closeTime = closeTime != null ? closeTime.toString() : null;
        this.opening = opening;
    }
    public Long getId() {
        return id;
    }
    public String getCourseName() {
        return courseName;
    }
    public String getAdminId() {
        return adminId;
    }
    public String getOpenTime() {
        return openTime;
    }
    public String getCloseTime() {
        return closeTime;
    }
    public boolean isOpening() {
        return opening;
    }
}    

class UserResponse {
    private String content;
    UserResponse(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
}
class SetResponse {
    private boolean result;
    SetResponse(boolean result) {
        this.result = result;
    }
    public boolean getResult() {
        return result;
    }
}
