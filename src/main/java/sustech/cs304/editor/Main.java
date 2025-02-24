package sustech.cs304.editor;

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
        URL fxmlUrl0 = getClass().getResource(".");
        System.out.println(fxmlUrl0);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./resources/deme_c.fxml")));
        Scene scene = new Scene(root);
        //stage.setResizable(false); // 禁止放大缩小
        stage.setTitle("editor_UI");
        stage.setScene(scene);
        stage.show();
    }
}
