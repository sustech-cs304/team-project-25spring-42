package sustech.cs304.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Assignment;
import sustech.cs304.entity.Course;
import sustech.cs304.entity.Query;
import sustech.cs304.entity.Resource;
import sustech.cs304.utils.HttpUtils;

public class CourseApiImpl implements CourseApi {

    public List<Long> getCourseIdByUserId(String userId) {
        List<Long> courseIds = null;

        RequestBody body = new FormBody.Builder()
            .add("userId", userId)
            .build();

        try (Response response = HttpUtils.postForm("/course", "/getCourseIdList", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<Long>>() {}.getType();
                courseIds = new Gson().fromJson(responseBody, listType);
            } else {
                courseIds = new ArrayList<>();
                courseIds.add(111111L);
            }
        } catch (Exception e) {
            courseIds = new ArrayList<>();
            courseIds.add(111111L);
            System.err.println("Error fetching course IDs: " + e.getMessage());
        }

        return courseIds;
    }

    public Course getCourseById(Long courseId) {
        Course course = null;
        Query[] queries = {
            new Query("courseId", courseId.toString())
        };
        try {
            Response response = HttpUtils.get("/course", "/getCourseById", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                course = gson.fromJson(responseBody, Course.class);
            } else {
                throw new RuntimeException("Failed to get course: " + response.message());
            }
        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            course = new Course((Long) 111111L, "DSAA", "admin", "2023-10-01", "2023-10-31", true);
        }
        return course;
    }

    public List<Announce> getAnnounceByCourseId(Long courseId) {
        List<Announce> announceList = null;
        RequestBody body = new FormBody.Builder()
            .add("courseId", courseId.toString())
            .build();
        try (Response response = HttpUtils.postForm("/announce", "/getVisibleAnnounceList", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<Announce>>() {}.getType();
                announceList = new Gson().fromJson(responseBody, listType);
            } else {
                System.out.println("No announcements found for course ID: " + courseId);
                Announce announce = new Announce(11111L, "test", "test", "2023-10-01", "2023-10-31", true);
                announceList = List.of(announce);
            }
        } catch (Exception e) {
            System.out.println("Error fetching announcements: " + e.getMessage());
            Announce announce = new Announce(11111L, "test", "test", "2023-10-01", "2023-10-31", true);
            announceList = List.of(announce);
        }
        return announceList;
    }

    public List<Resource> getResourceByCourseId(Long courseId) {
        List<Resource> resourceList = null;
        RequestBody body = new FormBody.Builder()
            .add("courseId", courseId.toString())
            .build();
        try (Response response = HttpUtils.postForm("/resource", "/getVisibleResourceList", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<Resource>>() {}.getType();
                resourceList = new Gson().fromJson(responseBody, listType);
            } else {
                System.out.println("No resources found for course ID: " + courseId);
                Resource resource = new Resource(11111L, "test", "test", "test", null, 11111L, "test", true);
                resourceList = List.of(resource);
            }
        } catch (Exception e) {
            System.out.println("Error fetching resources: " + e.getMessage());
            Resource resource = new Resource(11111L, "test", "test", "test", null, 11111L, "test", true);
            resourceList = List.of(resource);
        }
        return resourceList;
    }

    public List<Assignment> getAssignmentByCourseId(Long courseId, String userId) {
        List<Long> assignmentIds = null;
        List<Assignment> assignmentList = new ArrayList<>();
        RequestBody body = new FormBody.Builder()
            .add("courseId", courseId.toString())
            .build();
        try (Response response = HttpUtils.postForm("/assignment", "/getVisibleAssignmentIdList", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<Long>>() {}.getType();
                assignmentIds = new Gson().fromJson(responseBody, listType);
            } else {
                System.out.println("No assignments found for course ID: " + courseId);
                Assignment assignment = new Assignment(11111L, "test", "test", "test", true);
                assignmentList = List.of(assignment);
            }
        } catch (Exception e) {
            System.out.println("Error fetching assignments: " + e.getMessage());
            Assignment assignment = new Assignment(11111L, "test", "test", "test", true);
            assignmentList = List.of(assignment);
        }

        if (assignmentIds == null) {
            return assignmentList;
        }

        for (Long assignmentId : assignmentIds) {
            RequestBody body1 = new FormBody.Builder()
                .add("assignmentId", assignmentId.toString())
                .add("userId", userId)
                .build();
            try (Response response = HttpUtils.postForm("/assignment", "/getAssignment", body1)) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    Type listType = new TypeToken<Assignment>() {}.getType();
                    Assignment assignment = new Gson().fromJson(responseBody, listType);
                    assignmentList.add(assignment);
                } else {
                    System.out.println("No assignments found for course ID: " + courseId);
                    Assignment assignment = new Assignment(11111L, "test", "test", "test", true);
                    assignmentList = List.of(assignment);
                }
            } catch (Exception e) {
                System.out.println("Error fetching assignments: " + e.getMessage());
                Assignment assignment = new Assignment(11111L, "test", "test", "test", true);
                assignmentList = List.of(assignment);
            }
        }
        return assignmentList;
    }
}
