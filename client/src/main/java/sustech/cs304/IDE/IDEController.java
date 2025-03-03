package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import sustech.cs304.pdfReader.pdfReaderController;

public class IDEController {

    @FXML
    private AnchorPane backgroundPane;

    @FXML
    private AnchorPane editorPane;

    @FXML
    private FileTreeController fileTreeController;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private EditorController editorController;

    @FXML
    private pdfReaderController MYpdfReaderController;

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
        editorController.setBackground("hc-black");
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
}
