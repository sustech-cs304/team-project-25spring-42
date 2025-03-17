package sustech.cs304;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        URL fxmlUrl = getClass().getResource("/fxml/IDE/IDE.fxml");
        //URL fxmlUrl = getClass().getResource("/fxml/IDE/IDE.fxml");
        if (fxmlUrl == null) {
            System.err.println("FXML file not found! Check the path and ensure the file is in the resources folder.");
            return;
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("AIDE");
        String loginCSS = this.getClass().getResource("/css/login.css").toExternalForm();
        scene.getStylesheets().add(loginCSS);
        String initialBgCSS = this.getClass().getResource("/css/style-vs-dark.css").toExternalForm();
        scene.getStylesheets().add(initialBgCSS);
        stage.setScene(scene);
        stage.show();
    }
}

// import javafx.application.Application;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.stage.Stage;
// import javafx.scene.web.WebView;
// import javafx.scene.web.WebEngine;
//
// public class App extends Application {
//     private static final String CLIENT_ID = "your_client_id";
//     private static final String REDIRECT_URI = "http://localhost:8080/callback";
//     private static final String AUTH_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&scope=read:user";
//
//     @Override
//     public void start(Stage stage) {
//         // 创建 GitHub 按钮
//         Button githubButton = new Button("Sign in with GitHub");
//         githubButton.setFont(new Font("Arial", 16));
//         githubButton.setTextFill(Color.WHITE);
//         githubButton.setStyle("-fx-background-color: #24292e; -fx-padding: 10px 20px; -fx-background-radius: 5px;");
//         
//         // GitHub Logo
//         ImageView githubIcon = new ImageView(new Image("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png"));
//         githubIcon.setFitWidth(20);
//         githubIcon.setFitHeight(20);
//         
//         githubButton.setGraphic(githubIcon);
//         githubButton.setOnAction(e -> openGitHubLogin());
//
//         // 布局
//         VBox root = new VBox(20, githubButton);
//         root.setAlignment(Pos.CENTER);
//         root.setStyle("-fx-background-color: #ffffff;");
//
//         Scene scene = new Scene(root, 400, 300);
//         stage.setTitle("GitHub Sign-In");
//         stage.setScene(scene);
//         stage.show();
//     }
//
//     private void openGitHubLogin() {
//         Stage webStage = new Stage();
//         WebView webView = new WebView();
//         WebEngine webEngine = webView.getEngine();
//         webEngine.load(AUTH_URL);
//
//         Scene webScene = new Scene(webView, 600, 400);
//         webStage.setScene(webScene);
//         webStage.setTitle("GitHub Authentication");
//         webStage.show();
//     }
//
//     public static void main(String[] args) {
//         launch();
//     }
// }
