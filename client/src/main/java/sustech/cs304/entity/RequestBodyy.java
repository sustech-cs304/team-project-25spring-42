package sustech.cs304.entity;

import java.util.List;

/**
 * Represents the request body for a chat or messaging API request.
 * Contains the model identifier and a list of messages.
 */
public class RequestBodyy {
    private String model;
    private List<Message> messages;

    /**
     * Constructs a new RequestBodyy with the specified model and messages.
     *
     * @param model    the model identifier
     * @param messages the list of messages
     */
    public RequestBodyy(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    /**
     * Represents an individual message with a role and content.
     */
    public static class Message {
        private String role;
        private String content;

        /**
         * Constructs a new Message with the specified role and content.
         *
         * @param role    the role of the message sender
         * @param content the content of the message
         */
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
