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
@RequestMapping("/resource")
public class ResourceController {

    private final CourseRepository courseRepository;
    private final ResourceRepository resourceRepository;

    public ResourceController(CourseRepository courseRepository, ResourceRepository resourceRepository) {
        this.courseRepository = courseRepository;
        this.resourceRepository = resourceRepository;
    }
    
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
        Resource resource = new Resource(savePath, courseId, originalFilename, file.getContentType(), groupId, String.valueOf(file.getSize()));
        resourceRepository.save(resource); 
        return ResponseEntity.ok(new SetResponse(true));
    }

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

    @PostMapping(value = "/getResourceIdList", produces = "application/json")
    public ResponseEntity<longList> getResourceIdList(@RequestParam String courseId, @RequestParam String userId) {
        List<Long> resourceIds = resourceRepository.findResourceIdByCourseId(courseId);
        if (resourceIds.isEmpty()) {
            return ResponseEntity.ok(new longList(resourceIds));
        }
        return ResponseEntity.ok(new longList(resourceIds));
    }

    @PostMapping(value = "/getVisibleResourceIdList", produces = "application/json")
    public ResponseEntity<longList> getVisibleResourceIdList(@RequestParam String courseId, @RequestParam String userId) {
        List<Long> resourceIds = resourceRepository.findVisibleResourceIdByCourseId(courseId);
        if (resourceIds.isEmpty()) {
            return ResponseEntity.ok(new longList(resourceIds));
        }
        return ResponseEntity.ok(new longList(resourceIds));
    }

    @PostMapping(value = "/getResourceList", produces = "application/json")
    public ResponseEntity<List<ClientResource>> getResourceList(@RequestParam String courseId, @RequestParam String userId) {
        List<Resource> resources = resourceRepository.findByCourseId(courseId);
        if (resources.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<ClientResource> clientResources = resources.stream()
                .map(resource -> new ClientResource(resource.getId(), resource.getAddress(), resource.getResourceName(), resource.getType(), resource.getUpLoadTime(), resource.getGroupId(), resource.getSize(), resource.getVisible()))
                .toList();
        return ResponseEntity.ok(clientResources);
    }

    @PostMapping(value = "/getVisibleResourceList", produces = "application/json")  
    public ResponseEntity<List<ClientResource>> getVisibleResourceList(@RequestParam String courseId, @RequestParam String userId) {
        List<Resource> resources = resourceRepository.findVisibleByCourseId(courseId);
        if (resources.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
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
    private LocalDateTime uploadTime;
    private Long groupId;
    private String size;
    private boolean visible;

    public ClientResource(Long id, String address, String resourceName, String type, LocalDateTime uploadTime, Long groupId, String size, boolean visible) {
        this.id = id;
        this.address = address;
        this.resourceName = resourceName;
        this.type = type;
        this.uploadTime = uploadTime;
        this.groupId = groupId;
        this.size = size;
        this.visible = visible;
    }

    public Long getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getResourceName() {
        return resourceName;
    }
    public String getType() {
        return type;
    }
    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
    public Long getGroupId() {
        return groupId;
    }
    public String getSize() {
        return size;
    }
    public boolean getVisible() {
        return visible;
    }
}
