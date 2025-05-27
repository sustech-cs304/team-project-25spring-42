package sustech.cs304.AIDE.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import sustech.cs304.AIDE.model.Announce;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.repository.AnnounceRepository;
import sustech.cs304.AIDE.repository.CourseRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AnnounceControllerTest {

    @Mock
    private AnnounceRepository announceRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private AnnounceController announceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAnnounce_WhenCourseExists_CreatesAnnounce() {
        // Arrange
        String courseId = "1";
        String announceName = "Test Announcement";
        String announceContent = "Test Content";
        String userId = "admin123";

        Course mockCourse = new Course("Test Course", userId);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));
        when(announceRepository.save(any(Announce.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ResponseEntity<Boolean> response = announceController.createAnnounce(courseId, announceName, announceContent, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody());
        verify(announceRepository).save(any(Announce.class));
    }

    @Test
    void createAnnounce_WhenCourseDoesNotExist_ReturnsFalse() {
        // Arrange
        String courseId = "999";
        String announceName = "Test Announcement";
        String announceContent = "Test Content";
        String userId = "admin123";

        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Boolean> response = announceController.createAnnounce(courseId, announceName, announceContent, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody());
    }

    @Test
    void getVisibleAnnounceList_WhenCourseExists_ReturnsAnnounceList() {
        // Arrange
        String courseId = "1";
        List<Announce> expectedAnnounces = Arrays.asList(
            new Announce(courseId, "Test 1", "Content 1"),
            new Announce(courseId, "Test 2", "Content 2")
        );

        when(announceRepository.findByCourseIdAndVisible(courseId, true)).thenReturn(expectedAnnounces);

        // Act
        ResponseEntity<List<ClientAnnounce>> response = announceController.getVisibleAnnounceList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedAnnounces.size(), response.getBody().size());
        verify(announceRepository).findByCourseIdAndVisible(courseId, true);
    }

    @Test
    void getVisibleAnnounceList_WhenCourseDoesNotExist_ReturnsEmptyList() {
        // Arrange
        String courseId = "999";
        when(announceRepository.findByCourseIdAndVisible(courseId, true)).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<ClientAnnounce>> response = announceController.getVisibleAnnounceList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void closeAnnounce_WhenAnnounceExists_ClosesAnnounce() {
        // Arrange
        Long announceId = 1L;
        String userId = "admin123";
        Announce mockAnnounce = new Announce("1", "Test", "Content");
        Course mockCourse = new Course("Test Course", userId);

        when(announceRepository.findById(announceId)).thenReturn(Optional.of(mockAnnounce));
        when(courseRepository.findAdminIdById(1L)).thenReturn(userId);

        // Act
        ResponseEntity<Boolean> response = announceController.closeAnnounce(announceId, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody());
        verify(announceRepository).updateVisibilityById(announceId.toString(), false);
    }

    @Test
    void closeAnnounce_WhenAnnounceDoesNotExist_ReturnsFalse() {
        // Arrange
        Long announceId = 999L;
        String userId = "admin123";
        when(announceRepository.findById(announceId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Boolean> response = announceController.closeAnnounce(announceId, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody());
    }
} 