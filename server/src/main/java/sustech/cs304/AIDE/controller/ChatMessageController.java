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

/**
 * Controller for handling chat messages.
 *
 * This class is responsible for retrieving chat messages from the database.
 */
@RestController
@RequestMapping("/messages")
public class ChatMessageController {
    private final ChatMessageRepository chatMessageRepository;

    /**
     * Constructor for ChatMessageController.
     *
     * @param chatMessageRepository The repository for chat messages.
     */
    public ChatMessageController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    /**
     * Retrieves messages from a specific dialog.
     *
     * @param userId   The ID of the user.
     * @param friendId The ID of the friend.
     * @return A list of chat messages in the dialog.
     */
    @GetMapping(value = "/getDialogMessages")
    @Transactional
    public ResponseEntity<List<ChatMessage>> getDialogMessages(@RequestParam String userId, @RequestParam String friendId) {
        List<ChatMessage> messages = chatMessageRepository.findDialogMessages(userId, friendId);
        return ResponseEntity.ok(messages);
    }

    /**
     * Retrieves messages from a specific group.
     *
     * @param groupId The ID of the group.
     * @return A list of chat messages in the group.
     */
    @GetMapping(value = "/getGroupMessages")
    @Transactional
    public ResponseEntity<List<ChatMessage>> getGroupMessages(@RequestParam String groupId) {
        List<ChatMessage> messages = chatMessageRepository.findGroupMessages(groupId);
        return ResponseEntity.ok(messages);
    }
}
