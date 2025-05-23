package sustech.cs304.controller;

import java.io.File;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import sustech.cs304.utils.FileUtils;

public class SettingsController {

    @FXML private TextField javaHomeField;
    @FXML private TextField pythonPathField;
    @FXML private TextField chatGptUrlField;
    @FXML private TextField chatGptKeyField;
    @FXML private TextField deepSeekUrlField;
    @FXML private TextField deepSeekKeyField;
    @FXML private TextField geminiUrlField;
    @FXML private TextField geminiKeyField;

    @FXML
    private void initialize() {
        // Load existing settings if available
        javaHomeField.setText(System.getProperty("java.home"));
        pythonPathField.setText(System.getenv("PYTHONPATH"));
        FileUtils.modifyEnvFile("JAVA_HOME", javaHomeField.getText());
        FileUtils.modifyEnvFile("PYTHONPATH", pythonPathField.getText());
        chatGptUrlField.setText(Dotenv.load().get("OPENAI_API_URL"));
        chatGptKeyField.setText(Dotenv.load().get("OPENAI_API_KEY"));
        deepSeekUrlField.setText(Dotenv.load().get("DEEPSEEK_API_URL"));
        deepSeekKeyField.setText(Dotenv.load().get("DEEPSEEK_API_KEY"));
        geminiUrlField.setText(Dotenv.load().get("GEMINI_API_URL"));
        geminiKeyField.setText(Dotenv.load().get("GEMINI_API_KEY"));
    }

    @FXML
    private void handleSave() {
        String javaHome = javaHomeField.getText();
        String pythonPath = pythonPathField.getText();
        String chatGptUrl = chatGptUrlField.getText();
        String chatGptKey = chatGptKeyField.getText();
        String deepSeekUrl = deepSeekUrlField.getText();
        String deepSeekKey = deepSeekKeyField.getText();
        String geminiUrl = geminiUrlField.getText();
        String geminiKey = geminiKeyField.getText();

        FileUtils.modifyEnvFile("JAVA_HOME", javaHome);
        FileUtils.modifyEnvFile("PYTHONPATH", pythonPath);
        FileUtils.modifyEnvFile("OPENAI_API_URL", chatGptUrl);
        FileUtils.modifyEnvFile("OPENAI_API_KEY", chatGptKey);
        FileUtils.modifyEnvFile("DEEPSEEK_API_URL", deepSeekUrl);
        FileUtils.modifyEnvFile("DEEPSEEK_API_KEY", deepSeekKey);
        FileUtils.modifyEnvFile("GEMINI_API_URL", geminiUrl);
        FileUtils.modifyEnvFile("GEMINI_API_KEY", geminiKey);
    }

    @FXML
    private void browseJavaHome() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Java Home Directory");
        File dir = chooser.showDialog(javaHomeField.getScene().getWindow());
        if (dir != null) {
            javaHomeField.setText(dir.getAbsolutePath());
        }
    }

    @FXML
    private void browsePythonPath() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Python Path");
        File dir = chooser.showDialog(pythonPathField.getScene().getWindow());
        if (dir != null) {
            pythonPathField.setText(dir.getAbsolutePath());
        }
    }
}
