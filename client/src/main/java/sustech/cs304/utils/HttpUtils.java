package sustech.cs304.utils;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sustech.cs304.entity.Query;

public class HttpUtils {
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

    public static String getBaseUrl() {
        return "http://139.180.143.70:8080";
    }

    public static String getUserApiUrl() {
        return getBaseUrl() + "/self";
    }

    public static String getProjectApiUrl() {
        return getBaseUrl() + "/project";
    }

    public static String getFileApiUrl() {
        return getBaseUrl() + "/file";
    }
}


