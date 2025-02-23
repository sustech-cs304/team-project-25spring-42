package sustech.cs304.ai;

public abstract class Chatbot {
    protected String botName;
    protected String apiKey;

    public Chatbot(String botName, String apiKey) {
        this.botName = botName;
        this.apiKey = apiKey;
    }

    public abstract String getResponse(String message);
}
