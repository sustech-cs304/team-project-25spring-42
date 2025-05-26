package sustech.cs304.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.Gson;

/**
 * Utility class for performing JSON key replacement using Gson.
 * Supports recursive replacement of all occurrences of a specific key in a JSON structure.
 */
public class JsonUtils {

    private static final Gson gson = new Gson();

    /**
     * Replaces all occurrences of a specific JSON key with a new key in the given JSON string.
     * This replacement is performed recursively on all nested objects and arrays.
     *
     * @param json   the original JSON string
     * @param oldKey the key to be replaced
     * @param newKey the new key to use in place of oldKey
     * @return the modified JSON string with all oldKey occurrences replaced by newKey
     */
    public static String replaceJsonKey(String json, String oldKey, String newKey) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        // Iterate through the keys and replace the old key with the new key
        replaceKeyRecursively(jsonObject, oldKey, newKey);

        return gson.toJson(jsonObject);
    }

    /**
     * Recursively replaces all occurrences of oldKey with newKey in the provided JsonElement.
     *
     * @param element the current JSON element (object or array)
     * @param oldKey  the key to be replaced
     * @param newKey  the new key to use
     */
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
