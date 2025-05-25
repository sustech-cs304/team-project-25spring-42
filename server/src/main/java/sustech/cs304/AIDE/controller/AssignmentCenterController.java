package sustech.cs304.AIDE.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

import sustech.cs304.AIDE.model.Assignment;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.model.Resource;
import sustech.cs304.AIDE.model.Submission;
import sustech.cs304.AIDE.repository.AssignmentRepository;
import sustech.cs304.AIDE.repository.CourseRepository;
import sustech.cs304.AIDE.repository.ResourceRepository;
import sustech.cs304.AIDE.repository.SubmissionRepository;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Controller for managing assignments in the assignment center.
 * 
 * This controller handles requests related to creating, uploading, and managing assignments.
 */
@RestController
@RequestMapping("/assignment")
public class AssignmentCenterController {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final ResourceRepository resourceRepository;
    private final SubmissionRepository submissionRepository;
    
    /**
     * Constructor for AssignmentCenterController.
     *
     * @param assignmentRepository the assignment repository
     * @param courseRepository the course repository
     * @param resourceRepository the resource repository
     * @param submissionRepository the submission repository
     */
    public AssignmentCenterController(AssignmentRepository assignmentRepository, CourseRepository courseRepository, ResourceRepository resourceRepository, SubmissionRepository submissionRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
        this.resourceRepository = resourceRepository;
        this.submissionRepository = submissionRepository;
    }

    /**
     * Creates a new assignment.
     *  
     * @param courseId the ID of the course
     * @param assignmentName the name of the assignment
     * @param deadline the deadline for the assignment
     * @param userId the ID of the user creating the assignment
     * @return the ID of the created assignment
     */
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

    /**
     * Uploads an attachment for an assignment.
     * 
     * @param assignmentId the ID of the assignment
     * @param file the file to be uploaded
     * @param userId the ID of the user uploading the file
     * @return a response indicating success or failure
     */
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

    /**
     * Retrieves the resource address by resource ID.
     * 
     * @param resourceId the ID of the resource
     * @return the address of the resource
     */
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

    /**
     * Retrieves the attachment ID by assignment ID.
     * 
     * @param assignmentId the ID of the assignment
     * @return a list of attachment IDs
     */
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

    /**
     * Retrieves the attachment resource by ID.
     * 
     * @param resourceId the ID of the resource
     * @return the attachment resource
     */
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

    /**
     * Changes the name of an assignment.
     *
     * @param assignmentId the ID of the assignment
     * @param newName the new name for the assignment
     * @param userId the ID of the user making the change
     * @return a response indicating success or failure
     */
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
    
    /**
     * Changes the deadline of an assignment.
     *
     * @param assignmentId the ID of the assignment
     * @param newDeadline the new deadline for the assignment
     * @param userId the ID of the user making the change
     * @return a response indicating success or failure
     */
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

    /**
     * Closes an assignment.
     *
     * @param assignmentId the ID of the assignment
     * @param userId the ID of the user making the change
     * @return a response indicating success or failure
     */
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
    
    /**
     * Reopens an assignment.
     *
     * @param assignmentId the ID of the assignment
     * @param userId the ID of the user making the change
     * @return a response indicating success or failure
     */
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

    /**
     * Get the list of assignment IDs for a course.
     * 
     * @param courseId the ID of the course
     * @return a list of assignment IDs
     */
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

    /**
     * Get the list of visible assignment IDs for a course.
     * 
     * @param courseId the ID of the course
     * @return a list of visible assignment IDs
     */
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

    /**
     * Get the assignment by ID and user ID.
     *
     * @param assignmentId the ID of the assignment
     * @param userId the ID of the user
     * @return the assignment details
     */
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

    /**
     * Get all assignments for a course and user.
     *
     * @param courseId the ID of the course
     * @param userId the ID of the user
     * @return a list of assignments
     */
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

    /**
     * Submits an assignment.
     *
     * @param assignmentId the ID of the assignment
     * @param userId the ID of the user submitting the assignment
     * @param file the file to be submitted
     * @return a response indicating success or failure
     */
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

    /**
     * Retrieves the list of submissions for an assignment.
     *
     * @param assignmentId the ID of the assignment
     * @return a list of submissions
     */
    @PostMapping(value = "/getSubmissionList", produces = "application/json")
    public ResponseEntity<List<Submission>> getSubmissionList(@RequestParam Long assignmentId) {
        List<Submission> submissionList = submissionRepository.findByAssignmentId(assignmentId);
        if (submissionList.isEmpty()) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(submissionList);
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

    /**
     * Constructor for clientPersonalAssignment.
     *
     * @param id the ID of the assignment
     * @param assignmentName the name of the assignment
     * @param deadline the deadline for the assignment
     * @param courseId the ID of the course
     * @param visible whether the assignment is visible
     * @param address the address of the assignment
     */
    clientPersonalAssignment(Long id, String assignmentName, String deadline, String courseId, boolean visible, String address) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.courseId = courseId;
        this.visible = visible;
        this.whetherSubmitted = true;
        this.address = address;
    }

    /**
     * Constructor for clientPersonalAssignment without address.
     *
     * @param id the ID of the assignment
     * @param assignmentName the name of the assignment
     * @param deadline the deadline for the assignment
     * @param courseId the ID of the course
     * @param visible whether the assignment is visible
     */
    clientPersonalAssignment(Long id, String assignmentName, String deadline, String courseId, boolean visible) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.courseId = courseId;
        this.visible = visible;
        this.whetherSubmitted = false;
        this.address = null;
    }

    /**
     * Getters for the class attributes.
     */
    public Long getId() {
        return id;
    }

    /**
     * Getters for the class attributes.
     */
    public String getAssignmentName() {
        return assignmentName;
    }

    /**
     * Getters for the class attributes.
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Getters for the class attributes.
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Getters for the class attributes.
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Getters for the class attributes.
     */
    public boolean getWhetherSubmitted() {
        return whetherSubmitted;
    }

    /**
     * Getters for the class attributes.
     */
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

    /**
     * Constructor for clientPersonalAssignmentTwo.
     *
     * @param id the ID of the assignment
     * @param assignmentName the name of the assignment
     * @param deadline the deadline for the assignment
     * @param courseId the ID of the course
     * @param visible whether the assignment is visible
     * @param address the address of the assignment
     * @param attachmentName the name of the attachment
     * @param attachmentaddress the address of the attachment
     */
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
    
    /**
     * Get the ID of the assignment.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Get the name of the assignment.
     */
    public String getAssignmentName() {
        return assignmentName;
    }

    /**
     * Get the deadline of the assignment.
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Get the course ID of the assignment.
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Get the visibility of the assignment.
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Get whether the assignment has been submitted.
     */
    public boolean getWhetherSubmitted() {
        return whetherSubmitted;
    }

    /**
     * Get the address of the assignment.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get the name of the attachment.
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * Get the address of the attachment.
     */
    public String getAttachmentaddress() {
        return attachmentaddress;
    }
}
