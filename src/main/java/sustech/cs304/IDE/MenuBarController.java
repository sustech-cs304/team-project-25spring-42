package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;

    private FileTreeController fileTreeController;

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

    public FileTreeController getFileTreeController() {
        return fileTreeController;
    }

}
