package sustech.cs304.AIDE.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import sustech.cs304.AIDE.model.Assignment;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.repository.AssignmentRepository;
import sustech.cs304.AIDE.repository.CourseRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AssignmentCenterControllerTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private AssignmentCenterController assignmentCenterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAssignment_WhenCourseExists_CreatesAssignment() {
        // Arrange
        String courseId = "1";
        String userId = "admin123";
        String assignmentName = "Test Assignment";
        String deadline = "2024-12-31T23:59:59";

        Course mockCourse = new Course("Test Course", userId);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));
        when(assignmentRepository.save(any(Assignment.class))).thenAnswer(i -> i.getArgument(0));
        when(assignmentRepository.findAssignmentIdByAssignmentNameAndDeadline(anyString(), any(LocalDateTime.class))).thenReturn(1L);

        // Act
        ResponseEntity<Long> response = assignmentCenterController.createAssignment(courseId, assignmentName, deadline, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody());
        verify(assignmentRepository).save(any(Assignment.class));
    }

    @Test
    void createAssignment_WhenCourseDoesNotExist_ReturnsNotFound() {
        // Arrange
        String courseId = "999";
        String userId = "admin123";
        String assignmentName = "Test Assignment";
        String deadline = "2024-12-31T23:59:59";

        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Long> response = assignmentCenterController.createAssignment(courseId, assignmentName, deadline, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(-1L, response.getBody());
    }

    @Test
    void getAssignmentIdList_WhenCourseExists_ReturnsAssignmentList() {
        // Arrange
        String courseId = "1";
        List<Long> mockAssignmentIds = Arrays.asList(1L, 2L, 3L);
        Course mockCourse = new Course("Test Course", "admin123");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));
        when(assignmentRepository.findVisibleAssignmentIdByCourseId(courseId)).thenReturn(mockAssignmentIds);

        // Act
        ResponseEntity<List<Long>> response = assignmentCenterController.getAssignmentIdList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(mockAssignmentIds, response.getBody());
    }

    @Test
    void getAssignmentIdList_WhenCourseDoesNotExist_ReturnsNull() {
        // Arrange
        String courseId = "999";
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<Long>> response = assignmentCenterController.getAssignmentIdList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getVisibleAssignmentIdList_WhenCourseExists_ReturnsVisibleAssignmentList() {
        // Arrange
        String courseId = "1";
        List<Long> mockAssignmentIds = Arrays.asList(1L, 2L);
        Course mockCourse = new Course("Test Course", "admin123");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));
        when(assignmentRepository.findVisibleAssignmentIdByCourseId(courseId)).thenReturn(mockAssignmentIds);

        // Act
        ResponseEntity<List<Long>> response = assignmentCenterController.getVisibleAssignmentIdList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(mockAssignmentIds, response.getBody());
    }

    @Test
    void getVisibleAssignmentIdList_WhenCourseDoesNotExist_ReturnsNull() {
        // Arrange
        String courseId = "999";
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<Long>> response = assignmentCenterController.getVisibleAssignmentIdList(courseId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void changeAssignmentName_WhenAssignmentExists_ChangesName() {
        // Arrange
        String assignmentId = "1";
        String userId = "admin123";
        String newName = "Updated Assignment";

        Assignment mockAssignment = new Assignment("Old Assignment", LocalDateTime.now(), "1");
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(mockAssignment));
        when(courseRepository.findAdminIdById(1L)).thenReturn(userId);
        when(assignmentRepository.save(any(Assignment.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ResponseEntity<SetResponse> response = assignmentCenterController.changeAssignmentName(assignmentId, newName, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getResult());
        verify(assignmentRepository).save(any(Assignment.class));
    }

    @Test
    void changeAssignmentName_WhenAssignmentDoesNotExist_ReturnsFalse() {
        // Arrange
        String assignmentId = "999";
        String userId = "admin123";
        String newName = "Updated Assignment";

        when(assignmentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<SetResponse> response = assignmentCenterController.changeAssignmentName(assignmentId, newName, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getResult());
    }
} 