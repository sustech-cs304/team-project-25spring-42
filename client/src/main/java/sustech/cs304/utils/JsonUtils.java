package sustech.cs304.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.Gson;

public class JsonUtils {

    private static final Gson gson = new Gson();

    public static String replaceJsonKey(String json, String oldKey, String newKey) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        // Iterate through the keys and replace the old key with the new key
        replaceKeyRecursively(jsonObject, oldKey, newKey);

        return gson.toJson(jsonObject);
    }

    private static void replaceKeyRecursively(JsonElement element, String oldKey, String newKey) {
        if (element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();
            if (jsonObject.has(oldKey)) {
                JsonElement value = jsonObject.remove(oldKey);
                jsonObject.add(newKey, value); // Add the new key with the old value
            }

            // Recursively check all the nested elements
            for (String key : jsonObject.keySet()) {
                replaceKeyRecursively(jsonObject.get(key), oldKey, newKey);
            }
        } else if (element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement arrayElement : jsonArray) {
                replaceKeyRecursively(arrayElement, oldKey, newKey);
            }
        }
    }
}
