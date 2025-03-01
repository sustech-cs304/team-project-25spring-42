package sustech.cs304.login;

import okhttp3.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LoginController {
    private static final String SERVER_URL = "http://107.173.91.140:8080/auth/github";

    @FXML
    private StackPane loginPane;

    @FXML
    public void loginWithGitHub() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_URL).build();
        Response response = client.newCall(request).execute();
        
        String loginUrl = response.body().string();
        response.close();
        System.out.println(loginUrl);

        switchToAuthPage(loginUrl);

        // if (Desktop.isDesktopSupported()) {
        //     Desktop.getDesktop().browse(URI.create(loginUrl));
        // } else {
        //     System.out.println("Open manually: " + loginUrl);
        // }
    }

    private void switchToAuthPage(String loginUrl) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(loginUrl);

        StackPane authPane = new StackPane(webView);
        loginPane.getChildren().setAll(authPane);
    }
}

