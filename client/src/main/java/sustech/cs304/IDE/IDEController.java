package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import sustech.cs304.pdfReader.pdfReaderController;
import sustech.cs304.terminal.JeditermController;

public class IDEController {

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
        menuBarController.setIdeController(this);
        fileTreeController.setIdeController(this);
        editorController.setIdeController(this);
        editorController.setBackground("vs-dark");
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

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    public FileTreeController getFileTreeController() {
        return fileTreeController;
    }

    public EditorController getEditorController() {
        return editorController;
    }

    public pdfReaderController getMYpdfReaderController() {
        return MYpdfReaderController;
    }

    public JeditermController getJeditermController() {
        return jeditermController;
    }
}

