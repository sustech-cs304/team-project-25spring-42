package sustech.cs304.entity;

import java.time.LocalDateTime;

/**
 * Represents a chat message exchanged between users.
 * Contains information such as sender ID, receiver ID, message content, timestamp, and a unique ID.
 */
public class ChatMessage {

    private Long id;

    private String senderId;
    private String receiverId;
    private String message;
    private LocalDateTime timestamp;

    public ChatMessage() {}

    /**
     * Constructs a ChatMessage with the specified sender, receiver, message, and timestamp.
     *
     * @param senderId   the user ID of the sender
     * @param receiverId the user ID of the receiver
     * @param message    the text content of the message
     * @param timestamp  the time when the message was sent
     */
    public ChatMessage(String senderId, String receiverId, String message, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * Returns the user ID of the sender.
     *
     * @return the sender's user ID
     */
    public String getSenderId() { return senderId; }

    /**
     * Returns the user ID of the receiver.
     *
     * @return the receiver's user ID
     */
    public String getReceiverId() { return receiverId; }

    /**
     * Returns the text content of the chat message.
     *
     * @return the message content
     */
    public String getMessage() { return message; }

    /**
     * Returns the timestamp when the message was sent.
     *
     * @return the message timestamp
     */
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * Sets the user ID of the sender.
     *
     * @param senderId the sender's user ID
     */
    public void setSenderId(String senderId) { this.senderId = senderId; }

    /**
     * Sets the user ID of the receiver.
     *
     * @param receiverId the receiver's user ID
     */
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    /**
     * Sets the text content of the chat message.
     *
     * @param message the message content
     */
    public void setMessage(String message) { this.message = message; }

    /**
     * Sets the timestamp when the message was sent.
     *
     * @param timestamp the message timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
