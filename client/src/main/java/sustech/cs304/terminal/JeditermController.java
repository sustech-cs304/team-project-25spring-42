package sustech.cs304.terminal;

import com.techsenger.jeditermfx.ui.JediTermFxWidget;
import com.techsenger.jeditermfx.ui.settings.DefaultSettingsProvider;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;

import sustech.cs304.IDE.IDEController;
import sustech.cs304.terminal.pty.PtyProcessTtyConnector;

import com.techsenger.jeditermfx.core.TerminalColor;
import com.techsenger.jeditermfx.core.TextStyle;
import com.techsenger.jeditermfx.core.TtyConnector;
import com.techsenger.jeditermfx.core.util.Platform;
import com.techsenger.jeditermfx.ui.DefaultHyperlinkFilter;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import kotlin.text.Charsets;

public class JeditermController {
    @FXML 
    private AnchorPane terminalPane;
    @FXML
    private AnchorPane backPane;

    private IDEController ideController;

    private JediTermFxWidget widget;
    private CustomSettingsProvider settingsProvider;

    @FXML
    public void initialize() {
        widget =  createTerminalWidget();
        widget.getPane().prefWidthProperty().bind(terminalPane.widthProperty());
        widget.getPane().prefHeightProperty().bind(terminalPane.heightProperty());
        widget.addListener(terminalWidget -> {
            widget.close();
        });
        terminalPane.getChildren().add(widget.getPane());
    }

    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }

    public void changeTheme(String theme) {
        switch (theme) {
            case "vs":
                settingsProvider.setBackgroundColor(new TerminalColor(255, 255, 255));
                settingsProvider.setForegroundColor(new TerminalColor(0, 0, 0));
                break;
            case "vs-dark":
                settingsProvider.setBackgroundColor(new TerminalColor(0, 0, 0));
                settingsProvider.setForegroundColor(new TerminalColor(255, 255, 255));
                break;
            case "hc-black":
                settingsProvider.setBackgroundColor(new TerminalColor(0, 0, 0));
                settingsProvider.setForegroundColor(new TerminalColor(255, 255, 255));
                break;
            default:
                throw new IllegalArgumentException("Unknown theme: " + theme);
        }
    }

    public void close() {
        backPane.setVisible(false);
    }

    public void open() {
        backPane.setVisible(true);
    }

    public void executeCommand(String command) throws Exception {
        widget.getTtyConnector().write(command + "\n");
    }

    private JediTermFxWidget createTerminalWidget() {
        settingsProvider = new CustomSettingsProvider();
        JediTermFxWidget widget = new JediTermFxWidget(80, 24, this.settingsProvider);
        widget.setTtyConnector(createTtyConnector());
        widget.addHyperlinkFilter(new DefaultHyperlinkFilter());
        widget.start();
        return widget;
    }

    private TtyConnector createTtyConnector() {
        try {
            String[] command;
            Map<String, String> envs = new HashMap<String, String>(System.getenv());
            if (Platform.isWindows()) {
                command = new String[]{"powershell.exe"};
            } else {
                String shell = (String) envs.get("SHELL");
                if (shell == null) {
                    shell = "/bin/bash";
                }
                if (Platform.isMacOS()) {
                    command = new String[]{shell, "--login"};
                } else {
                    command = new String[]{shell};
                }
            }
            if (Platform.isWindows()) {
                envs.put("TERM", "xterm-256color");
            } else {
                envs.put("LC_CTYPE", Charsets.UTF_8.name());
                envs.put("PATH", System.getenv("PATH"));
            }
            PtyProcess process = new PtyProcessBuilder()
                    .setCommand(command)
                    .setEnvironment(envs)
                    .start();
            return new PtyProcessTtyConnector(process, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

final class CustomSettingsProvider extends DefaultSettingsProvider {
    private TerminalColor foregroundColor = new TerminalColor(255, 255, 255);
    private TerminalColor backgroundColor = new TerminalColor(0, 0, 0);

    @Override
    public TextStyle getDefaultStyle() {
        return new TextStyle(foregroundColor, backgroundColor);
    }

    @Override
    public TerminalColor getDefaultBackground() {
        return backgroundColor;
    }

    @Override
    public TerminalColor getDefaultForeground() {
        return foregroundColor;
    }

    public void setBackgroundColor(TerminalColor color) {
        this.backgroundColor = color;
    }

    public void setForegroundColor(TerminalColor color) {
        this.foregroundColor = color;
    }
}
