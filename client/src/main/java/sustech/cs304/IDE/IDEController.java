package sustech.cs304.IDE;

import javafx.fxml.FXML;
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

    @FXML
    private void initialize() {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            backgroundPane.setPrefHeight(1048);
            backgroundPane.setLayoutY(-32);
        }

        menuBarController.setFileTreeController(fileTreeController);
        menuBarController.setEditorController(editorController);
        fileTreeController.setEditorController(editorController);
        fileTreeController.setMYpdfReaderController(MYpdfReaderController);
    }
}
