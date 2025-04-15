package sustech.cs304.AIDE.Elements;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;
import sustech.cs304.AIDE.Elements.Assignment;
import sustech.cs304.AIDE.Elements.Course;
import sustech.cs304.AIDE.Elements.Resource;
import sustech.cs304.AIDE.Elements.Submission;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequestMapping("/assignment")
public class AssignmentCenterController {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final ResourceRepository resourceRepository;
    private final SubmissionRepository submissionRepository;

    public AssignmentCenterController(AssignmentRepository assignmentRepository, CourseRepository courseRepository, ResourceRepository resourceRepository, SubmissionRepository submissionRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
        this.resourceRepository = resourceRepository;
        this.submissionRepository = submissionRepository;
    }
    @PostMapping(value = "/createAssignment", produces = "application/json")
    public ResponseEntity<SetResponse> createAssignment(@RequestParam String courseId, @RequestParam String assignmentName, @RequestParam String deadline, @RequestParam String userId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (!courseOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String adminId = courseOptional.get().getAdminId();
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        Assignment assignment = new Assignment(assignmentName, LocalDateTime.parse(deadline), courseId);
        assignmentRepository.save(assignment);
        return ResponseEntity.ok(new SetResponse(true));
    }

    @PostMapping(value = "/uploadAttachment", produces = "application/json")
    public ResponseEntity<SetResponse> uploadAttachment(@RequestParam String assignmentId, @RequestParam("file") MultipartFile file, @RequestParam String userId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(Long.parseLong(assignmentId));
        if (!assignmentOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = assignmentOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        String userHome = System.getProperty("user.home");
        String originalFilename = file.getOriginalFilename();
        String savePath = Paths.get(userHome, "Documents", "Save", userId, assignmentId, "attachment",originalFilename).toString();
    
        File directory = new File(Paths.get(userHome, "Documents", "Save", userId, assignmentId, "attachment").toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            file.transferTo(new File(savePath));
        } catch (IOException e) {
            return ResponseEntity.ok(new SetResponse(false));
        } 
        Resource resource = new Resource(savePath, originalFilename, assignmentId);
        resourceRepository.save(resource); 
        return ResponseEntity.ok(new SetResponse(true));
    }

    @PostMapping(value = "/changeAssignmentName", produces = "application/json")
    public ResponseEntity<SetResponse> changeAssignmentName(@RequestParam String assignmentId, @RequestParam String newName, @RequestParam String userId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(Long.parseLong(assignmentId));
        if (!assignmentOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = assignmentOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        Assignment assignment = assignmentOptional.get();
        assignment.setAssignmentName(newName);
        assignmentRepository.save(assignment);
        return ResponseEntity.ok(new SetResponse(true));
    }
    
    @PostMapping(value = "/changeDeadline", produces = "application/json")
    public ResponseEntity<SetResponse> changeDeadline(@RequestParam String assignmentId, @RequestParam String newDeadline, @RequestParam String userId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(Long.parseLong(assignmentId));
        if (!assignmentOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = assignmentOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        Assignment assignment = assignmentOptional.get();
        assignment.setDeadline(LocalDateTime.parse(newDeadline));
        assignmentRepository.save(assignment);
        return ResponseEntity.ok(new SetResponse(true));
    }

    @PostMapping(value = "/closeAssignment", produces = "application/json")
    public ResponseEntity<SetResponse> closeAssignment(@RequestParam String assignmentId, @RequestParam String userId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(Long.parseLong(assignmentId));
        if (!assignmentOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = assignmentOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        Assignment assignment = assignmentOptional.get();
        assignment.setVisible(false);
        assignmentRepository.save(assignment);
        return ResponseEntity.ok(new SetResponse(true));
    }
    
    @PostMapping(value = "/reOpenAssignment", produces = "application/json")
    public ResponseEntity<SetResponse> reOpenAssignment(@RequestParam String assignmentId, @RequestParam String userId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(Long.parseLong(assignmentId));
        if (!assignmentOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = assignmentOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        Assignment assignment = assignmentOptional.get();
        assignment.setVisible(true);
        assignmentRepository.save(assignment);
        return ResponseEntity.ok(new SetResponse(true));
    }

    @PostMapping(value = "/getAssignmentIdList", produces = "application/json")
     public ResponseEntity<List<Long>> getAssignmentIdList(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (!courseOptional.isPresent()){
            System.out.println("no assignment");
            return ResponseEntity.ok(null);
        }
        List<Long> assignmentIdList = assignmentRepository.findAssignmentIdByCourseId(courseId);
        System.out.println("number of assignment: " + assignmentIdList.size());
        return ResponseEntity.ok(assignmentIdList);
    }

    @PostMapping(value = "/getVisibleAssignmentIdList", produces = "application/json")
     public ResponseEntity<List<Long>> getVisibleAssignmentIdList(@RequestParam String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (!courseOptional.isPresent()){
            System.out.println("no assignment");
            return ResponseEntity.ok(null);
        }
        System.out.println("number of visible assignment: " + assignmentRepository.findVisibleAssignmentIdByCourseId(courseId).size());
        return ResponseEntity.ok(assignmentRepository.findVisibleAssignmentIdByCourseId(courseId));
    }

    @PostMapping(value = "/getAssignment", produces = "application/json")
    public ResponseEntity<clientPersonalAssignment> getAssignment(@RequestParam String assignmentId, @RequestParam String userId) {
        System.out.println("assignmentId: " + assignmentId);
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(Long.parseLong(assignmentId));
        if (!assignmentOptional.isPresent()){
            return ResponseEntity.ok(new clientPersonalAssignment(-1L, "", "", "", false));
        }
        System.out.println("assignment is present");
        Assignment assignment = assignmentOptional.get();
        List<Long> submissionIdList = submissionRepository.findSubmissionIdByAssignmentIdAndUserId(Long.parseLong(assignmentId), userId);
        if (submissionIdList.isEmpty()) {
            return ResponseEntity.ok(new clientPersonalAssignment(Long.parseLong(assignmentId), assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible()));
        } else {
            String address = submissionRepository.findAddressById(submissionIdList.get(0));
            return ResponseEntity.ok(new clientPersonalAssignment(Long.parseLong(assignmentId), assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), address));
        }
    }
    @PostMapping(value = "/submitAssignment", produces = "application/json")
    public ResponseEntity<SetResponse> submitAssignment(@RequestParam String assignmentId, @RequestParam String userId, @RequestParam("file") MultipartFile file) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(Long.parseLong(assignmentId));
        if (!assignmentOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String home = System.getProperty("user.home");
        String originalFilename = file.getOriginalFilename();
        String savePath = Paths.get(home, "Documents", "Save", userId, assignmentId, "submit",originalFilename).toString();
        File directory = new File(Paths.get(home, "Documents", "Save", userId, assignmentId, "submit").toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            file.transferTo(new File(savePath));
        } catch (IOException e) {
            return ResponseEntity.ok(new SetResponse(false));
        } 
        List<Long> submissionIdList = submissionRepository.findSubmissionIdByAssignmentIdAndUserId(Long.parseLong(assignmentId), userId);
        if (submissionIdList.isEmpty()) {
            Submission submission = new Submission(Long.parseLong(assignmentId), userId, savePath);
            submissionRepository.save(submission);
        } else {
            submissionRepository.updateAddressById(submissionIdList.get(0), savePath);
        }
        return ResponseEntity.ok(new SetResponse(true));
    }
}
class clientPersonalAssignment {
    private Long id;
    private String assignmentName;
    private String deadline;
    private String courseId;
    private boolean visible;
    private boolean whetherSubmitted;
    private String address;
    clientPersonalAssignment(Long id, String assignmentName, String deadline, String courseId, boolean visible, String address) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.courseId = courseId;
        this.visible = visible;
        this.whetherSubmitted = true;
        this.address = address;
    }
    clientPersonalAssignment(Long id, String assignmentName, String deadline, String courseId, boolean visible) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.courseId = courseId;
        this.visible = visible;
        this.whetherSubmitted = false;
        this.address = null;
    }
    public Long getId() {
        return id;
    }
    public String getAssignmentName() {
        return assignmentName;
    }
    public String getDeadline() {
        return deadline;
    }
    public String getCourseId() {
        return courseId;
    }
    public boolean getVisible() {
        return visible;
    }
    public boolean getWhetherSubmitted() {
        return whetherSubmitted;
    }
    public String getAddress() {
        return address;
    }
}
