package sustech.cs304.utils; 

import okhttp3.*;
import sustech.cs304.entity.UserServerSide;

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
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return new UserServerSide("test010100101", "tester" , "https://test.com" , "2025-04-07T18:11:20.059626" , "2025-04-08T14:37:04.979725072" , "1231123212" , "test@test.com", "test bio");
        }
    }  
    
    public static String getUserName(String userId){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/getUserName")
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
                UserResponse userName = gson.fromJson(responseBody, UserResponse.class);
                return userName.getContent();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return "testUser";
        }
    }

    public static String getUserAvatar(String userId){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/getUserAvatar")
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
                UserResponse userAvatar = gson.fromJson(responseBody, UserResponse.class);
                return userAvatar.getContent();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return "https://test.com";
        }
    }

    public static String getUserBio(String userId){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/getUserBio")
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
                UserResponse userBio = gson.fromJson(responseBody, UserResponse.class);
                return userBio.getContent();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return "test bio";
        }
    }

    public static String getUserEmail(String userId){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/getUserEmail")
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
                UserResponse userEmail = gson.fromJson(responseBody, UserResponse.class);
                return userEmail.getContent();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return "test@test.com";
        }
    }

    public static String getUserRegisterTime(String userId){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/getUserRegisterTime")
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
                UserResponse userRegisterTime = gson.fromJson(responseBody, UserResponse.class);
                return userRegisterTime.getContent();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return "2025-04-07T18:11:20.059626";
        }
    }
    
    public static String getUserLastLoginTime(String userId){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/getUserLastLoginTime")
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
                UserResponse userLastLoginTime = gson.fromJson(responseBody, UserResponse.class);
                return userLastLoginTime.getContent();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return "2025-04-08T14:37:04.979725072";
        }
    }

    public static boolean setUserName(String userId, String newUserName){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/setUserName")
                .newBuilder()
                .addQueryParameter("platformId", userId)
                .addQueryParameter("newUserName", newUserName)
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
                SetResponse userWhetherResetUserName = gson.fromJson(responseBody, SetResponse.class);
                return userWhetherResetUserName.getResult();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return false;
        }
    }

    public static boolean setUserAvatar(String userId, String newUserAvatarUrl){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/setUserAvatar")
                .newBuilder()
                .addQueryParameter("platformId", userId)
                .addQueryParameter("newAvatarUrl", newUserAvatarUrl)
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
                SetResponse userWhetherResetUserAvatar = gson.fromJson(responseBody, SetResponse.class);
                return userWhetherResetUserAvatar.getResult();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean setUserPhoneNumber(String userId, String newUserPhoneNumber){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/setUserPhoneNumber")
                .newBuilder()
                .addQueryParameter("platformId", userId)
                .addQueryParameter("newPhoneNumber", newUserPhoneNumber)
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
                SetResponse userWhetherResetUserPhoneNumber = gson.fromJson(responseBody, SetResponse.class);
                return userWhetherResetUserPhoneNumber.getResult();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return false;
        }        
    }

    public static boolean setUserBio(String userId, String newUserBio){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/setUserBio")
                .newBuilder()
                .addQueryParameter("platformId", userId)
                .addQueryParameter("newBio", newUserBio)
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
                SetResponse userWhetherResetUserBio = gson.fromJson(responseBody, SetResponse.class);
                return userWhetherResetUserBio.getResult();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return false;
        }        
    }

    public static boolean setUserEmail(String userId, String newUserEmail){
        OkHttpClient client = new OkHttpClient();         
        HttpUrl url = HttpUrl.parse("http://139.180.143.70:8080/self/setUserEmail")
                .newBuilder()
                .addQueryParameter("platformId", userId)
                .addQueryParameter("newEmail", newUserEmail)
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
                SetResponse userWhetherResetUserEmail = gson.fromJson(responseBody, SetResponse.class);
                return userWhetherResetUserEmail.getResult();
            } else {
                System.err.println("Failed to load user data: " + response.message());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while loading user data: " + e.getMessage());
            return false;
        }        
    }

}
class UserResponse {
    private String content;
    UserResponse(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
}
class SetResponse {
    private boolean result;
    SetResponse(boolean result) {
        this.result = result;
    }
    public boolean getResult() {
        return result;
    }
}
