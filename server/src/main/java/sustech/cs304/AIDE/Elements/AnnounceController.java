package sustech.cs304.AIDE.Elements;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;
import sustech.cs304.AIDE.Elements.Course;

@RestController
@RequestMapping("/announce")
public class AnnounceController {

    private final AnnounceRepository announceRepository;
    private final CourseRepository courseRepository;

    public AnnounceController(AnnounceRepository announceRepository, CourseRepository courseRepository) {
        this.announceRepository = announceRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping(value = "/createAnnounce", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> createAnnounce(@RequestParam String courseId, @RequestParam String announceName, @RequestParam String announceContent, @RequestParam String userId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (!courseOptional.isPresent()){
            return ResponseEntity.ok(new SetResponse(false));
        }
        String adminId = courseOptional.get().getAdminId();
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        Announce announce = new Announce(courseId, announceName, announceContent);
        announceRepository.save(announce);
        return ResponseEntity.ok(new SetResponse(true));
    }

    @PostMapping(value = "/getAnnounceIdList", produces = "application/json")
    public ResponseEntity<longList> getAnnounceIdList(@RequestParam String courseId) {
        List<AnnounceProjection> announceList = announceRepository.findProjectByCourseId(courseId);
        if (announceList.isEmpty()) {
            return ResponseEntity.ok(new longList(List.of()));
        }
        List<Long> announceIdList = announceList.stream()
                .map(AnnounceProjection::getId)
                .toList();
        return ResponseEntity.ok(new longList(announceIdList));
    }

    @PostMapping(value = "/getVisibleAnnounceIdList", produces = "application/json")
    public ResponseEntity<longList> getVisibleAnnounceIdList(@RequestParam String courseId) {
        List<AnnounceProjection> announceList = announceRepository.findProjectByCourseIdAndVisible(courseId, true);
        if (announceList.isEmpty()) {
            return ResponseEntity.ok(new longList(List.of()));
        }
        List<Long> announceIdList = announceList.stream()
                .map(AnnounceProjection::getId)
                .toList();
        return ResponseEntity.ok(new longList(announceIdList));
    }

    @PostMapping(value = "/getAnnounce", produces = "application/json")
    @Transactional
    public ResponseEntity<ClientAnnounce> getAnnounce(@RequestParam Long id) {
        Optional<Announce> announceOptional = announceRepository.findById(id);
        if (announceOptional.isPresent()) {
            Announce announce = announceOptional.get();
            ClientAnnounce clientAnnounce = new ClientAnnounce(
                    announce.getId(),
                    announce.getCourseId(),
                    announce.getAnnounceName(),
                    announce.getUpLoadTime().toString(),
                    announce.getAnnounceContent(),
                    announce.getVisible()
            );
            return ResponseEntity.ok(clientAnnounce);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/closeAnnounce", produces = "application/json")
    public ResponseEntity<SetResponse> closeAnnounce(@RequestParam Long id, @RequestParam String userId) {
        Optional<Announce> announceOptional = announceRepository.findById(id);
        if (!announceOptional.isPresent()) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = announceOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        announceRepository.updateVisibilityById(id.toString(), false);
        return ResponseEntity.ok(new SetResponse(true));
    }

    @PostMapping(value = "/reOpenAnnounce", produces = "application/json")
    public ResponseEntity<SetResponse> reOpenAnnounce(@RequestParam Long id, @RequestParam String userId) {
        Optional<Announce> announceOptional = announceRepository.findById(id);
        if (!announceOptional.isPresent()) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = announceOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        announceRepository.updateVisibilityById(id.toString(), true);
        return ResponseEntity.ok(new SetResponse(true));
    }

    @PostMapping(value = "/setAnnounceName", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> setAnnounceName(@RequestParam Long id, @RequestParam String announceName, @RequestParam String userId) {
        Optional<Announce> announceOptional = announceRepository.findById(id);
        if (!announceOptional.isPresent()) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        String courseId = announceOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(new SetResponse(false));
        }
        announceRepository.updateAnnounceNameById(id.toString(), announceName);
        return ResponseEntity.ok(new SetResponse(true));
    }

    @PostMapping(value = "/setAnnounceContent", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> setAnnounceContent(@RequestParam Long id, @RequestParam String announceContent, @RequestParam String userId) {
        Optional<Announce> announceOptional = announceRepository.findById(id);
        if (announceOptional.isPresent()) {
            Announce announce = announceOptional.get();
            String courseId = announce.getCourseId();
            Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                if (course.getAdminId().equals(userId)) {
                    announce.setannounceContent(announceContent);
                    announceRepository.save(announce);
                    return ResponseEntity.ok(new SetResponse(true));
                } else {
                    return ResponseEntity.ok(new SetResponse(false));
                }
            } else {
                return ResponseEntity.ok(new SetResponse(false));
            }
        } else {
            return ResponseEntity.ok(new SetResponse(false));
        }
    }
}
class ClientAnnounce {
    private Long id;
    private String courseId;
    private String announceName;
    private String upLoadTime;
    private String announceContent;
    private boolean visible;
    public ClientAnnounce(Long id, String courseId, String announceName, String upLoadTime, String announceContent, boolean visible) {
        this.id = id;
        this.courseId = courseId;
        this.announceName = announceName;
        this.upLoadTime = upLoadTime;
        this.announceContent = announceContent;
        this.visible = visible;
    }
    public Long getId() {
        return id;
    }
    public String getCourseId() {
        return courseId;
    }
    public String getAnnounceName() {
        return announceName;
    }
    public String getUpLoadTime() {
        return upLoadTime;
    }
    public String getAnnounceContent() {
        return announceContent;
    }
    public boolean isVisible() {
        return visible;
    }
}
class longList {
    private List<Long> announceIdList;
    public longList(List<Long> announceIdList) {
        this.announceIdList = announceIdList;
    }
    public List<Long> getAnnounceIdList() {
        return announceIdList;
    }
}
