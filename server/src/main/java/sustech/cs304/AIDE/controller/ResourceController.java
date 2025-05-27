package sustech.cs304.AIDE.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import sustech.cs304.AIDE.repository.CourseRepository;
import sustech.cs304.AIDE.repository.ResourceRepository;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.model.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * Controller for managing resources in a course.
 *
 * This class handles uploading, updating, and retrieving resources associated with a course.
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private final CourseRepository courseRepository;
    private final ResourceRepository resourceRepository;

    /**
     * Constructor for ResourceController.
     *
     * @param courseRepository the repository for courses
     * @param resourceRepository the repository for resources
     */
    public ResourceController(CourseRepository courseRepository, ResourceRepository resourceRepository) {
        this.courseRepository = courseRepository;
        this.resourceRepository = resourceRepository;
    }
    
    /**
     * Uploads a resource file to the server.
     *
     * @param courseId the ID of the course
     * @param file the resource file to upload
     * @param userId the ID of the user uploading the file
     * @param groupId the group ID associated with the resource
     * @return a response entity indicating success or failure
     */
    @PostMapping(value = "/uploadResource", produces = "application/json")
    public ResponseEntity<SetResponse> uploadResource(@RequestParam String courseId, @RequestParam("file") MultipartFile file, @RequestParam String userId, @RequestParam Long groupId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (!courseOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String adminId = courseOptional.get().getAdminId();
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        String userHome = System.getProperty("user.home");
        String originalFilename = file.getOriginalFilename();
        String savePath = Paths.get(userHome, "Documents", "Save", userId, courseId, "resource", originalFilename).toString();
    
        File directory = new File(Paths.get(userHome, "Documents", "Save", userId, courseId, "resource").toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            file.transferTo(new File(savePath));
        } catch (IOException e) {
            return ResponseEntity.ok(new SetResponse(false));
        } 
        String contentType = null;
        try {
            contentType = Files.probeContentType(Paths.get(savePath));
        } catch (IOException e) {
            contentType = null;
        }
        if (contentType == null) {
            contentType = "application/octet-stream"; 
        }
        switch (contentType) {
        case "text/x-java":
            contentType = "java";
            break;
        default:
            break;
    }
        Resource resource = new Resource(savePath, courseId, originalFilename, contentType, groupId, String.valueOf(file.getSize()));
        resourceRepository.save(resource); 
        return ResponseEntity.ok(new SetResponse(true));
    }

    /**
     * Update the resource file.
     *
     * @param resourceId the ID of the resource
     * @param file the new resource file to upload
     * @param userId the ID of the user uploading the file
     * @return a response entity indicating success or failure
     */
    @PostMapping(value = "/updateResource", produces = "application/json")
    public ResponseEntity<SetResponse> updateResource(@RequestParam String resourceId, @RequestParam("file") MultipartFile file, @RequestParam String userId) {
        Optional<Resource> resourceOptional = resourceRepository.findById(Long.parseLong(resourceId));
        if (!resourceOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = resourceOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        String userHome = System.getProperty("user.home");
        String originalFilename = file.getOriginalFilename();
        String savePath = Paths.get(userHome, "Documents", "Save", userId, courseId, "resource", originalFilename).toString();
    
        File directory = new File(Paths.get(userHome, "Documents", "Save", userId, courseId, "resource").toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            file.transferTo(new File(savePath));
        } catch (IOException e) {
            return ResponseEntity.ok(new SetResponse(false));
        } 
        Resource resource = resourceOptional.get();
        resource.setAddress(savePath);
        resource.setResourceName(originalFilename);
        resource.setType(file.getContentType());
        resource.setSize(String.valueOf(file.getSize()));
        resourceRepository.save(resource); 
        return ResponseEntity.ok(new SetResponse(true));
    }

    /**
     * Update the group ID of a resource.
     * 
     * @param resourceId the ID of the resource
     * @param groupId the new group ID
     * @param userId the ID of the user updating the group ID
     * @return a response entity indicating success or failure
     */
    @PostMapping(value = "/updateGroupId", produces = "application/json")
    public ResponseEntity<SetResponse> updateGroupId(@RequestParam String resourceId, @RequestParam Long groupId, @RequestParam String userId) {
        Optional<Resource> resourceOptional = resourceRepository.findById(Long.parseLong(resourceId));
        if (!resourceOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = resourceOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        Resource resource = resourceOptional.get();
        resource.setGroupId(groupId);
        resourceRepository.save(resource); 
        return ResponseEntity.ok(new SetResponse(true));
    }

    /** 
     * Get a list of resource IDs for a specific course.
     *
     * @param courseId the ID of the course
     * @param userId the ID of the user
     * @return a response entity containing the list of resource IDs
     */
    @PostMapping(value = "/getResourceIdList", produces = "application/json")
    public ResponseEntity<longList> getResourceIdList(@RequestParam String courseId, @RequestParam String userId) {
        List<Long> resourceIds = resourceRepository.findResourceIdByCourseId(courseId);
        if (resourceIds.isEmpty()) {
            return ResponseEntity.ok(new longList(resourceIds));
        }
        return ResponseEntity.ok(new longList(resourceIds));
    }

    /**
     * Get a list of visible resource IDs for a specific course.
     *
     * @param courseId the ID of the course
     * @param userId the ID of the user
     * @return a response entity containing the list of visible resource IDs
     */
    @PostMapping(value = "/getVisibleResourceIdList", produces = "application/json")
    public ResponseEntity<longList> getVisibleResourceIdList(@RequestParam String courseId, @RequestParam String userId) {
        List<Long> resourceIds = resourceRepository.findVisibleResourceIdByCourseId(courseId);
        if (resourceIds.isEmpty()) {
            return ResponseEntity.ok(new longList(resourceIds));
        }
        return ResponseEntity.ok(new longList(resourceIds));
    }

    /**
     * Get a list of resources for a specific course.
     *
     * @param courseId the ID of the course
     * @return a response entity containing the list of resources
     */
    @PostMapping(value = "/getResourceList", produces = "application/json")
    public ResponseEntity<List<ClientResource>> getResourceList(@RequestParam String courseId) {
        List<Resource> resources = resourceRepository.findByCourseId(courseId);
        if (resources.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<ClientResource> clientResources = resources.stream()
                .map(resource -> new ClientResource(resource.getId(), resource.getAddress(), resource.getResourceName(), resource.getType(), resource.getUpLoadTime(), resource.getGroupId(), resource.getSize(), resource.getVisible()))
                .toList();
        return ResponseEntity.ok(clientResources);
    }

    /**
     * Get a list of visible resources for a specific course.
     *
     * @param courseId the ID of the course
     * @return a response entity containing the list of visible resources
     */
    @PostMapping(value = "/getVisibleResourceList", produces = "application/json")  
    public ResponseEntity<List<ClientResource>> getVisibleResourceList(@RequestParam String courseId) {
        List<Resource> resources = resourceRepository.findVisibleByCourseId(courseId);
        if (resources.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        System.out.println("Visible resources found: " + resources.size());
        List<ClientResource> clientResources = resources.stream()
                .map(resource -> new ClientResource(resource.getId(), resource.getAddress(), resource.getResourceName(), resource.getType(), resource.getUpLoadTime(), resource.getGroupId(), resource.getSize(), resource.getVisible()))
                .toList();
        return ResponseEntity.ok(clientResources);
    }
}
class ClientResource{
    private Long id;
    private String address;
    private String resourceName;
    private String type;
    private String uploadTime;
    private Long groupId;
    private String size;
    private boolean visible;

    /**
     * Constructor for ClientResource.
     *
     * @param id the ID of the resource
     * @param address the address of the resource
     * @param resourceName the name of the resource
     * @param type the type of the resource
     * @param uploadTime the upload time of the resource
     * @param groupId the group ID associated with the resource
     * @param size the size of the resource
     * @param visible whether the resource is visible or not
     */
    public ClientResource(Long id, String address, String resourceName, String type, LocalDateTime uploadTime, Long groupId, String size, boolean visible) {
        this.id = id;
        this.address = address;
        this.resourceName = resourceName;
        this.type = type;
        if (uploadTime == null) {
            this.uploadTime = null;
        } else {
            this.uploadTime = uploadTime.toString();
        }
        this.groupId = groupId;
        this.size = size;
        this.visible = visible;
    }

    /**
     * get the id of the resource
     */
    public Long getId() {
        return id;
    }

    /**
     * get the address of the resources
     */
    public String getAddress() {
        return address;
    }

    /** 
     * get the name of the resources
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * get the type of the resources
     */
    public String getType() {
        return type;
    }

    /**
     * get the upload time of the resources
     */
    public String getUploadTime() {
        return uploadTime;
    }

    /** 
     * get the group id of the resources
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * get the size of the resources
     */
    public String getSize() {
        return size;
    }
    
    /**
     * get the visibility of the resources
     */
    public boolean getVisible() {
        return visible;
    }
}
