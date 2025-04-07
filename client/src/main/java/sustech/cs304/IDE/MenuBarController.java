package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;

    private IDEController ideController;

    @FXML
    private void initialize() {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            menuBar.setUseSystemMenuBar(true);
        }
    }

    @FXML
    public void openFolder() {
        ideController.getFileTreeController().handleSelectFolder();
    }

    @FXML
    public void savePage() {
        ideController.getEditorController().savePage();
    }

    @FXML
    public void saveAll() {
        ideController.getEditorController().saveAll();
    }

    @FXML
    private void openTerminal() {
        ideController.getJeditermController().open();
    }

    @FXML
    private void setColorVs() {
        ideController.changeTheme("vs");
    }

    @FXML
    private void setColorVsDark() {
        ideController.changeTheme("vs-dark");
    }

    @FXML
    private void setColorHcBlack() {
        ideController.changeTheme("hc-black");
    }

    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }

}
