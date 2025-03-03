package sustech.cs304.login;


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

public class LoginController {
    private static final String SERVER_URL_Github = "http://107.173.91.140:8080/auth/github";
    private static final String SERVER_URL_X = "http://107.173.91.140:8080/auth/x";
    private static final String SERVER_URL_Google = "http://107.173.91.140:8080/auth/google";

    @FXML
    private StackPane loginPane;

    @FXML
    public void loginWithGitHub() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_URL_Github).build();
        Response response = client.newCall(request).execute();

        String loginUrl = response.body().string();
        int state = getState(loginUrl);
        response.close();

        //switchToAuthPage(loginUrl);

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI.create(loginUrl));
        } else {
            System.out.println("Open manually: " + loginUrl);
        }
        AsyncAuthChecker authChecker = new AsyncAuthChecker(state);
        if (authChecker.checkAuth()) {
            switchToIDEPage();
        } else {
            return;
        }
    }

    @FXML
    public void loginWithX() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_URL_X).build();
        Response response = client.newCall(request).execute();
        
        String loginUrl = response.body().string();
        response.close();
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI.create(loginUrl));
        } else {

            System.out.println("Open manually: " + loginUrl);
        }
    }
     @FXML
    public void loginWithGoogle() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_URL_Google).build();
        Response response = client.newCall(request).execute();
        
        String loginUrl = response.body().string();
        int state = getState(loginUrl);
        response.close();
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI.create(loginUrl));
        } else {
            System.out.println("Open manually: " + loginUrl);
        }
        AsyncAuthChecker authChecker = new AsyncAuthChecker(state);
        if (authChecker.checkAuth()) {
            switchToIDEPage();
        } else {
            return;
        }
    }
    
    public static int getState(String loginUrl){
        String[] parts = loginUrl.split("=");
        String state = parts[parts.length-1];
        return Integer.parseInt(state);
    }

    private void switchToAuthPage(String loginUrl) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(loginUrl);

        StackPane authPane = new StackPane(webView);
        loginPane.getChildren().setAll(authPane);
    }

    private void switchToIDEPage() throws IOException {
        Scene scene = loginPane.getScene();
        Stage stage = (Stage) scene.getWindow();
        URL fxmlUrl = getClass().getResource("/fxml/IDE/IDE.fxml");
        if (fxmlUrl == null) {
            System.err.println("FXML file not found! Check the path and ensure the file is in the resources folder.");
            return;
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        scene.setRoot(loader.load());
        stage.sizeToScene();
        stage.centerOnScreen();

    }

    
}

