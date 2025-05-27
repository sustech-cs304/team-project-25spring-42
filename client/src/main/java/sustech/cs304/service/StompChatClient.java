package sustech.cs304.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import org.apache.commons.lang3.function.TriConsumer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import sustech.cs304.utils.ServerConfig;

/**
 * StompChatClient is a WebSocket client that connects to a STOMP server for real-time chat functionality.
 * It allows sending and receiving private and group messages, subscribing to topics, and handling incoming messages.
 */
public class StompChatClient extends WebSocketClient {

    private final String userId;
    private final List<Long> courseIds;
    private final ObjectMapper mapper = new ObjectMapper();
    private boolean stompConnected = false;
    private BiConsumer<String, String> onReceivedMessage;
    private TriConsumer<String, String, String> onReceivedGroupMessage;

    /**
     * Constructs a StompChatClient with the specified user ID and course IDs.
     *
     * @param userId The ID of the user.
     * @param courseIds A list of course IDs to subscribe to for group messages.
     * @throws Exception If the URI is invalid or cannot be connected.
     */
    public StompChatClient(String userId, List<Long> courseIds) throws Exception {
        super(new URI(ServerConfig.SERVER_URL_WS));
        this.userId = userId;
        this.courseIds = courseIds;
    }

    /**
     * Sets the handler for receiving private messages.
     *
     * @param handler A BiConsumer that takes the sender ID and message text.
     */
    public void setOnReceivedMessage(BiConsumer<String, String> handler) {
        this.onReceivedMessage = handler;
    }

    /**
     * Sets the handler for receiving group messages.
     *
     * @param handler A TriConsumer that takes the sender ID, group ID, and message text.
     */
    public void setOnReceivedGroupMessage(TriConsumer<String, String, String> handler) {
        this.onReceivedGroupMessage = handler;
    }

    /**
     * Checks if the STOMP connection is established.
     *
     */
    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("WebSocket connected");
        send("CONNECT\naccept-version:1.2\nheart-beat:10000,10000\n\n\u0000");
    }

    /**
     * Handles incoming messages from the STOMP server.
     *
     * @param msg The message received from the server.
     */
    @Override
    public void onMessage(String msg) {
        if (msg.startsWith("CONNECTED")) {
            stompConnected = true;
            System.out.println("STOMP connected");

            for (Long courseId : courseIds) {
                String courseIdStr = String.valueOf(courseId);
                send(subscribeFrame("/topic/group/" + courseIdStr));
            }

            send(subscribeFrame("/topic/private/" + userId));;
        } else if (msg.startsWith("MESSAGE")) {
            String body = msg.substring(msg.indexOf("\n\n") + 2, msg.lastIndexOf("\u0000"));
            try {
                JsonNode node = mapper.readTree(body);
                String senderId = node.get("senderId").asText();
                String receiverId = node.get("receiverId").asText();
                String message = node.get("message").asText();
                if (onReceivedMessage != null && receiverId.equals(userId)) {
                    onReceivedMessage.accept(senderId, message);
                } else if (onReceivedGroupMessage != null && !receiverId.equals(userId)) {
                    onReceivedGroupMessage.accept(senderId, receiverId, message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Received: " + msg);
        }
    }

    /**
     * Handles the closing of the WebSocket connection.
     *
     * @param code The closing code.
     * @param reason The reason for closing.
     * @param remote Whether the close was initiated by the remote peer.
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason);
    }

    /**
     * Handles errors that occur during WebSocket communication.
     *
     * @param ex The exception that occurred.
     */
    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    /**
     * Sends a message frame to the STOMP server.
     *
     * @param frame The message frame to send.
     */
    private String subscribeFrame(String destination) {
        return "SUBSCRIBE\nid:sub-" + destination.hashCode() + "\ndestination:" + destination + "\n\n\u0000";
    }

    /**
     * Sends a message frame to the STOMP server with the specified destination and body.
     *
     * @param destination The destination to send the message to.
     * @param bodyJson The JSON body of the message.
     * @return The formatted message frame.
     */
    private String sendFrame(String destination, String bodyJson) {
        return "SEND\ndestination:" + destination + "\ncontent-type:application/json\n\n" + bodyJson + "\u0000";
    }

    /**
     * Sends a private message to the specified user ID.
     *
     * @param toUserId The ID of the user to send the message to.
     * @param messageText The text of the message to send.
     * @throws Exception If there is an error sending the message.
     */
    public void sendPrivateMessage(String toUserId, String messageText) throws Exception {
        Map<String, Object> msg = Map.of(
            "senderId", userId,
            "receiverId", toUserId,
            "message", messageText
        );
        send(sendFrame("/app/chat/private", mapper.writeValueAsString(msg)));
    }

    /**
     * Sends a group message to the specified group ID.
     *
     * @param groupId The ID of the group to send the message to.
     * @param messageText The text of the message to send.
     * @throws Exception If there is an error sending the message.
     */
    public void sendGroupMessage(String groupId, String messageText) throws Exception {
        Map<String, Object> msg = Map.of(
            "senderId", userId,
            "receiverId", groupId,
            "message", messageText
        );
        send(sendFrame("/app/chat/group", mapper.writeValueAsString(msg)));
    }

    /**
     * Checks if the STOMP connection is currently open.
     *
     */
    public void disconnect() {
        if (this.isOpen()) {
            this.close();
        }
        stompConnected = false;
    }
}
