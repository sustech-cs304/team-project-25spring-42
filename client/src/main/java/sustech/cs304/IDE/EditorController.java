package sustech.cs304.IDE;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import eu.mihosoft.monacofx.MonacoFX;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.skin.TabPaneSkin;
import javafx.scene.layout.AnchorPane;

public class EditorController {

    @FXML
    private TabPane editorTabPane;

    private HashSet<MonacoFX> monacoFXs;
    private HashSet<File> files;

    @FXML
    private void initialize() {
        monacoFXs = new HashSet<>();
        files = new HashSet<>();
    }

    public void setTheme(String theme) {
        for (MonacoFX monacoFX : monacoFXs) {
            monacoFX.getEditor().setCurrentTheme(theme);
        }
    }

    public void setText(MonacoFX monacoFX, List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        monacoFX.getEditor().getDocument().setText(sb.toString());
    }

    public void addPage(List<String> lines, File file) {
        if (files.contains(file)) {
            Tab tab = findTabByFile(file);
            editorTabPane.getSelectionModel().select(tab);
            return;
        }

        Tab newTab = new Tab();
        newTab.setId(file.getAbsolutePath());
        newTab.setText(file.getName());
        newTab.setOnClosed(event -> {
            files.remove(file);
        });
        
        MonacoFX monacoFX = new MonacoFX();
        monacoFXs.add(monacoFX);

        files.add(file);

        AnchorPane editorPane = new AnchorPane();
        AnchorPane.setTopAnchor(monacoFX, 0.0);
        AnchorPane.setBottomAnchor(monacoFX, 0.0);
        AnchorPane.setLeftAnchor(monacoFX, 0.0);
        AnchorPane.setRightAnchor(monacoFX, 0.0);
        editorPane.getChildren().add(monacoFX);

        newTab.setContent(editorPane);
        editorTabPane.getTabs().add(newTab);
        editorTabPane.getSelectionModel().select(newTab);
        setText(monacoFX, lines);
    }

    private Tab findTabByFile(File file) {
        for (Tab tab : editorTabPane.getTabs()) {
            if (tab.getId().equals(file.getAbsolutePath())) {
                return tab;
            }
        }
        return null;
    }
}


