package sustech.cs304.AIDE.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CourseControllerTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private ResourceRepository resourceRepository;
    @Mock
    private AnnounceRepository announceRepository;
    @Mock
    private SubmissionRepository submissionRepository;
    @Mock
    private CourseInvitationRepository courseInvitationRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCourseById_WhenCourseExists_ReturnsCourse() {
        // Arrange
        String courseId = "1";
        Course mockCourse = new Course("Test Course", "admin123");
        mockCourse.setOpenTime(LocalDateTime.now());
        mockCourse.setCloseTime(LocalDateTime.now().plusDays(30));
        mockCourse.setOpening(true);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));

        // Act
        ResponseEntity<ClientCourse> response = courseController.getCourseById(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Course", response.getBody().getCourseName());
    }

    @Test
    void getCourseById_WhenCourseDoesNotExist_ReturnsNotFound() {
        // Arrange
        String courseId = "999";
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ClientCourse> response = courseController.getCourseById(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getCourseName_WhenCourseExists_ReturnsCourseName() {
        // Arrange
        String courseId = "1";
        Course mockCourse = new Course("Test Course", "admin123");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));

        // Act
        ResponseEntity<UserResponse> response = courseController.getCourseName(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Course", response.getBody().getContent());
    }

    @Test
    void getCourseName_WhenCourseDoesNotExist_ReturnsNotFound() {
        // Arrange
        String courseId = "999";
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserResponse> response = courseController.getCourseName(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
} 