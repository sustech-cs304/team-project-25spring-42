package sustech.cs304.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;
import java.util.function.BiConsumer;

public class StompChatClient extends WebSocketClient {

    private final String userId;
    private final ObjectMapper mapper = new ObjectMapper();
    private boolean stompConnected = false;
    private BiConsumer<String, String> onReceivedMessage;

    public StompChatClient(String userId) throws Exception {
        super(new URI("ws://139.180.143.70:8080/ws/websocket")); // 你服务器地址
        this.userId = userId;
    }

    public void setOnReceivedMessage(BiConsumer<String, String> handler) {
        this.onReceivedMessage = handler;
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

            send(subscribeFrame("/user/" + userId + "/queue/messages"));
            send(subscribeFrame("/topic/group/123")); // 群聊示例
        } else if (msg.startsWith("MESSAGE")) {
            String body = msg.substring(msg.indexOf("\n\n") + 2, msg.lastIndexOf("\u0000"));
            try {
                JsonNode node = mapper.readTree(body);
                String senderId = node.get("senderId").asText();
                String message = node.get("message").asText();
                if (onReceivedMessage != null) {
                    onReceivedMessage.accept(senderId, message);
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
}
