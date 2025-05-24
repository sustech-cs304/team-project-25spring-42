package sustech.cs304.AIDE.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This class represents a chat message in the system.
 */
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String senderId;
    private String receiverId;
    private String message;
    private LocalDateTime timestamp;

    public ChatMessage() {}

    /**
     * Constructor for ChatMessage
     *
     * @param senderId    ID of the sender
     * @param receiverId  ID of the receiver
     * @param message     The message content
     * @param timestamp  The time the message was sent
     */
    public ChatMessage(String senderId, String receiverId, String message, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * get senderId
     * @return senderId
     */
    public String getSenderId() { return senderId; }

    /**
     * get receiverId
     * @return receiverId
     */
    public String getReceiverId() { return receiverId; }

    /**
     * get message
     * @return message
     */
    public String getMessage() { return message; }

    /**
     * get timestamp
     * @return timestamp
     */
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * set senderId
     * @param senderId the ID of the sender
     */
    public void setSenderId(String senderId) { this.senderId = senderId; }

    /**
     * set receiverId
     * @param receiverId the ID of the receiver
     */
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    /**
     * set message
     * @param message the content of the message
     */
    public void setMessage(String message) { this.message = message; }

    /**
     * set timestamp
     * @param timestamp the time the message was sent
     */
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
