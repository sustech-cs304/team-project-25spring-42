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
    List<Resource> getResourceByCourseId(Long courseId);
    List<Assignment> getAssignmentByCourseId(Long courseId, String userId);
}
