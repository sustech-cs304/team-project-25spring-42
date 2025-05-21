package sustech.cs304.AIDE.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import sustech.cs304.AIDE.model.ChatMessage;
import sustech.cs304.AIDE.repository.ChatMessageRepository;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat/private")
    public void sendPrivateMessage(@Payload ChatMessage message) {
        // Save the message to the database
        message.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(message);

        // Send the message to the specific user
        messagingTemplate.convertAndSend(
            "/topic/private/" + message.getReceiverId(),
            message
        );
    }

    @MessageMapping("/chat/group")
    public void sendGroupMessage(@Payload ChatMessage message) {
        // Save the message to the database
        message.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(message);

        // Send the message to the group
        messagingTemplate.convertAndSend(
            "/topic/group/" + message.getReceiverId(),
            message
        );
    }
}
