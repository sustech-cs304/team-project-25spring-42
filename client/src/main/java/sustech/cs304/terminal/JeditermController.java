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
import javafx.scene.input.MouseEvent;
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

    private JediTermFxWidget vsWidget;
    private JediTermFxWidget vsDarkWidget;
    private JediTermFxWidget hcBlackWidget;

    @FXML
    public void initialize() {
        vsWidget =  createTerminalWidget("vs");
        vsDarkWidget = createTerminalWidget("vs-dark");
        hcBlackWidget = createTerminalWidget("hc-black");

        vsWidget.getPane().prefWidthProperty().bind(terminalPane.widthProperty());
        vsWidget.getPane().prefHeightProperty().bind(terminalPane.heightProperty());
        vsDarkWidget.getPane().prefWidthProperty().bind(terminalPane.widthProperty());
        vsDarkWidget.getPane().prefHeightProperty().bind(terminalPane.heightProperty());
        hcBlackWidget.getPane().prefWidthProperty().bind(terminalPane.widthProperty());
        hcBlackWidget.getPane().prefHeightProperty().bind(terminalPane.heightProperty());

        vsWidget.addListener(terminalWidget -> {
            vsWidget.close();
        });
        vsDarkWidget.addListener(terminalWidget -> {
            vsDarkWidget.close();
        });
        hcBlackWidget.addListener(terminalWidget -> {
            hcBlackWidget.close();
        });

        terminalPane.getChildren().add(vsWidget.getPane());
    }

    @FXML
    private void checkIfDragging(MouseEvent event) {
        ideController.checkIfDragging(event);
    } 

    @FXML
    private void dragTerminal(MouseEvent event) {
        ideController.dragTerminal(event);
    }

    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }

    public void changeTheme(String theme) {
        try {
            switch (theme) {
                case "vs":
                    widget = vsWidget;
                    executeCommand("clear");
                    Image xmarkImage = new Image(getClass().getResourceAsStream("/img/xmark-black.png"));
                    this.xmarkImage.setImage(xmarkImage);
                    terminalBackPane.setStyle("-fx-background-color: #f5f5f5;");
                    terminalPane.getChildren().clear();
                    terminalPane.getChildren().add(vsWidget.getPane());
                    break;
                case "vs-dark":
                    widget = vsDarkWidget;
                    executeCommand("clear");
                    xmarkImage = new Image(getClass().getResourceAsStream("/img/xmark-white.png"));
                    this.xmarkImage.setImage(xmarkImage);
                    terminalBackPane.setStyle("-fx-background-color: #1e1e1e;");
                    terminalPane.getChildren().clear();
                    terminalPane.getChildren().add(vsDarkWidget.getPane());
                    break;
                case "hc-black":
                    widget = hcBlackWidget;
                    executeCommand("clear");
                    xmarkImage = new Image(getClass().getResourceAsStream("/img/xmark-white.png"));
                    this.xmarkImage.setImage(xmarkImage);
                    terminalBackPane.setStyle("-fx-background-color: #000000;");
                    terminalPane.getChildren().clear();
                    terminalPane.getChildren().add(hcBlackWidget.getPane());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown theme: " + theme);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        ideController.closeTerminal();
    }

    public void open() {
        ideController.openTerminal();
    }

    public void executeCommand(String command) throws Exception {
        widget.getTtyConnector().write(command + "\n");
    }

    private JediTermFxWidget createTerminalWidget(String theme) {
        DefaultSettingsProvider settingsProvider;
        if (theme.equals("vs")) {
            settingsProvider = new vsSettingsProvider();
        } else if (theme.equals("vs-dark")) {
            settingsProvider = new vsDarkSettingsProvider();
        } else if (theme.equals("hc-black")) {
            settingsProvider = new hcBlackSettingsProvider();
        } else {
            throw new IllegalArgumentException("Unknown theme: " + theme);
        }
        JediTermFxWidget widget = new JediTermFxWidget(80, 24, settingsProvider);
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

final class vsSettingsProvider extends DefaultSettingsProvider {
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

    @Override
    public float getTerminalFontSize() {
        return 12;
    }
}

final class vsDarkSettingsProvider extends DefaultSettingsProvider {
    private TerminalColor foregroundColor = new TerminalColor(245, 245, 245);
    private TerminalColor backgroundColor = new TerminalColor(30, 30, 30);
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

    @Override
    public float getTerminalFontSize() {
        return 12;
    }
}

final class hcBlackSettingsProvider extends DefaultSettingsProvider {
    private TerminalColor foregroundColor = new TerminalColor(245, 245, 245);
    private TerminalColor backgroundColor = new TerminalColor(0, 0, 0);
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

    @Override
    public float getTerminalFontSize() {
        return 12;
    }
}
