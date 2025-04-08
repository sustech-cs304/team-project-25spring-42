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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import kotlin.text.Charsets;

public class JeditermController {
    @FXML 
    private AnchorPane terminalPane;
    @FXML
    private AnchorPane terminalBackPane;
    @FXML
    private ImageView xmarkImage;

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
                Image xmarkImage = new Image(getClass().getResourceAsStream("/img/xmark-black.png"));
                this.xmarkImage.setImage(xmarkImage);
                terminalBackPane.setStyle("-fx-background-color: #f5f5f5;");
                settingsProvider.setBackgroundColor(new TerminalColor(245, 245, 245));
                settingsProvider.setForegroundColor(new TerminalColor(30, 30, 30));
                settingsProvider.setDefaultStyle(new TerminalColor(30, 30, 30), new TerminalColor(245, 245, 245));
                break;
            case "vs-dark":
                xmarkImage = new Image(getClass().getResourceAsStream("/img/xmark-white.png"));
                this.xmarkImage.setImage(xmarkImage);
                terminalBackPane.setStyle("-fx-background-color: #1e1e1e;");
                settingsProvider.setBackgroundColor(new TerminalColor(30, 30, 30));
                settingsProvider.setForegroundColor(new TerminalColor(245, 245, 245));
                settingsProvider.setDefaultStyle(new TerminalColor(245, 245, 245), new TerminalColor(30, 30, 30));
                break;
            case "hc-black":
                xmarkImage = new Image(getClass().getResourceAsStream("/img/xmark-white.png"));
                this.xmarkImage.setImage(xmarkImage);
                terminalBackPane.setStyle("-fx-background-color: #000000;");
                settingsProvider.setBackgroundColor(new TerminalColor(0, 0, 0));
                settingsProvider.setForegroundColor(new TerminalColor(245, 245, 245));
                settingsProvider.setDefaultStyle(new TerminalColor(245, 245, 245), new TerminalColor(0, 0, 0));
                break;
            default:
                throw new IllegalArgumentException("Unknown theme: " + theme);
        }
        this.widget.getTerminalPanel().repaint();

    }

    public void close() {
        terminalBackPane.setVisible(false);
    }

    public void open() {
        terminalBackPane.setVisible(true);
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
    private TerminalColor foregroundColor = new TerminalColor(30, 30, 30);
    private TerminalColor backgroundColor = new TerminalColor(245, 245, 245);
    private TextStyle defaultStyle = new TextStyle(foregroundColor, backgroundColor);

    @Override
    public TextStyle getDefaultStyle() {
        return defaultStyle;
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

    public void setDefaultStyle(TerminalColor foreground, TerminalColor background) {
        this.defaultStyle = new TextStyle(foreground, background);
    }
}
