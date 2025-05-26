package sustech.cs304.AIDE.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import sustech.cs304.AIDE.model.Friendship;
import sustech.cs304.AIDE.model.User;
import sustech.cs304.AIDE.repository.FriendshipRepository;
import sustech.cs304.AIDE.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FriendshipControllerTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FriendshipController friendshipController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void applyFriendship_WhenValidRequest_SendsRequest() {
        // Arrange
        String applicantId = "user1";
        String targetId = "user2";

        // Act
        ResponseEntity<String> response = friendshipController.applyFriendship(applicantId, targetId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Friendship request sent"));
        verify(friendshipRepository).save(any(Friendship.class));
    }

    @Test
    void acceptFriendship_WhenRequestExists_AcceptsRequest() {
        // Arrange
        String applicantId = "user1";
        String targetId = "user2";
        Friendship mockFriendship = new Friendship(applicantId, targetId, "pending");
        when(friendshipRepository.findByApplicantIdAndTargetId(applicantId, targetId)).thenReturn(mockFriendship);

        // Act
        ResponseEntity<String> response = friendshipController.acceptFriendship(applicantId, targetId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Friendship request accepted"));
        assertEquals("accepted", mockFriendship.getStatus());
        verify(friendshipRepository).save(mockFriendship);
    }

    @Test
    void acceptFriendship_WhenRequestDoesNotExist_ReturnsNotFound() {
        // Arrange
        String applicantId = "user1";
        String targetId = "user2";
        when(friendshipRepository.findByApplicantIdAndTargetId(applicantId, targetId)).thenReturn(null);

        // Act
        ResponseEntity<String> response = friendshipController.acceptFriendship(applicantId, targetId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Friendship request not found"));
    }

    @Test
    void rejectFriendship_WhenRequestExists_RejectsRequest() {
        // Arrange
        String applicantId = "user1";
        String targetId = "user2";
        Friendship mockFriendship = new Friendship(applicantId, targetId, "pending");
        when(friendshipRepository.findByApplicantIdAndTargetId(applicantId, targetId)).thenReturn(mockFriendship);

        // Act
        ResponseEntity<String> response = friendshipController.rejectFriendship(applicantId, targetId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Friendship request rejected"));
        verify(friendshipRepository).delete(mockFriendship);
    }

    @Test
    void rejectFriendship_WhenRequestDoesNotExist_ReturnsNotFound() {
        // Arrange
        String applicantId = "user1";
        String targetId = "user2";
        when(friendshipRepository.findByApplicantIdAndTargetId(applicantId, targetId)).thenReturn(null);

        // Act
        ResponseEntity<String> response = friendshipController.rejectFriendship(applicantId, targetId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Friendship request not found"));
    }

    @Test
    void deleteFriendship_WhenFriendshipExists_DeletesFriendship() {
        // Arrange
        String userId1 = "user1";
        String userId2 = "user2";
        Friendship mockFriendship = new Friendship(userId1, userId2, "accepted");
        when(friendshipRepository.findByApplicantIdAndTargetId(userId1, userId2)).thenReturn(mockFriendship);

        // Act
        ResponseEntity<String> response = friendshipController.deleteFriendship(userId1, userId2);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Friendship deleted"));
        verify(friendshipRepository).delete(mockFriendship);
    }

    @Test
    void deleteFriendship_WhenFriendshipDoesNotExist_ReturnsNotFound() {
        // Arrange
        String userId1 = "user1";
        String userId2 = "user2";
        when(friendshipRepository.findByApplicantIdAndTargetId(userId1, userId2)).thenReturn(null);
        when(friendshipRepository.findByApplicantIdAndTargetId(userId2, userId1)).thenReturn(null);

        // Act
        ResponseEntity<String> response = friendshipController.deleteFriendship(userId1, userId2);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Friendship not found"));
    }

    @Test
    void getFriendList_WhenUserHasFriends_ReturnsFriendList() {
        // Arrange
        String userId = "user1";
        String friendId = "user2";
        User mockFriend = new User(friendId, "Friend User", "http://example.com/avatar.jpg");
        Friendship mockFriendship = new Friendship(userId, friendId, "accepted");
        
        when(friendshipRepository.findByUserId(userId)).thenReturn(Arrays.asList(mockFriendship));
        when(userRepository.findByPlatformId(friendId)).thenReturn(Optional.of(mockFriend));

        // Act
        ResponseEntity<List<User>> response = friendshipController.getFriendList(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(friendId, response.getBody().get(0).getPlatformId());
    }

    @Test
    void getFriendRequestList_WhenUserHasRequests_ReturnsRequestList() {
        // Arrange
        String userId = "user1";
        String requesterId = "user2";
        User mockRequester = new User(requesterId, "Requester User", "http://example.com/avatar.jpg");
        Friendship mockFriendship = new Friendship(requesterId, userId, "pending");
        
        when(friendshipRepository.findByUserId(userId)).thenReturn(Arrays.asList(mockFriendship));
        when(userRepository.findByPlatformId(requesterId)).thenReturn(Optional.of(mockRequester));

        // Act
        ResponseEntity<List<User>> response = friendshipController.getFriendRequestList(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(requesterId, response.getBody().get(0).getPlatformId());
    }
} 