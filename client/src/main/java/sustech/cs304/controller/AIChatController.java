package sustech.cs304.controller;

import java.util.Arrays;
import java.util.List;

import sustech.cs304.service.clients.DeepseekClient;
import sustech.cs304.service.clients.GeminiClient;
import sustech.cs304.service.clients.OpenaiClient;

public class AIChatController {
    private final OpenaiClient openaiClient = new OpenaiClient();
    private final DeepseekClient deepseekClient = new DeepseekClient();
    private final GeminiClient geminiClient = new GeminiClient();
    
    private final List<String> openaiModels = Arrays.asList("gpt-3.5-turbo", "gpt-4.0", "o1", "o1-mini", "o3-mini", "o1-preview");
    private final List<String> deepseekModels = Arrays.asList("deepseek-chat", "deepseek-reasoner");
    private final List<String> geminiModels = Arrays.asList("gemini-2.0-flash");

    public String getResponse(String model, String prompt) {
        if (openaiModels.contains(model)) {
            return getOpenaiResponse(model, prompt);
        } else if (deepseekModels.contains(model)) {
            return getDeepseekResponse(model, prompt);
        } else if (geminiModels.contains(model)) {
            return getOpenaiResponse(model, prompt);
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

    private String getGeminiResponse(String model, String prompt) {
        try {
            return geminiClient.getResponse(model, prompt);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
