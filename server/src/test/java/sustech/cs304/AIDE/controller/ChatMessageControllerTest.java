package sustech.cs304.AIDE.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import sustech.cs304.AIDE.model.ChatMessage;
import sustech.cs304.AIDE.repository.ChatMessageRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ChatMessageControllerTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatMessageController chatMessageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDialogMessages_ReturnsMessages() {
        // Arrange
        String userId = "user123";
        String friendId = "friend123";
        List<ChatMessage> expectedMessages = Arrays.asList(
            new ChatMessage(),
            new ChatMessage()
        );

        when(chatMessageRepository.findDialogMessages(userId, friendId))
            .thenReturn(expectedMessages);

        // Act
        ResponseEntity<List<ChatMessage>> response = chatMessageController.getDialogMessages(userId, friendId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedMessages.size(), response.getBody().size());
        verify(chatMessageRepository).findDialogMessages(userId, friendId);
    }

    @Test
    void getGroupMessages_ReturnsMessages() {
        // Arrange
        String groupId = "group123";
        List<ChatMessage> expectedMessages = Arrays.asList(
            new ChatMessage(),
            new ChatMessage()
        );

        when(chatMessageRepository.findGroupMessages(groupId))
            .thenReturn(expectedMessages);

        // Act
        ResponseEntity<List<ChatMessage>> response = chatMessageController.getGroupMessages(groupId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedMessages.size(), response.getBody().size());
        verify(chatMessageRepository).findGroupMessages(groupId);
    }
} 