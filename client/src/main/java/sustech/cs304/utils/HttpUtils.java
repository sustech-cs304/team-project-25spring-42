package sustech.cs304.utils;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sustech.cs304.entity.Query;

/**
 * Utility class for performing HTTP operations (GET, POST) using OkHttp.
 * Provides convenient methods for making requests to APIs with various content types and URL building.
 * All methods are static and thread-safe for use in application-level network access.
 */
public class HttpUtils {

    /**
     * Executes a HTTP GET request with query parameters.
     *
     * @param apiUrl  The API base URL segment (e.g., "/api").
     * @param apiPath The API endpoint path (e.g., "/users").
     * @param queries Array of Query objects representing key-value pairs for URL query parameters.
     * @return The OkHttp {@link Response} object from the request.
     * @throws Exception if the request fails or an error occurs.
     */
    public static Response get(String apiUrl, String apiPath, Query[] queries) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String urlString = getBaseUrl() + apiUrl + apiPath; 
        HttpUrl.Builder urlBuilder = HttpUrl.parse(urlString).newBuilder();
        for (Query query : queries) {
            urlBuilder.addQueryParameter(query.getKey(), query.getValue());
        }
        HttpUrl url = urlBuilder.build();
        Request request = new Request.Builder()
            .url(url)
            .addHeader("Accept", "application/json")
            .build();

        return client.newCall(request).execute();
    }

    /**
     * Executes a HTTP POST request with a JSON request body.
     *
     * @param apiUrl  The API base URL segment (e.g., "/api").
     * @param apiPath The API endpoint path (e.g., "/users").
     * @param json    The JSON string to send as the request body.
     * @return The OkHttp {@link Response} object from the request.
     * @throws Exception if the request fails or an error occurs.
     */
    public static Response post(String apiUrl, String apiPath, String json) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String urlString = getBaseUrl() + apiUrl + apiPath; 
        HttpUrl.Builder urlBuilder = HttpUrl.parse(urlString).newBuilder();
        HttpUrl url = urlBuilder.build();
        Request request = new Request.Builder()
            .url(url)
            .post(okhttp3.RequestBody.create(json, okhttp3.MediaType.parse("application/json")))
            .addHeader("Accept", "application/json")
            .build();

        return client.newCall(request).execute();
    }

    /**
     * Executes a HTTP POST request with a form-encoded request body and appropriate headers.
     *
     * @param apiUrl   The API base URL segment (e.g., "/api").
     * @param apiPath  The API endpoint path (e.g., "/users").
     * @param formBody The OkHttp {@link RequestBody} for form data.
     * @return The OkHttp {@link Response} object from the request.
     * @throws Exception if the request fails or an error occurs.
     */
    public static Response postForm(String apiUrl, String apiPath, RequestBody formBody) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String urlString = getBaseUrl() + apiUrl + apiPath;
        HttpUrl url = HttpUrl.parse(urlString).newBuilder().build();

        Request request = new Request.Builder()
            .url(url)
            .post(formBody)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/x-www-form-urlencoded") // 表单类型
            .build();

        return client.newCall(request).execute();
    }

    /**
     * Executes a HTTP POST request with a form-encoded request body, without setting explicit headers.
     *
     * @param apiUrl   The API base URL segment (e.g., "/api").
     * @param apiPath  The API endpoint path (e.g., "/users").
     * @param formBody The OkHttp {@link RequestBody} for form data.
     * @return The OkHttp {@link Response} object from the request.
     * @throws Exception if the request fails or an error occurs.
     */
    public static Response postForm2(String apiUrl, String apiPath, RequestBody formBody) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String urlString = getBaseUrl() + apiUrl + apiPath;
        HttpUrl url = HttpUrl.parse(urlString).newBuilder().build();

        Request request = new Request.Builder()
            .url(url)
            .post(formBody)
            // .addHeader("Accept", "application/json")
            // .addHeader("Content-Type", "application/x-www-form-urlencoded") // 表单类型
            .build();

        return client.newCall(request).execute();
    }


    /**
     * Returns the base server URL for API calls.
     *
     * @return The base API URL as a String.
     */
    public static String getBaseUrl() {
        return ServerConfig.SERVER_URL;
    }

    /**
     * Returns the API URL for user-related endpoints.
     *
     * @return The user API URL as a String.
     */
    public static String getUserApiUrl() {
        return getBaseUrl() + "/self";
    }

    /**
     * Returns the API URL for project-related endpoints.
     *
     * @return The project API URL as a String.
     */
    public static String getProjectApiUrl() {
        return getBaseUrl() + "/project";
    }

    /**
     * Returns the API URL for file-related endpoints.
     *
     * @return The file API URL as a String.
     */
    public static String getFileApiUrl() {
        return getBaseUrl() + "/file";
    }
}


