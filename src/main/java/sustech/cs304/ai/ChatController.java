package sustech.cs304.ai;

import sustech.cs304.ai.clients.OpenaiClient;
import sustech.cs304.ai.clients.DeepseekClient;

import java.util.Arrays;
import java.util.List;

public class ChatController {
    private final OpenaiClient openaiClient = new OpenaiClient();
    private final DeepseekClient deepseekClient = new DeepseekClient();
    
    private final List<String> openaiModels = Arrays.asList("gpt-3.5-turbo", "gpt-4.0", "o1", "o1-mini", "o3-mini", "o1-preview");
    private final List<String> deepseekModels = Arrays.asList("deepseek-chat");

    public String getResponse(String model, String prompt) {
        if (openaiModels.contains(model)) {
            return getOpenaiResponse(model, prompt);
        } else if (deepseekModels.contains(model)) {
            return getDeepseekResponse(model, prompt);
        } else {
            return "Invalid model";
        }
    }

    private String getOpenaiResponse(String model, String prompt) {
        try {
            return openaiClient.getResponse(model, prompt);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getDeepseekResponse(String model, String prompt) {
        try {
            return deepseekClient.getResponse(model, prompt);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
