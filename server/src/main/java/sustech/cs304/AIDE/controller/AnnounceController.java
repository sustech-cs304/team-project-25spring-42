package sustech.cs304.AIDE.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.List;

import sustech.cs304.AIDE.model.Announce;
import sustech.cs304.AIDE.model.AnnounceProjection;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.repository.AnnounceRepository;
import sustech.cs304.AIDE.repository.CourseRepository;

/**
 * Controller for handling announcements related to courses.
 * 
 * This class provides endpoints for creating, retrieving, updating, and deleting announcements.
 */
@RestController
@RequestMapping("/announce")
public class AnnounceController {

    private final AnnounceRepository announceRepository;
    private final CourseRepository courseRepository;
    
    /**
     * Constructor for AnnounceController.
     *
     * @param announceRepository the AnnounceRepository instance
     * @param courseRepository   the CourseRepository instance
     */
    public AnnounceController(AnnounceRepository announceRepository, CourseRepository courseRepository) {
        this.announceRepository = announceRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Creates a new announcement.
     *
     * @param courseId        the ID of the course
     * @param announceName    the name of the announcement
     * @param announceContent the content of the announcement
     * @param userId          the ID of the user creating the announcement
     * @return a ResponseEntity indicating success or failure
     */
    @PostMapping(value = "/createAnnounce", produces = "application/json")
    @Transactional
    public ResponseEntity<Boolean> createAnnounce(@RequestParam String courseId, @RequestParam String announceName, @RequestParam String announceContent, @RequestParam String userId) {
        Optional<Course> courseOptional = courseRepository.findById(Long.parseLong(courseId));
        if (!courseOptional.isPresent()){
            return ResponseEntity.ok(false);
        }
        String adminId = courseOptional.get().getAdminId();
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(false);
        }
        Announce announce = new Announce(courseId, announceName, announceContent);
        announceRepository.save(announce);
        return ResponseEntity.ok(true);
    }

    /**
     * Retrieves a list of announcements for a given course.
     *
     * @param courseId the ID of the course
     * @return a ResponseEntity containing a list of ClientAnnounce objects
     */
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

    /**
     * Retrieves a list of visible announcements for a given course.
     *
     * @param courseId the ID of the course
     * @return a ResponseEntity containing a list of ClientAnnounce objects
     */
    @PostMapping(value = "/getVisibleAnnounceList", produces = "application/json")
    public ResponseEntity<List<ClientAnnounce>> getVisibleAnnounceList(@RequestParam String courseId) {
        List<Announce> announceList = announceRepository.findByCourseIdAndVisible(courseId, true);
        if (announceList.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        System.out.println("The total number of announces is: " + announceList.size());
        List<ClientAnnounce> clientAnnounceList = announceList.stream()
                .map(announce -> new ClientAnnounce(
                        announce.getId(),
                        announce.getCourseId(),
                        announce.getAnnounceName(),
                        announce.getUpLoadTime().toString(),
                        announce.getAnnounceContent(),
                        announce.getVisible()
                ))
                .toList();
        return ResponseEntity.ok(clientAnnounceList);
    }

    /**
     * Retrieves a list of visible announcement IDs for a given course.
     *
     * @param courseId the ID of the course
     * @return a ResponseEntity containing a list of visible announcement IDs
     */
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
    
    /**
     * Retrieves a specific announcement by its ID.
     *
     * @param id the ID of the announcement
     * @return a ResponseEntity containing the ClientAnnounce object
     */
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

    /**
     * Deletes an announcement by its ID.
     *
     * @param id the ID of the announcement
     * @param userId the ID of the user requesting the deletion
     * @return a ResponseEntity indicating success or failure
     */
    @PostMapping(value = "/closeAnnounce", produces = "application/json")
    public ResponseEntity<Boolean> closeAnnounce(@RequestParam Long id, @RequestParam String userId) {
        Optional<Announce> announceOptional = announceRepository.findById(id);
        if (!announceOptional.isPresent()) {
            return ResponseEntity.ok(false);
        }
        String courseId = announceOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(false);
        }
        announceRepository.updateVisibilityById(id.toString(), false);
        return ResponseEntity.ok(true);
    }

