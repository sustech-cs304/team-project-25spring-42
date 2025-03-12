package sustech.cs304.IDE;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import eu.mihosoft.monacofx.MonacoFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import sustech.cs304.pdfReader.pdfReaderController;
import sustech.cs304.utils.FileUtils;

public class EditorController {

    @FXML
    private TabPane editorTabPane;

    private HashSet<MonacoFX> monacoFXs;
    private HashSet<File> files;

    private String background;

    @FXML
    private void initialize() {
        monacoFXs = new HashSet<>();
        files = new HashSet<>();
    }

    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * Set the theme of the editor
     * @param theme the theme to be set, include "vs", "vs-dark", "hc-black"
     */


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

    public void setLanguage(MonacoFX monacoFX, File file) {
        String extension = FileUtils.getExtension(file);
        if (extension.equals("java")) {
            monacoFX.getEditor().setCurrentLanguage("java");
        } else if (extension.equals("cpp")) {
            monacoFX.getEditor().setCurrentLanguage("cpp");
        } else if (extension.equals("c")) {
            monacoFX.getEditor().setCurrentLanguage("c");
        } else if (extension.equals("py")) {
            monacoFX.getEditor().setCurrentLanguage("python");
        } else if (extension.equals("html")) {
            monacoFX.getEditor().setCurrentLanguage("html");
        } else if (extension.equals("css")) {
            monacoFX.getEditor().setCurrentLanguage("css");
        } else if (extension.equals("js")) {
            monacoFX.getEditor().setCurrentLanguage("javascript");
        } else if (extension.equals("json")) {
            monacoFX.getEditor().setCurrentLanguage("json");
        } else if (extension.endsWith("xml")) {
            monacoFX.getEditor().setCurrentLanguage("xml");
        } else if (extension.equals("sql")) {
            monacoFX.getEditor().setCurrentLanguage("sql");
        } else if (extension.equals("sh")) {
            monacoFX.getEditor().setCurrentLanguage("shell");
        } else {
            monacoFX.getEditor().setCurrentLanguage("plaintext");
        }
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
        setLanguage(monacoFX, file);
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
        setTheme(background);
    }




    public void addpdfPage(File file) throws IOException {
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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/pdfReader.fxml")); // 确保路径正确
        AnchorPane pdfPane = loader.load();
        pdfReaderController pdfReaderController = loader.getController();
        pdfReaderController.getFile(file);

        newTab.setContent(pdfPane);
        editorTabPane.getTabs().add(newTab);
        editorTabPane.getSelectionModel().select(newTab);

        files.add(file);
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


