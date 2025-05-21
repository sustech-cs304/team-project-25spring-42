package sustech.cs304.service;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import com.google.gson.Gson;

import okhttp3.Response;
import sustech.cs304.entity.ChatMessage;
import sustech.cs304.entity.Query;
import sustech.cs304.utils.HttpUtils;

public class ChatApiImpl implements ChatApi {
    @Override
    public List<ChatMessage> getChatMessages(String userId, String friendId) {
        List<ChatMessage> messages = null;

        Query[] queries = {
            new Query("userId", userId),
            new Query("friendId", friendId)
        };
        try {
            Response response = HttpUtils.get("/messages", "getDialogMessages", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<ChatMessage>>() {}.getType();
                messages = new Gson().fromJson(responseBody, listType);
            } else {
                System.out.println("Failed to fetch chat messages: " + response.message());
            }
        } catch (Exception e) {
            System.out.println("Error fetching chat messages: " + e.getMessage());
        }
        return messages;
    }
}