    /**
     * Reopens an announcement by its ID.
     *
     * @param id the ID of the announcement
     * @param userId the ID of the user requesting the reopening
     * @return a ResponseEntity indicating success or failure
     */
    @PostMapping(value = "/reOpenAnnounce", produces = "application/json")
    public ResponseEntity<Boolean> reOpenAnnounce(@RequestParam Long id, @RequestParam String userId) {
        Optional<Announce> announceOptional = announceRepository.findById(id);
        if (!announceOptional.isPresent()) {
            return ResponseEntity.ok(false);
        }
        String courseId = announceOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(false);
        }
        announceRepository.updateVisibilityById(id.toString(), true);
        return ResponseEntity.ok(true);
    }

    /**
     * Updates the name of an announcement by its ID.
     *
     * @param id the ID of the announcement
     * @param announceName the new name for the announcement
     * @param userId the ID of the user requesting the update
     * @return a ResponseEntity indicating success or failure
     */
    @PostMapping(value = "/setAnnounceName", produces = "application/json")
    @Transactional
    public ResponseEntity<Boolean> setAnnounceName(@RequestParam Long id, @RequestParam String announceName, @RequestParam String userId) {
        Optional<Announce> announceOptional = announceRepository.findById(id);
        if (!announceOptional.isPresent()) {
            return ResponseEntity.ok(false);
        }
        String courseId = announceOptional.get().getCourseId();
        String adminId = courseRepository.findAdminIdById(Long.parseLong(courseId));
        if (!adminId.equals(userId)) {
            return ResponseEntity.ok(false);
        }
        announceRepository.updateAnnounceNameById(id.toString(), announceName);
        return ResponseEntity.ok(true);
    }

    /**
     * Updates the content of an announcement by its ID.
     *
     * @param id the ID of the announcement
     * @param announceContent the new content for the announcement
     * @param userId the ID of the user requesting the update
     * @return a ResponseEntity indicating success or failure
     */
    @PostMapping(value = "/setAnnounceContent", produces = "application/json")
    @Transactional
    public ResponseEntity<Boolean> setAnnounceContent(@RequestParam Long id, @RequestParam String announceContent, @RequestParam String userId) {
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
                    return ResponseEntity.ok(true);
                } else {
                    return ResponseEntity.ok(false);
                }
            } else {
                return ResponseEntity.ok(false);
            }
        } else {
            return ResponseEntity.ok(false);
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
    /**
     * Constructor for ClientAnnounce.
     *
     * @param id              the ID of the announcement
     * @param courseId        the ID of the course
     * @param announceName    the name of the announcement
     * @param upLoadTime      the upload time of the announcement
     * @param announceContent the content of the announcement
     * @param visible         whether the announcement is visible
     */
    public ClientAnnounce(Long id, String courseId, String announceName, String upLoadTime, String announceContent, boolean visible) {
        this.id = id;
        this.courseId = courseId;
        this.announceName = announceName;
        this.upLoadTime = upLoadTime;
        this.announceContent = announceContent;
        this.visible = visible;
    }
    /**
     * Getters for the ClientAnnounce class.
     */
    public Long getId() {
        return id;
    }
    /**
     * Getters for the ClientAnnounce class.
     */
    public String getCourseId() {
        return courseId;
    }
    /**
     * Getters for the ClientAnnounce class.
     */
    public String getAnnounceName() {
        return announceName;
    }
    /**
     * Getters for the ClientAnnounce class.
     */
    public String getUpLoadTime() {
        return upLoadTime;
    }
    /**
     * Getters for the ClientAnnounce class.
     */
    public String getAnnounceContent() {
        return announceContent;
    }
    /**
     * Getters for the ClientAnnounce class.
     */
    public boolean isVisible() {
        return visible;
    }
}
class longList {
    private List<Long> longList;
    /**
     * Constructor for longList.
     *
     * @param longList the list of Long values
     */
    public longList(List<Long> longList) {
        this.longList = longList;
    }
    /**
     * Getter for the longList.
     *
     * @return the list of Long values
     */
    public List<Long> getLongList() {
        return longList;
    }
}
