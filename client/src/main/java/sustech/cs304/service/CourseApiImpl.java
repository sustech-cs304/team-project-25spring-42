package sustech.cs304.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Course;
import sustech.cs304.entity.Query;
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
            }
        } catch (Exception e) {
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
            }
        } catch (Exception e) {
            System.err.println("Error fetching announcements: " + e.getMessage());
        }
        return announceList;
    }
}
