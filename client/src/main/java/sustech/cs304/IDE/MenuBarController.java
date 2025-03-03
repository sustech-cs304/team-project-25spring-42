package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;

    private FileTreeController fileTreeController;

    private EditorController editorController;

    private IDEController ideController;

    @FXML
    private void initialize() {
        fileTreeController = new FileTreeController();
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            menuBar.setUseSystemMenuBar(true);
        }
    }

    @FXML
    private void openFolder() {
        fileTreeController.handleSelectFolder();
    }

    public void setFileTreeController(FileTreeController fileTreeController) {
        this.fileTreeController = fileTreeController;
    }

    public void setEditorController(EditorController editorController) {
        this.editorController = editorController;
    }

    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }

    public FileTreeController getFileTreeController() {
        return fileTreeController;
    }

    @FXML
    private void setColorVs() {
        if (editorController != null) {
            ideController.changeTheme("vs");
        }
    }

    @FXML
    private void setColorVsDark() {
        if (editorController != null) {
            ideController.changeTheme("vs-dark");
        }
    }

    @FXML
    private void setColorHcBlack() {
        if (editorController != null) {
            ideController.changeTheme("hc-black");
        }
    }
}
