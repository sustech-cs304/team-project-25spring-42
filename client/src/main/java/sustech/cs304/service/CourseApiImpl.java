package sustech.cs304.service;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
        List<Assignment> assignmentList = null;

        RequestBody body = new FormBody.Builder()
            .add("courseId", courseId.toString())
            .add("userId", userId)
            .build();
        try (Response response = HttpUtils.postForm("/assignment", "/getAllAssignment", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<Assignment>>() {}.getType();
                assignmentList = new Gson().fromJson(responseBody, listType);
            } else {
                System.out.println("No assignments found for course ID: " + courseId);
                Assignment assignment = new Assignment(11111L, "test", "test", "test", true, null, null, null);
                assignmentList = List.of(assignment);
            }
        } catch (Exception e) {
            System.out.println("Error fetching assignments: " + e.getMessage());
            Assignment assignment = new Assignment(11111L, "test", "test", "test", true, null, null, null);
            assignmentList = List.of(assignment);
        }
    return assignmentList;
    }

    public Boolean createAnnouncment(Long courseId, String announceName, String announceContent, String userId) {
        RequestBody body = new FormBody.Builder()
            .add("courseId", courseId.toString())
            .add("announceName", announceName)
            .add("announceContent", announceContent)
            .add("userId", userId)
            .build();
        try (Response response = HttpUtils.postForm("/announce", "/createAnnounce", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return Boolean.parseBoolean(responseBody);
            } else {
                System.out.println("Failed to create announcement");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error creating announcement: " + e.getMessage());
            return false;
        }
    }

    public Boolean createAssignment(Long courseId, String assignmentName, String deadline, String userId, String address) {
        Long assignmentId = null;
        RequestBody body = new FormBody.Builder()
            .add("courseId", courseId.toString())
            .add("assignmentName", assignmentName)
            .add("deadline", deadline)
            .add("userId", userId)
            .build();
        try (Response response = HttpUtils.postForm("/assignment", "/createAssignment", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                assignmentId = Long.parseLong(responseBody);
            } else {
                System.out.println("Failed to create assignment");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error creating assignment: " + e.getMessage());
            return false;
        }

        File file = new File(address);
        RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));

        RequestBody body1 = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("assignmentId", assignmentId.toString())
            .addFormDataPart("file", file.getName(), fileBody)
            .addFormDataPart("userId", userId)
            .build();

        try (Response response = HttpUtils.postForm("/assignment", "/uploadAttachment", body1)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return Boolean.parseBoolean(responseBody);
            } else {
                System.out.println("Failed to upload assignment");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error uploading assignment: " + e.getMessage());
            return false;
        }
    }

    public Resource getAttachmentByAssignmentId(Long assignmentId) {
        List<Long> attachmentIds = null;
        RequestBody body = new FormBody.Builder()
            .add("assignmentId", assignmentId.toString())
            .build();
        try (Response response = HttpUtils.postForm("/assignment", "/getAttachmentId", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<Long>>() {}.getType();
                attachmentIds = new Gson().fromJson(responseBody, listType);
            } else {
                System.out.println("No attachments found for assignment ID: " + assignmentId);
            }
        } catch (Exception e) {
            System.out.println("Error fetching attachments: " + e.getMessage());
        }
        if (attachmentIds == null || attachmentIds.isEmpty()) {
            return null;
        }
        Long attachmentId = attachmentIds.get(0);
        RequestBody body1 = new FormBody.Builder()
            .add("resourceId", attachmentId.toString())
            .build();
        Resource resource = null;
        try (Response response = HttpUtils.postForm("/assignment", "/getAttachmentResourceById", body1)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<Resource>() {}.getType();
                resource = new Gson().fromJson(responseBody, listType);
            } else {
                System.out.println("No resources found for attachment ID: " + attachmentId);
                resource = new Resource(11111L, "test", "test", "test", null, 11111L, "test", true);
            }
        } catch (Exception e) {
            System.out.println("Error fetching resources: " + e.getMessage());
            resource = new Resource(11111L, "test", "test", "test", null, 11111L, "test", true);
        }
        return resource;
    }

    public void downloadResource(String address, String savePath) {
        RequestBody body = new FormBody.Builder()
            .add("filePath", address)
            .build();

        try (Response response = HttpUtils.postForm2("/download", "/downloadBinaryByPath", body)) {
            if (response.isSuccessful() && response.body() != null) {
                Path path = Paths.get(savePath);
                Path addressPath = Paths.get(address);
                String fileName = addressPath.getFileName().toString();
                path = path.resolveSibling(fileName);
                Files.copy(response.body().byteStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } else {
                System.out.println("Failed to download resource");
            }
        } catch (Exception e) {
            System.out.println("Error downloading resource: " + e.getMessage());
        }
    }

    public void submitAssignment(Long assignmentId, String userId, String address) {
        File file = new File(address);
        RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));

        RequestBody body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("assignmentId", assignmentId.toString())
            .addFormDataPart("file", file.getName(), fileBody)
            .addFormDataPart("userId", userId)
            .build();

        try (Response response = HttpUtils.postForm("/assignment", "/submitAssignment", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("Assignment submitted successfully: " + responseBody);
            } else {
                System.out.println("Failed to submit assignment");
            }
        } catch (Exception e) {
            System.out.println("Error submitting assignment: " + e.getMessage());
        }
    }

    public void uploadResource(Long courseId, String address, String userId) {
        File file = new File(address);
        RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));

        RequestBody body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("courseId", courseId.toString())
            .addFormDataPart("file", file.getName(), fileBody)
            .addFormDataPart("userId", userId)
            .addFormDataPart("groupId", "0")
            .build();

        try (Response response = HttpUtils.postForm("/resource", "/uploadResource", body)) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("Resource uploaded successfully: " + responseBody);
            } else {
                System.out.println("Failed to upload resource");
            }
        } catch (Exception e) {
            System.out.println("Error uploading resource: " + e.getMessage());
        }
    }
}
