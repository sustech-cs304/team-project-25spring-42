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
        URL fxmlUrl = getClass().getResource("/fxml/login.fxml");
        // URL fxmlUrl = getClass().getResource("/fxml/IDE/IDE.fxml");
        if (fxmlUrl == null) {
            System.err.println("FXML file not found! Check the path and ensure the file is in the resources folder.");
            return;
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("AIDE");
        String initialCSS = this.getClass().getResource("/css/style-vs.css").toExternalForm();
        scene.getStylesheets().add(initialCSS);
        stage.setScene(scene);
        stage.show();
    }
}
