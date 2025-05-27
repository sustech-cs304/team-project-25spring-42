package sustech.cs304.AIDE.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.model.Resource;
import sustech.cs304.AIDE.repository.CourseRepository;
import sustech.cs304.AIDE.repository.ResourceRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ResourceControllerTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceController resourceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadResource_WhenCourseExists_UploadsResource() {
        // Arrange
        String courseId = "1";
        String userId = "admin123";
        Long groupId = 1L;
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.txt",
            "text/plain",
            "Hello, World!".getBytes()
        );

        Course mockCourse = new Course("Test Course", userId);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));
        when(resourceRepository.save(any(Resource.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ResponseEntity<SetResponse> response = resourceController.uploadResource(courseId, file, userId, groupId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getResult());
        verify(resourceRepository).save(any(Resource.class));
    }

    @Test
    void uploadResource_WhenCourseDoesNotExist_ReturnsFalse() {
        // Arrange
        String courseId = "999";
        String userId = "admin123";
        Long groupId = 1L;
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.txt",
            "text/plain",
            "Hello, World!".getBytes()
        );

        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<SetResponse> response = resourceController.uploadResource(courseId, file, userId, groupId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getResult());
    }

    @Test
    void getResourceList_WhenCourseExists_ReturnsResourceList() {
        // Arrange
        String courseId = "1";
        List<Resource> expectedResources = Arrays.asList(
            new Resource("/path/to/resource1", courseId, "Resource 1", "text/plain", 1L, "1024"),
            new Resource("/path/to/resource2", courseId, "Resource 2", "text/plain", 1L, "2048")
        );

        when(resourceRepository.findByCourseId(courseId)).thenReturn(expectedResources);

        // Act
        ResponseEntity<List<ClientResource>> response = resourceController.getResourceList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedResources.size(), response.getBody().size());
        verify(resourceRepository).findByCourseId(courseId);
    }

    @Test
    void getResourceList_WhenCourseDoesNotExist_ReturnsEmptyList() {
        // Arrange
        String courseId = "999";
        when(resourceRepository.findByCourseId(courseId)).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<ClientResource>> response = resourceController.getResourceList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getVisibleResourceList_WhenCourseExists_ReturnsVisibleResourceList() {
        // Arrange
        String courseId = "1";
        List<Resource> expectedResources = Arrays.asList(
            new Resource("/path/to/resource1", courseId, "Resource 1", "text/plain", 1L, "1024"),
            new Resource("/path/to/resource2", courseId, "Resource 2", "text/plain", 1L, "2048")
        );

        when(resourceRepository.findVisibleByCourseId(courseId)).thenReturn(expectedResources);

        // Act
        ResponseEntity<List<ClientResource>> response = resourceController.getVisibleResourceList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedResources.size(), response.getBody().size());
        verify(resourceRepository).findVisibleByCourseId(courseId);
    }

    @Test
    void getVisibleResourceList_WhenCourseDoesNotExist_ReturnsEmptyList() {
        // Arrange
        String courseId = "999";
        when(resourceRepository.findVisibleByCourseId(courseId)).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<ClientResource>> response = resourceController.getVisibleResourceList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void updateGroupId_WhenResourceExists_UpdatesGroupId() {
        // Arrange
        String resourceId = "1";
        Long newGroupId = 2L;
        String userId = "admin123";
        Resource mockResource = new Resource("/path/to/resource", "1", "Resource 1", "text/plain", 1L, "1024");

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(mockResource));
        when(courseRepository.findAdminIdById(1L)).thenReturn(userId);
        when(resourceRepository.save(any(Resource.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ResponseEntity<SetResponse> response = resourceController.updateGroupId(resourceId, newGroupId, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getResult());
        verify(resourceRepository).save(any(Resource.class));
    }

    @Test
    void updateGroupId_WhenResourceDoesNotExist_ReturnsFalse() {
        // Arrange
        String resourceId = "999";
        Long newGroupId = 2L;
        String userId = "admin123";
        when(resourceRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<SetResponse> response = resourceController.updateGroupId(resourceId, newGroupId, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getResult());
    }
} 