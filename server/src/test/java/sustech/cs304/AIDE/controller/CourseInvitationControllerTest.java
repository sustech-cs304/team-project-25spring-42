package sustech.cs304.AIDE.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import sustech.cs304.AIDE.model.Course;
import sustech.cs304.AIDE.model.CourseInvitation;
import sustech.cs304.AIDE.model.Enrollment;
import sustech.cs304.AIDE.model.User;
import sustech.cs304.AIDE.repository.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CourseInvitationControllerTest {

    @Mock
    private CourseInvitationRepository courseInvitationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseInvitationController courseInvitationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createInvitation_WhenCourseExists_CreatesInvitation() {
        // Arrange
        Long courseId = 1L;
        String userIds = "user1,user2,user3";
        Course mockCourse = new Course("Test Course", "admin123");
        User mockUser1 = new User("user1", "User 1", "avatar1.jpg");
        User mockUser2 = new User("user2", "User 2", "avatar2.jpg");
        User mockUser3 = new User("user3", "User 3", "avatar3.jpg");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
        when(userRepository.findByPlatformId("user1")).thenReturn(Optional.of(mockUser1));
        when(userRepository.findByPlatformId("user2")).thenReturn(Optional.of(mockUser2));
        when(userRepository.findByPlatformId("user3")).thenReturn(Optional.of(mockUser3));
        when(courseInvitationRepository.save(any(CourseInvitation.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ResponseEntity<String> response = courseInvitationController.createInvitation(courseId, userIds);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Invitations created successfully", response.getBody());
        verify(courseInvitationRepository, times(3)).save(any(CourseInvitation.class));
    }

    @Test
    void createInvitation_WhenCourseDoesNotExist_ReturnsBadRequest() {
        // Arrange
        Long courseId = 999L;
        String userIds = "user1,user2,user3";
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = courseInvitationController.createInvitation(courseId, userIds);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Invitations created successfully", response.getBody());
    }

    @Test
    void acceptInvitation_WhenInvitationExists_AcceptsInvitation() {
        // Arrange
        Long courseId = 1L;
        String userId = "user123";
        CourseInvitation mockInvitation = new CourseInvitation(courseId, userId);

        when(courseInvitationRepository.findByCourseIdAndUserId(courseId, userId)).thenReturn(Optional.of(mockInvitation));
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ResponseEntity<String> response = courseInvitationController.acceptInvitation(courseId, userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Invitation accepted successfully", response.getBody());
        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(courseInvitationRepository).delete(mockInvitation);
    }

    @Test
    void acceptInvitation_WhenInvitationDoesNotExist_ReturnsBadRequest() {
        // Arrange
        Long courseId = 999L;
        String userId = "user123";
        when(courseInvitationRepository.findByCourseIdAndUserId(courseId, userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = courseInvitationController.acceptInvitation(courseId, userId);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("No invitation found for this course and user", response.getBody());
    }

    @Test
    void getInvitationCourses_WhenUserExists_ReturnsCourses() {
        // Arrange
        String userId = "user123";
        List<Long> courseIds = Arrays.asList(1L, 2L);
        List<Course> expectedCourses = Arrays.asList(
            new Course("Course 1", "admin1"),
            new Course("Course 2", "admin2")
        );

        when(courseInvitationRepository.findCourseIdByUserId(userId)).thenReturn(courseIds);
        when(courseRepository.findAllById(courseIds)).thenReturn(expectedCourses);

        // Act
        ResponseEntity<List<Course>> response = courseInvitationController.getInvitationCourses(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedCourses.size(), response.getBody().size());
        verify(courseInvitationRepository).findCourseIdByUserId(userId);
        verify(courseRepository).findAllById(courseIds);
    }

    @Test
    void getInvitationCourses_WhenUserDoesNotExist_ReturnsEmptyList() {
        // Arrange
        String userId = "nonexistent";
        when(courseInvitationRepository.findCourseIdByUserId(userId)).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<Course>> response = courseInvitationController.getInvitationCourses(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
} 