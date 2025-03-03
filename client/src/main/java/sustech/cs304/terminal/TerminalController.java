package sustech.cs304.terminal;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TerminalController {

    @FXML
    private TextArea terminalArea; // 终端显示区域
    @FXML
    private TextField inputField;  // 输入框

    private Process shellProcess;  // Shell 进程
    private BufferedWriter shellInput; // Shell 输入流
    private BufferedReader shellOutput; // Shell 输出流
    private BufferedReader shellError;  // Shell 错误流

    @FXML
    public void initialize() {
        // 启动 Shell 进程
        startShell();
    }

    @FXML
    private void handleInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String command = inputField.getText();
            executeCommand(command);
            inputField.clear();
        }
    }

    private void startShell() {
        try {
            // 启动 zsh 进程
            ProcessBuilder processBuilder = new ProcessBuilder("zsh");
            processBuilder.redirectErrorStream(true);
            shellProcess = processBuilder.start();

            // 获取输入输出流
            shellInput = new BufferedWriter(new OutputStreamWriter(shellProcess.getOutputStream()));
            shellOutput = new BufferedReader(new InputStreamReader(shellProcess.getInputStream()));
            shellError = new BufferedReader(new InputStreamReader(shellProcess.getErrorStream()));

            // 启动线程读取 Shell 输出
            new Thread(this::readShellOutput).start();


            updatePrompt();
        } catch (IOException e) {
            terminalArea.appendText("Failed to start shell: " + e.getMessage() + "\n");
        }
    }

    private void executeCommand(String command) {
        try {
            // display what user's input
            terminalArea.appendText("> " + command + "\n");

            // send command to Shell
            shellInput.write(command + "\n");
            shellInput.flush();
        } catch (IOException e) {
            terminalArea.appendText("Error sending command to shell: " + e.getMessage() + "\n");
        }
    }

    private void readShellOutput() {
        try {
            String line;
            while ((line = shellOutput.readLine()) != null) {
                // 在 JavaFX 主线程中更新 UI ??
                String finalLine = line;
                javafx.application.Platform.runLater(() -> {
                    terminalArea.appendText(finalLine + "\n");

                });
            }
            updatePrompt();
        } catch (IOException e) {
            javafx.application.Platform.runLater(() -> terminalArea.appendText("Error reading shell output: " + e.getMessage() + "\n"));
        }
    }

    private void updatePrompt() {
        //get current dir
        String currentDir = System.getProperty("user.dir");

        javafx.application.Platform.runLater(() -> terminalArea.appendText(currentDir + " > "));
    }
}