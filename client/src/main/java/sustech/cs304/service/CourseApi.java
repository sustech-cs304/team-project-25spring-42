package sustech.cs304.service;

import java.util.List;

import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Course;

public interface CourseApi {
    List<Long> getCourseIdByUserId(String userId);
    Course getCourseById(Long courseId);
    List<Announce> getAnnounceByCourseId(Long courseId);
}
