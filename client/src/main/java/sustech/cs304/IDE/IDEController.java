package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import sustech.cs304.pdfReader.pdfReaderController;
import sustech.cs304.terminal.JeditermController;

import java.io.IOException;
import java.util.Objects;

public class IDEController {
    @FXML
    public Button ThirdModeButton;

    @FXML
    public Button UserHomeButton;


    @FXML
    private AnchorPane backgroundPane;

    @FXML
    private AnchorPane editorPane;

    @FXML
    private AnchorPane profilePane;

    @FXML
    private FileTreeController fileTreeController;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private EditorController editorController;

    @FXML
    private pdfReaderController MYpdfReaderController;

    @FXML
    private JeditermController jeditermController;

    private Label welcomeLabel;

    @FXML
    private Hyperlink openLink;

    private String css;

    @FXML
    private void initialize() {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            backgroundPane.setPrefHeight(1048);
            backgroundPane.setLayoutY(-32);
        }
        menuBarController.setFileTreeController(fileTreeController);
        menuBarController.setEditorController(editorController);
        menuBarController.setIdeController(this);
        fileTreeController.setEditorController(editorController);
        fileTreeController.setMYpdfReaderController(MYpdfReaderController);
        editorController.setIdeController(this);
        editorController.setBackground("vs-dark");

        ThirdModeButton.setOnAction(event -> switchToClass());
        UserHomeButton.setOnAction(event -> switchToUserhome());

    }

    public void changeTheme(String theme) {
        editorController.setBackground(theme);
        editorController.setTheme(theme);
        Scene scene = backgroundPane.getScene();
        if (scene != null) {
            scene.getStylesheets().remove(css);
        }
        css = this.getClass().getResource("/css/style-" + theme + ".css").toExternalForm();
        scene.getStylesheets().add(css);
    }

    @FXML
    private void openFolder() {
        if (menuBarController != null) {
            menuBarController.openFolder();
        }
    }

    public void openEditor() {
        welcomeLabel.setVisible(false);
        openLink.setVisible(false);
        welcomeLabel.setManaged(false);
        openLink.setManaged(false);
    }

    @FXML
    public void openProfile() {

    }



    private void switchToClass() {
        try {
            // 加载新的FXML内容
            Parent newContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/class.fxml")));

            // 替换当前场景的内容
            Scene currentScene = ThirdModeButton.getScene();
            currentScene.setRoot(newContent);

            // 或者如果你只想替换部分内容
            // backgroundPane.getChildren().setAll(newContent);

        } catch (IOException e) {
            e.printStackTrace();
            // 错误处理...
        }
    }

    private void switchToUserhome() {
        try {
            // 加载新的FXML内容
            Parent newContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/UserHome.fxml")));

            // 替换当前场景的内容
            Scene currentScene = ThirdModeButton.getScene();
            currentScene.setRoot(newContent);

            // 或者如果你只想替换部分内容
            // backgroundPane.getChildren().setAll(newContent);

        } catch (IOException e) {
            e.printStackTrace();
            // 错误处理...
        }
    }

}
