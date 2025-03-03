package sustech.cs304.pdfReader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(getClass().getResource("/fxml/pdfReader.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pdfReader.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("PDF Reader Demo");
        stage.show();
    }
}