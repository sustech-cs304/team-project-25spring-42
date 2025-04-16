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
import java.util.ArrayList;

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
    public ResponseEntity<Long> createAssignment(@RequestParam String courseId, @RequestParam String assignmentName, @RequestParam String deadline, @RequestParam String userId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (!courseOptional.isPresent()){
            return ResponseEntity.ok(-1L);
        }
        String adminId = courseOptional.get().getAdminId();
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(-1L);
        }
        Assignment assignment = new Assignment(assignmentName, LocalDateTime.parse(deadline), courseId);
        assignmentRepository.save(assignment);
        Long assignmentId = assignmentRepository.findAssignmentIdByAssignmentNameAndDeadline(assignmentName, LocalDateTime.parse(deadline));
        System.out.println("assignmentId: " + assignmentId);
        return ResponseEntity.ok(assignmentId);
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

    @PostMapping(value = "/getResourceAddress", produces = "application/json")
    public ResponseEntity<String> getResourceAddress(@RequestParam String resourceId) {
        String address = resourceRepository.findAddressById(Long.parseLong(resourceId));
        if (address == null) {
            System.out.println("no resource");
            return ResponseEntity.ok(null);
        }
        System.out.println("resource address: " + address);
        return ResponseEntity.ok(address);
    }

    @PostMapping(value = "/getAttachmentId", produces = "application/json")
    public ResponseEntity<List<Long>> getAttachmentId(@RequestParam String assignmentId) {
        List<Long> attachmentIdList = resourceRepository.findIdByAssignmentId(assignmentId);
        if (attachmentIdList.isEmpty()) {
            System.out.println("no attachment");
            return ResponseEntity.ok(null);
        }
        System.out.println("number of attachment: " + attachmentIdList.size());
        return ResponseEntity.ok(attachmentIdList);
    }

    @PostMapping(value = "/getAttachmentResourceById", produces = "application/json")
    public ResponseEntity<ClientResource> getAttachmentResourceById(@RequestParam String resourceId) {
        Optional<Resource> resourceOptional = resourceRepository.findById(Long.parseLong(resourceId));
        if (!resourceOptional.isPresent()){
            return ResponseEntity.ok(null);
        }
        Long id = resourceOptional.get().getId();
        String address = resourceOptional.get().getAddress();
        String resourceName = resourceOptional.get().getResourceName();
        String type = resourceOptional.get().getType();
        LocalDateTime uploadTime = resourceOptional.get().getUploadTime();
        Long groupId = resourceOptional.get().getGroupId();
        String size = resourceOptional.get().getSize();
        boolean visible = resourceOptional.get().getVisible();
        ClientResource clientResource = new ClientResource(id, address, resourceName, type, uploadTime, groupId, size, visible);
        System.out.println("resource address: " + address);
        return ResponseEntity.ok(clientResource);
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
        List<Long> assignmentIdList = assignmentRepository.findVisibleAssignmentIdByCourseId(courseId);
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
            return ResponseEntity.ok(null);
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

    @PostMapping(value = "/getAllAssignment", produces = "application/json")
    public ResponseEntity<List<clientPersonalAssignmentTwo>> getAllAssignment(@RequestParam String courseId, @RequestParam String userId) {
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        boolean whetherAdmin = false;
        if (adminId.equals(userId)) {
            whetherAdmin = true;
        }
        List<clientPersonalAssignmentTwo> assignmentList = new ArrayList<>();
        if (!whetherAdmin) {
            List<Long> assignmentIdList = assignmentRepository.findVisibleAssignmentIdByCourseId(courseId);
            if (assignmentIdList.isEmpty()){
                return ResponseEntity.ok(null);
            }
            for (Long assignmentId : assignmentIdList){
                Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
                if (!assignmentOptional.isPresent()){
                    continue;
                }
                Assignment assignment = assignmentOptional.get();
                List<Long> submissionIdList = submissionRepository.findSubmissionIdByAssignmentIdAndUserId(assignmentId, userId);
                List<Long> attachmentIdList = resourceRepository.findIdByAssignmentId(assignmentId.toString());
                if (submissionIdList.isEmpty()) {
                    if (attachmentIdList.isEmpty()){
                        assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(),null, null, null)); 
                    } else {
                        Long attachmentId = attachmentIdList.get(0);
                        Optional<Resource> resourceOptional = resourceRepository.findById(attachmentId);
                        if (resourceOptional.isPresent()){
                            String attachmentName = resourceOptional.get().getResourceName();
                            String attachmentaddress = resourceOptional.get().getAddress();
                            System.out.println("attachmentName: " + attachmentName);
                            assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), null, attachmentName, attachmentaddress));
                        } else {
                            assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), null, null, null));
                        }
                    }
                } else {
                    String address = submissionRepository.findAddressById(submissionIdList.get(0));
                    if (attachmentIdList.isEmpty()){
                        assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), address, null, null)); 
                    } else {
                        Long attachmentId = attachmentIdList.get(0);
                        Optional<Resource> resourceOptional = resourceRepository.findById(attachmentId);
                        if (resourceOptional.isPresent()){
                            String attachmentName = resourceOptional.get().getResourceName();
                            String attachmentaddress = resourceOptional.get().getAddress();
                            assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), address, attachmentName, attachmentaddress));
                        } else {
                            assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), address, null, null));
                        }
                    }
                }
            }
        } else {
            List<Long> assignmentIdList = assignmentRepository.findAssignmentIdByCourseId(courseId);
            if (assignmentIdList.isEmpty()){
                return ResponseEntity.ok(null);
            }
            for (Long assignmentId : assignmentIdList){
                Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
                if (!assignmentOptional.isPresent()){
                    continue;
                }
                Assignment assignment = assignmentOptional.get();
                List<Long> submissionIdList = submissionRepository.findSubmissionIdByAssignmentIdAndUserId(assignmentId, userId);
                List<Long> attachmentIdList = resourceRepository.findIdByAssignmentId(assignmentId.toString());
                if (submissionIdList.isEmpty()) {
                    if (attachmentIdList.isEmpty()){
                        assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(),null, null, null)); 
                    } else {
                        Long attachmentId = attachmentIdList.get(0);
                        Optional<Resource> resourceOptional = resourceRepository.findById(attachmentId);
                        if (resourceOptional.isPresent()){
                            String attachmentName = resourceOptional.get().getResourceName();
                            String attachmentaddress = resourceOptional.get().getAddress();
                            System.out.println("attachmentName: " + attachmentName);
                            assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), null, attachmentName, attachmentaddress));
                        } else {
                            assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), null, null, null));
                        }
                    }
                } else {
                    String address = submissionRepository.findAddressById(submissionIdList.get(0));
                    if (attachmentIdList.isEmpty()){
                        assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), address, null, null)); 
                    } else {
                        Long attachmentId = attachmentIdList.get(0);
                        Optional<Resource> resourceOptional = resourceRepository.findById(attachmentId);
                        if (resourceOptional.isPresent()){
                            String attachmentName = resourceOptional.get().getResourceName();
                            String attachmentaddress = resourceOptional.get().getAddress();
                            assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), address, attachmentName, attachmentaddress));
                        } else {
                            assignmentList.add(new clientPersonalAssignmentTwo(assignmentId, assignment.getAssignmentName(), assignment.getDeadline().toString(), assignment.getCourseId(), assignment.getVisible(), address, null, null));
                        }
                    }
                }
            }

        }
        System.out.println("number of assignment: " + assignmentList.size());
        return ResponseEntity.ok(assignmentList);
    }

    @PostMapping(value = "/submitAssignment", produces = "application/json")
    public ResponseEntity<Boolean> submitAssignment(@RequestParam String assignmentId, @RequestParam String userId, @RequestParam("file") MultipartFile file) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(Long.parseLong(assignmentId));
        if (!assignmentOptional.isPresent()){
            return ResponseEntity.ok(false);
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
            return ResponseEntity.ok(false);
        } 
        List<Long> submissionIdList = submissionRepository.findSubmissionIdByAssignmentIdAndUserId(Long.parseLong(assignmentId), userId);
        if (submissionIdList.isEmpty()) {
            Submission submission = new Submission(Long.parseLong(assignmentId), userId, savePath);
            submissionRepository.save(submission);
        } else {
            submissionRepository.updateAddressById(submissionIdList.get(0), savePath);
        }
        return ResponseEntity.ok(true);
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
class clientPersonalAssignmentTwo {
    private Long id;
    private String assignmentName;
    private String deadline;
    private String courseId;
    private boolean visible;
    private boolean whetherSubmitted;
    private String attachmentName;
    private String attachmentaddress;
    private String address;
    clientPersonalAssignmentTwo(Long id, String assignmentName, String deadline, String courseId, boolean visible, String address, String attachmentName, String attachmentaddress) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.courseId = courseId;
        this.visible = visible;
        if (address == null) {
            this.whetherSubmitted = false;
        } else {
            this.whetherSubmitted = true;
        }
        this.address = address;
        this.attachmentName = attachmentName;
        this.attachmentaddress = attachmentaddress;
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
    public String getAttachmentName() {
        return attachmentName;
    }
    public String getAttachmentaddress() {
        return attachmentaddress;
    }
}
