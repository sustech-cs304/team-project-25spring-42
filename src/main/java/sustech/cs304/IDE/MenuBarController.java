package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private void initialize() {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            menuBar.setUseSystemMenuBar(true);
        }
    }

}
