package sustech.cs304.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    private final Gson gson = new Gson();

    @Test
    void testReplaceJsonKey_simpleObject() {
        String input = "{\"oldKey\":\"value1\",\"other\":\"value2\"}";
        String expected = "{\"newKey\":\"value1\",\"other\":\"value2\"}";

        String result = JsonUtils.replaceJsonKey(input, "oldKey", "newKey");
        JsonObject resultObj = gson.fromJson(result, JsonObject.class);
        JsonObject expectedObj = gson.fromJson(expected, JsonObject.class);

        assertEquals(expectedObj, resultObj);
    }

    @Test
    void testReplaceJsonKey_nestedObject() {
        String input = "{\"level1\":{\"oldKey\":\"value\"},\"oldKey\":123}";
        String expected = "{\"level1\":{\"newKey\":\"value\"},\"newKey\":123}";

        String result = JsonUtils.replaceJsonKey(input, "oldKey", "newKey");
        assertEquals(
                gson.fromJson(expected, JsonObject.class),
                gson.fromJson(result, JsonObject.class)
        );
    }

    @Test
    void testReplaceJsonKey_arrayOfObjects() {
        String input = "{\"arr\":[{\"oldKey\":\"a\"},{\"oldKey\":\"b\"}]}";
        String expected = "{\"arr\":[{\"newKey\":\"a\"},{\"newKey\":\"b\"}]}";

        String result = JsonUtils.replaceJsonKey(input, "oldKey", "newKey");
        assertEquals(
                gson.fromJson(expected, JsonObject.class),
                gson.fromJson(result, JsonObject.class)
        );
    }

    @Test
    void testReplaceJsonKey_complexStructure() {
        String input = "{\"a\":{\"b\":[{\"oldKey\":1}, {\"c\":{\"oldKey\":2}}]}, \"oldKey\":3}";
        String expected = "{\"a\":{\"b\":[{\"newKey\":1}, {\"c\":{\"newKey\":2}}]}, \"newKey\":3}";

        String result = JsonUtils.replaceJsonKey(input, "oldKey", "newKey");
        assertEquals(
                gson.fromJson(expected, JsonObject.class),
                gson.fromJson(result, JsonObject.class)
        );
    }

    @Test
    void testReplaceJsonKey_noOldKey() {
        String input = "{\"foo\":1,\"bar\":2}";
        String expected = "{\"foo\":1,\"bar\":2}";
        String result = JsonUtils.replaceJsonKey(input, "oldKey", "newKey");
        assertEquals(
                gson.fromJson(expected, JsonObject.class),
                gson.fromJson(result, JsonObject.class)
        );
    }

    @Test
    void testReplaceJsonKey_arrayAtRoot() {
        String input = "[{\"oldKey\":1},{\"oldKey\":2}]";
        assertThrows(Exception.class, () -> JsonUtils.replaceJsonKey(input, "oldKey", "newKey"));
    }

    @Test
    void testReplaceJsonKey_emptyJson() {
        String input = "{}";
        String expected = "{}";
        String result = JsonUtils.replaceJsonKey(input, "oldKey", "newKey");
        assertEquals(expected, result);
    }
}