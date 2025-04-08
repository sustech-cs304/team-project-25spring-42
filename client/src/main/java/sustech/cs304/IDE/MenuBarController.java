package sustech.cs304.IDE;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import sustech.cs304.utils.FileUtils;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;

    private String css;

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
        changeTheme("vs");
    }

    @FXML
    private void setColorVsDark() {
        changeTheme("vs-dark");
    }

    @FXML
    private void setColorHcBlack() {
        changeTheme("hc-black");
    }

    @FXML
    private void run() {
        File file = ideController.getEditorController().getCurrentFile();
        ideController.getJeditermController().open();
        if (file != null) {
            String filePath = file.getAbsolutePath();
            String extension = FileUtils.getExtension(file);
            ideController.getEditorController().savePage();

            try{
                if (extension.equals("java")) {
                    String command = "java" + " " + filePath;
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("py")) {
                    String command = "python" + " " + filePath;
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("c")) {
                    String command = "gcc" + " " + filePath + " -o " + file.getName().replace(".c", "");
                    ideController.getJeditermController().executeCommand(command);
                    command = "./" + file.getName().replace(".c", "");
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("sh")) {
                    String command = "bash" + " " + filePath;
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("js")) {
                    String command = "node" + " " + filePath;
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("cpp")) {
                    String command = "g++" + " " + filePath + " -o " + file.getName().replace(".cpp", "");
                    ideController.getJeditermController().executeCommand(command);
                    command = "./" + file.getName().replace(".cpp", "");
                    ideController.getJeditermController().executeCommand(command);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changeTheme(String theme) {
        ideController.getJeditermController().changeTheme(theme);
        ideController.getEditorController().setBackground(theme);
        ideController.getEditorController().setTheme(theme);
        ideController.changeImageColor(theme);
        Scene scene = menuBar.getScene();
        if (scene != null) {
            scene.getStylesheets().remove(this.css);
            this.css = this.getClass().getResource("/css/style-" + theme + ".css").toExternalForm();
            scene.getStylesheets().add(this.css);
        }
    }

    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }

}
