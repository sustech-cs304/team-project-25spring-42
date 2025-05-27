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

public class StompChatClient extends WebSocketClient {

    private final String userId;
    private final List<Long> courseIds;
    private final ObjectMapper mapper = new ObjectMapper();
    private boolean stompConnected = false;
    private BiConsumer<String, String> onReceivedMessage;
    private TriConsumer<String, String, String> onReceivedGroupMessage;

    public StompChatClient(String userId, List<Long> courseIds) throws Exception {
        super(new URI(ServerConfig.SERVER_URL_WS));
        this.userId = userId;
        this.courseIds = courseIds;
    }

    public void setOnReceivedMessage(BiConsumer<String, String> handler) {
        this.onReceivedMessage = handler;
    }

    public void setOnReceivedGroupMessage(TriConsumer<String, String, String> handler) {
        this.onReceivedGroupMessage = handler;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("WebSocket connected");
        send("CONNECT\naccept-version:1.2\nheart-beat:10000,10000\n\n\u0000");
    }

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

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    private String subscribeFrame(String destination) {
        return "SUBSCRIBE\nid:sub-" + destination.hashCode() + "\ndestination:" + destination + "\n\n\u0000";
    }

    private String sendFrame(String destination, String bodyJson) {
        return "SEND\ndestination:" + destination + "\ncontent-type:application/json\n\n" + bodyJson + "\u0000";
    }

    public void sendPrivateMessage(String toUserId, String messageText) throws Exception {
        Map<String, Object> msg = Map.of(
            "senderId", userId,
            "receiverId", toUserId,
            "message", messageText
        );
        send(sendFrame("/app/chat/private", mapper.writeValueAsString(msg)));
    }

    public void sendGroupMessage(String groupId, String messageText) throws Exception {
        Map<String, Object> msg = Map.of(
            "senderId", userId,
            "receiverId", groupId,
            "message", messageText
        );
        send(sendFrame("/app/chat/group", mapper.writeValueAsString(msg)));
    }

    public void disconnect() {
        if (this.isOpen()) {
            this.close();
        }
        stompConnected = false;
    }
}
