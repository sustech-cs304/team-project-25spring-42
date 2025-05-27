package sustech.cs304.service.clients;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Collections;

import sustech.cs304.entity.RequestBodyy;
import sustech.cs304.entity.RequestBodyy.Message;
import sustech.cs304.utils.FileUtils;
import sustech.cs304.utils.JsonUtils;

public class GeminiClient {

    private static final String API_KEY = FileUtils.getEnvValue("GEMINI_API_KEY");
    private static final String API_URL = FileUtils.getEnvValue("GEMINI_API_URL");

    private final OkHttpClient client = new OkHttpClient();

    private final Gson gson = new Gson();

    public String getResponse(String model, String prompt) throws IOException  {
        Message message = new Message("user", prompt);
        RequestBodyy requestBody = new RequestBodyy(model, Collections.singletonList(message));

        String jsonRequestBody = gson.toJson(requestBody);
        String updatedJsonRequestBody = JsonUtils.replaceJsonKey(jsonRequestBody, "content", "part");

        Request request = new Request.Builder()
            .url(API_URL + "/model/" + model + ":generateContent?key=" + API_KEY)
            .post(RequestBody.create(updatedJsonRequestBody, MediaType.get("application/json")))
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray choices = jsonObject.getAsJsonArray("choices");
            JsonObject messageObj = choices.get(0).getAsJsonObject().getAsJsonObject("message");
            return messageObj.get("content").getAsString();

            // return response.body().string();
        }
    }
    
}
