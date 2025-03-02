package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;

    private FileTreeController fileTreeController;

    private EditorController editorController;

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

    public FileTreeController getFileTreeController() {
        return fileTreeController;
    }

    @FXML
    private void setColorVs() {
        if (editorController != null) {
            this.changeTheme("vs");
        }
    }

    @FXML
    private void setColorVsDark() {
        this.changeTheme("vs-dark");
    }

    @FXML
    private void setColorHcBlack() {
        if (editorController != null) {
            this.changeTheme("hc-black");
        }
    }

    public void changeTheme(String theme) {
        editorController.setTheme(theme);
        switch (theme) {
            // 增加代码以切换除编辑界面外整体颜色
            case "vs":

                break;
            case "vs-dark":

                break;
            case "hc-black":

                break;
        }
    }
}
