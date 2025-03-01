package sustech.cs304.login;

import okhttp3.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import javafx.fxml.FXML;

public class LoginController {
    private static final String SERVER_URL = "http://107.173.91.140:8080/auth/github";

    @FXML
    public void loginWithGitHub() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_URL).build();
        Response response = client.newCall(request).execute();
        
        String loginUrl = response.body().string();
        response.close();
        System.out.println(loginUrl);
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI.create(loginUrl));
        } else {
            System.out.println("Open manually: " + loginUrl);
        }
    }
}

