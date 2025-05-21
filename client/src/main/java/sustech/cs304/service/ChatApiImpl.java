package sustech.cs304.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;

import okhttp3.Response;
import sustech.cs304.entity.ChatMessage;
import sustech.cs304.entity.Query;
import sustech.cs304.utils.HttpUtils;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.time.LocalDateTime;

public class ChatApiImpl implements ChatApi {
    @Override
    public List<ChatMessage> getChatMessages(String userId, String friendId) {
        List<ChatMessage> messages = null;

        Query[] queries = {
            new Query("userId", userId),
            new Query("friendId", friendId)
        };
        try {
            Response response = HttpUtils.get("/messages", "/getDialogMessages", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<ChatMessage>>() {}.getType();
                Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                        @Override
                        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                                throws JsonParseException {
                            return LocalDateTime.parse(json.getAsString());
                        }
                    })
                    .create();
                messages = gson.fromJson(responseBody, listType);
            } else {
                System.out.println("Failed to fetch chat messages: " + response.message());
            }
        } catch (Exception e) {
            System.out.println("Error fetching chat messages: " + e.getMessage());
        }
        return messages;
    }

    @Override
    public List<ChatMessage> getGroupMessages(String groupId) {
        List<ChatMessage> messages = null;

        Query[] queries = {
            new Query("groupId", groupId)
        };
        try {
            Response response = HttpUtils.get("/messages", "/getGroupMessages", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<ChatMessage>>() {}.getType();
                Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                        @Override
                        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                                throws JsonParseException {
                            return LocalDateTime.parse(json.getAsString());
                        }
                    })
                    .create();
                messages = gson.fromJson(responseBody, listType);
            } else {
                System.out.println("Failed to fetch group messages: " + response.message());
            }
        } catch (Exception e) {
            System.out.println("Error fetching group messages: " + e.getMessage());
        }
        return messages;
    }
}
