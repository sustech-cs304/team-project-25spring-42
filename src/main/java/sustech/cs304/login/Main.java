package sustech.cs304.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Loading FXML file...");
        URL fxmlUrl = getClass().getResource("/fxml/IDE/IDE.fxml");
        System.out.println("FXML URL: " + fxmlUrl); // Debugging: Check if the URL is null
        if (fxmlUrl == null) {
            System.err.println("FXML file not found! Check the path and ensure the file is in the resources folder.");
            return;
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlUrl));
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
