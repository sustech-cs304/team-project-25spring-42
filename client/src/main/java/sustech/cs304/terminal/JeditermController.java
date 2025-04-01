package sustech.cs304.terminal;

import com.techsenger.jeditermfx.ui.JediTermFxWidget;
import com.techsenger.jeditermfx.ui.settings.DefaultSettingsProvider;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import com.techsenger.jeditermfx.app.pty.PtyProcessTtyConnector;
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
    public void initialize() {
        System.out.println("Initializing JeditermController...");
        JediTermFxWidget widget =  createTerminalWidget();
        widget.addListener(terminalWidget -> {
            widget.close();
        });
        terminalPane.getChildren().add(widget.getPane());
    }

    private JediTermFxWidget createTerminalWidget() {
        JediTermFxWidget widget = new JediTermFxWidget(80, 24, new DefaultSettingsProvider());
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
