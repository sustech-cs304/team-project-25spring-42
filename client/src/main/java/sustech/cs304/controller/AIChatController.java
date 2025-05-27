package sustech.cs304.controller;

import java.util.Arrays;
import java.util.List;

import sustech.cs304.service.clients.DeepseekClient;
import sustech.cs304.service.clients.GeminiClient;
import sustech.cs304.service.clients.OpenaiClient;

/**
 * AI chat controller, responsible for interacting with different AI chat models (OpenAI, Deepseek, Gemini).
 */
public class AIChatController {
    private final OpenaiClient openaiClient = new OpenaiClient();
    private final DeepseekClient deepseekClient = new DeepseekClient();
    private final GeminiClient geminiClient = new GeminiClient();
    
    private final List<String> openaiModels = Arrays.asList("gpt-3.5-turbo", "gpt-4.0", "o1", "o1-mini", "o3-mini", "o1-preview");
    private final List<String> deepseekModels = Arrays.asList("deepseek-chat", "deepseek-reasoner");
    private final List<String> geminiModels = Arrays.asList("gemini-2.0-flash");

    /**
     * Gets the response from the specified AI model.
     * @param model The model name
     * @param prompt The prompt to send
     * @return The response string
     */
    public String getResponse(String model, String prompt) {
        if (openaiModels.contains(model)) {
            return getOpenaiResponse(model, prompt);
        } else if (deepseekModels.contains(model)) {
            return getDeepseekResponse(model, prompt);
        } else if (geminiModels.contains(model)) {
            return getGeminiResponse(model, prompt);
        } else {
            return "Invalid model";
        }
    }

    /**
     * Gets the response from OpenAI models.
     * @param model The model name
     * @param prompt The prompt to send
     * @return The response string
     */
    private String getOpenaiResponse(String model, String prompt) {
        try {
            return openaiClient.getResponse(model, prompt);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Gets the response from Deepseek models.
     * @param model The model name
     * @param prompt The prompt to send
     * @return The response string
     */
    private String getDeepseekResponse(String model, String prompt) {
        try {
            return deepseekClient.getResponse(model, prompt);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Gets the response from Gemini models.
     * @param model The model name
     * @param prompt The prompt to send
     * @return The response string
     */
    private String getGeminiResponse(String model, String prompt) {
        try {
            return geminiClient.getResponse(model, prompt);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
