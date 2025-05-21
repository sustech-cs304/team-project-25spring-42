package sustech.cs304.AIDE.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<ChatMessage>> getDialogMessages(@RequestParam String userId, @RequestParam String friendId) {
        List<ChatMessage> messages = chatMessageRepository.findDialogMessages(userId, friendId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping(value = "/getGroupMessages")
    @Transactional
    public ResponseEntity<List<ChatMessage>> getGroupMessages(@RequestParam String groupId) {
        List<ChatMessage> messages = chatMessageRepository.findGroupMessages(groupId);
        return ResponseEntity.ok(messages);
    }
}
