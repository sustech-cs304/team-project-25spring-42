package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuBarController {

    @FXML
    private MenuItem newFile;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveFile;

    @FXML
    private MenuItem saveAsFile;

    @FXML
    private MenuItem closeFile;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem undo;

    @FXML
    private MenuItem redo;

    @FXML
    private MenuItem cut;

    @FXML
    private MenuItem copy;

    @FXML
    private MenuItem paste;

    @FXML
    private MenuItem selectAll;

    @FXML
    private MenuItem find;

    @FXML
    private MenuItem replace;

    @FXML
    private MenuItem goTo;

    @FXML
    private MenuItem run;

    @FXML
    private MenuItem compile;

    @FXML
    private MenuItem build;

    @FXML
    private MenuItem debug;

    @FXML
    private MenuItem options;

    @FXML
    private MenuItem help;

    @FXML
    private MenuItem about;

    @FXML
    private TabPane tabPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private void newFile() {
        Tab tab = new Tab("New File");
        tabPane.getTabs().add(tab);
    }

    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Files", "*.java"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSS Files", "*.css"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JavaScript Files", "*.js"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL Files", "*.sql"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Python Files", "*.py"));
    }
}
