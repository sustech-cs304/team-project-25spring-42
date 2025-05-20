package sustech.cs304;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sustech.cs304.controller.NoteController;
import sustech.cs304.entity.User;
import sustech.cs304.service.UserApi;
import sustech.cs304.service.UserApiImpl;
import sustech.cs304.utils.AlterUtils;

import java.net.URL;

public class App extends Application {
    public static User user;
    public static UserApi userApi = new UserApiImpl();
    public static Stage primaryStage;

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

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if ((event.isMetaDown() || event.isControlDown()) && event.getCode() == KeyCode.T) {
                NoteController.toggle(stage);
                event.consume();
            }
        });

        primaryStage = stage;
        stage.setTitle("AIDE");
        String initialCSS = this.getClass().getResource("/css/style-vs.css").toExternalForm();
        scene.getStylesheets().add(initialCSS);
        stage.setScene(scene);
        stage.show();
    }
}
