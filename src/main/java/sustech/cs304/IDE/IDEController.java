package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class IDEController {

    @FXML
    private AnchorPane backgroundPane;

    @FXML
    private FileTreeController fileTreeController;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private EditorController editorController;

    @FXML
    private void initialize() {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            backgroundPane.setPrefHeight(1048);
            backgroundPane.setLayoutY(-32);
        }
        menuBarController.setFileTreeController(fileTreeController);
        fileTreeController.setEditorController(editorController);
    }
}
