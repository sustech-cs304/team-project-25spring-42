package sustech.cs304.AIDE.controller;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sustech.cs304.AIDE.model.ChatMessage;
import sustech.cs304.AIDE.repository.ChatMessageRepository;

@RestController
@RequestMapping("/messages")
public class ChatMessageController {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @GetMapping(value = "/getDialogMessages")
    @Transactional
    public List<ChatMessage> getDialogMessages(String userId1, String userId2) {
        List<ChatMessage> messages = chatMessageRepository.findDialogMessages(userId1, userId2);
        return messages;
    }
}
