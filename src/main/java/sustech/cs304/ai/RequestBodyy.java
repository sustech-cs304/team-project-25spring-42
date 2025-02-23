package sustech.cs304.ai;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class RequestBodyy {
    private String model;
    private List<Message> messages;

    @Data
    @Builder
    public static class Message {
        private String role;
        private String content;
    }
}
