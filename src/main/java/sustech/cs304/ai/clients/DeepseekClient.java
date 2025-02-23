package sustech.cs304.ai.clients;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;
import java.io.IOException;
import sustech.cs304.ai.RequestBodyy;
import sustech.cs304.ai.RequestBodyy.Message;
import java.util.List;
import java.util.Collections;

public class DeepseekClient {

    private static final String API_KEY = Dotenv.load().get("DEEPSEEK_API_KEY");
    private static final String API_URL = Dotenv.load().get("DEEPSEEK_API_URL");

    private final OkHttpClient client = new OkHttpClient();

    private final Gson gson = new Gson();

    public String getResponse(String model, String prompt) throws IOException  {
        Message message = Message.builder()
            .role("user")
            .content(prompt)
            .build();

        RequestBodyy requestBody = RequestBodyy.builder()
            .model(model)
            .messages(Collections.singletonList(message))
            .build();

        Request request = new Request.Builder()
            .url(API_URL)
            .post(RequestBody.create(gson.toJson(requestBody), MediaType.get("application/json")))
            .addHeader("Authorization", "Bearer " + API_KEY)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
    
}
