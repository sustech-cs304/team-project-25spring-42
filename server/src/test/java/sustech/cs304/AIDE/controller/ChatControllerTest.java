package sustech.cs304.AIDE.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import sustech.cs304.AIDE.model.ChatMessage;
import sustech.cs304.AIDE.repository.ChatMessageRepository;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendPrivateMessage_SavesAndSendsMessage() {
        // Arrange
        ChatMessage message = new ChatMessage();
        message.setSenderId("user1");
        message.setReceiverId("user2");
        message.setMessage("Hello!");

        // Act
        chatController.sendPrivateMessage(message);

        // Assert
        verify(chatMessageRepository).save(message);
        verify(messagingTemplate).convertAndSend(
            "/topic/private/" + message.getReceiverId(),
            message
        );
        assertNotNull(message.getTimestamp(), "Message timestamp should not be null");
    }

    @Test
    void sendGroupMessage_SavesAndSendsMessage() {
        // Arrange
        ChatMessage message = new ChatMessage();
        message.setSenderId("user1");
        message.setReceiverId("group1");
        message.setMessage("Hello group!");

        // Act
        chatController.sendGroupMessage(message);

        // Assert
        verify(chatMessageRepository).save(message);
        verify(messagingTemplate).convertAndSend(
            "/topic/group/" + message.getReceiverId(),
            message
        );
        assertNotNull(message.getTimestamp(), "Message timestamp should not be null");
    }
} 