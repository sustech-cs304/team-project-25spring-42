package sustech.cs304.AIDE.Elements;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;


@RestController
@RequestMapping("/course")
public class CourseRelatedController {

    private final CourseRepository courseRepository;

    public CourseRelatedController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping(value = "/allInfo", produces = "application/json")
    @Transactional
    public ResponseEntity<ClientCourse> getAllUserInfo(@RequestParam String courseId) {
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
    public ResponseEntity<UserResponse> getAdminId(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println("Course found: " + course.getCourseName());
            return ResponseEntity.ok(new UserResponse(course.getAdminId()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/getOpenTime", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getOpenTime(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println("Course found: " + course.getCourseName());
            return ResponseEntity.ok(new UserResponse(course.getOpenTime().toString()));
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
