package sustech.cs304.utils; 

import okhttp3.*;
import sustech.cs304.login.Elements.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.time.LocalDateTime;
import com.google.gson.Gson;
import sustech.cs304.userhome.UserServerSide;

public final class ServerUtils{
    private ServerUtils() {
        throw new AssertionError("Invalid class");
    }
    public static UserServerSide serverLoadUserData(String userId){
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/allInfo")
                .newBuilder()
                .addQueryParameter("platformId", userId)
                .build();
        System.out.println("URL: " + url);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("Response: " + responseBody);
                Gson gson = new Gson();
                UserServerSide user = gson.fromJson(responseBody, UserServerSide.class);
                return user;
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return null;
        }
    }  
}
