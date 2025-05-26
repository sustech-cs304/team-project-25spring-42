package sustech.cs304.AIDE.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import sustech.cs304.AIDE.model.User;
import sustech.cs304.AIDE.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SelfRelatedControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SelfRelatedController selfRelatedController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUserInfo_WhenUserExists_ReturnsUserInfo() {
        // Arrange
        String platformId = "test123";
        User mockUser = new User(platformId, "Test User", "http://example.com/avatar.jpg");
        mockUser.setPhoneNumber("1234567890");
        mockUser.setEmail("test@example.com");
        mockUser.setBio("Test bio");

        when(userRepository.findByPlatformId(platformId)).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<ClientUser> response = selfRelatedController.getAllUserInfo(platformId);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(platformId, response.getBody().getPlatformId());
        assertEquals("Test User", response.getBody().getUsername());
        assertEquals("http://example.com/avatar.jpg", response.getBody().getAvatarUrl());
        assertEquals("1234567890", response.getBody().getPhoneNumber());
        assertEquals("test@example.com", response.getBody().getEmail());
        assertEquals("Test bio", response.getBody().getBio());
    }

    @Test
    void getAllUserInfo_WhenUserDoesNotExist_ReturnsNull() {
        // Arrange
        String platformId = "nonexistent";
        when(userRepository.findByPlatformId(platformId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ClientUser> response = selfRelatedController.getAllUserInfo(platformId);

        // Assert
        assertNull(response);
    }

    @Test
    void getUserName_WhenUserExists_ReturnsUsername() {
        // Arrange
        String platformId = "test123";
        User mockUser = new User(platformId, "Test User", "http://example.com/avatar.jpg");

        when(userRepository.findByPlatformId(platformId)).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<UserResponse> response = selfRelatedController.getUserName(platformId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test User", response.getBody().getContent());
    }

    @Test
    void getUserName_WhenUserDoesNotExist_ReturnsNotFound() {
        // Arrange
        String platformId = "nonexistent";
        when(userRepository.findByPlatformId(platformId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserResponse> response = selfRelatedController.getUserName(platformId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void setUserName_WhenUserExists_UpdatesUsername() {
        // Arrange
        String platformId = "test123";
        String newUsername = "New Username";
        User mockUser = new User(platformId, "Old Username", "http://example.com/avatar.jpg");

        when(userRepository.findByPlatformId(platformId)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        ResponseEntity<SetResponse> response = selfRelatedController.setUserName(platformId, newUsername);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getResult());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void setUserName_WhenUserDoesNotExist_ReturnsFalse() {
        // Arrange
        String platformId = "nonexistent";
        String newUsername = "New Username";
        when(userRepository.findByPlatformId(platformId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<SetResponse> response = selfRelatedController.setUserName(platformId, newUsername);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getResult());
    }
} 