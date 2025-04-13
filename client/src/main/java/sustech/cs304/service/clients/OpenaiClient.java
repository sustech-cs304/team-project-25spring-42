package sustech.cs304.service.clients;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import sustech.cs304.entity.RequestBodyy;
import sustech.cs304.entity.RequestBodyy.Message;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Collections;

public class OpenaiClient {

    private static final String API_KEY = Dotenv.load().get("OPENAI_API_KEY");
    private static final String API_URL = Dotenv.load().get("OPENAI_API_URL");

    private final OkHttpClient client = new OkHttpClient();

    private final Gson gson = new Gson();

    public String getResponse(String model, String prompt) throws IOException  {
        Message message = new Message("user", prompt);
        RequestBodyy requestBody = new RequestBodyy(model, Collections.singletonList(message));

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
