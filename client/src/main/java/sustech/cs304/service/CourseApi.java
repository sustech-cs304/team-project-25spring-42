package sustech.cs304.service;

import java.util.List;

import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Assignment;
import sustech.cs304.entity.Course;
import sustech.cs304.entity.Resource;

public interface CourseApi {
    List<Long> getCourseIdByUserId(String userId);
    Course getCourseById(Long courseId);
    List<Announce> getAnnounceByCourseId(Long courseId);
    Boolean createAnnouncment(Long courseId, String announceName, String announceContent, String userId);
    List<Resource> getResourceByCourseId(Long courseId);
    void uploadResource(Long courseId, String address, String userId);
    List<Assignment> getAssignmentByCourseId(Long courseId, String userId);
    Boolean createAssignment(Long courseId, String assignmentName, String deadline, String userId, String address);
    Resource getAttachmentByAssignmentId(Long assignmentId);
    void downloadResource(String address, String savePath);
    void submitAssignment(Long assignmentId, String userId, String address);
    void createCourse(String courseName, String userId);
    String getAdminIdByCourseId(Long courseId);
    void deleteCourse(Long courseId, String adminId);
}
