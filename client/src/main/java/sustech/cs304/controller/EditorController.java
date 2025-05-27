package sustech.cs304.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import eu.mihosoft.monacofx.MonacoFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import sustech.cs304.utils.FileUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Editor controller, responsible for managing code/text editor tabs and file operations in the IDE.
 */
public class EditorController {

    private IDEController ideController;

    @FXML
    private TabPane editorTabPane;

    private HashSet<MonacoFX> monacoFXs;
    private HashSet<File> files;

    private String background;

    /**
     * Initializes the editor controller, sets up data structures.
     */
    @FXML
    private void initialize() {
        monacoFXs = new HashSet<>();
        files = new HashSet<>();
    }

    /**
     * Sets the IDE controller reference.
     * @param ideController The IDE controller
     */
    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }

    /**
     * Sets the background theme for the editor.
     * @param background The theme name
     */
    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * Sets the theme for all MonacoFX editors.
     * @param theme The theme name
     */
    public void setTheme(String theme) {
        for (MonacoFX monacoFX : monacoFXs) {
            monacoFX.getEditor().setCurrentTheme(theme);
        }
    }

    /**
     * Sets the text content for a MonacoFX editor.
     * @param monacoFX The MonacoFX instance
     * @param lines The text lines
     */
    public void setText(MonacoFX monacoFX, List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        monacoFX.getEditor().getDocument().setText(sb.toString());
    }

    /**
     * Sets the language for a MonacoFX editor based on file extension.
     * @param monacoFX The MonacoFX instance
     * @param file The file to determine language
     */
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

    /**
     * Adds a new code/text tab for the given file.
     * @param lines The file content lines
     * @param file The file to open
     */
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

    /**
     * Adds a new PDF tab for the given file.
     * @param file The PDF file
     * @throws IOException if loading fails
     */
    public void addPDFPage(File file) throws IOException {
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
        PDFReaderController pdfReaderController = loader.getController();
        pdfReaderController.setIdeController(ideController);
        pdfReaderController.setFile(file);

        newTab.setContent(pdfPane);
        editorTabPane.getTabs().add(newTab);
        editorTabPane.getSelectionModel().select(newTab);

        files.add(file);
    }

    /**
     * Adds a new PPT tab for the given file.
     * @param file The PPT file
     * @throws IOException if loading fails
     */
    public void addPPTPage(File file) throws IOException {
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
        loader.setLocation(getClass().getResource("/fxml/pptReader.fxml")); // 确保路径正确
        AnchorPane pptPane = loader.load();
        PPTReaderController pptReaderController = loader.getController();
        pptReaderController.setFile(file);

        newTab.setContent(pptPane);
        editorTabPane.getTabs().add(newTab);
        editorTabPane.getSelectionModel().select(newTab);

        files.add(file);
    }

    /**
     * Finds the tab corresponding to the given file.
     * @param file The file
     * @return The Tab instance, or null if not found
     */
    private Tab findTabByFile(File file) {
        for (Tab tab : editorTabPane.getTabs()) {
            if (tab.getId().equals(file.getAbsolutePath())) {
                return tab;
            }
        }
        return null;
    }

    /**
     * Saves the content of the currently selected tab to file.
     */
    public void savePage() {
        Tab tab = this.editorTabPane.getSelectionModel().getSelectedItem();
        if (tab == null) {
            return;
        }
        File file = getCurrentFile();
        MonacoFX monaco = (MonacoFX) ((AnchorPane) tab.getContent()).getChildren().get(0);
        String content = monaco.getEditor().getDocument().getText();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll() {
        for (Tab tab : editorTabPane.getTabs()) {
            if (tab == null) {
                return;
            }
            String path = tab.getId();
            File file = new File(path);
            MonacoFX monaco = (MonacoFX) ((AnchorPane) tab.getContent()).getChildren().get(0);
            String content = monaco.getEditor().getDocument().getText();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Gets the file currently being edited in the selected tab.
     * @return The current file, or null if none
     */
    public File getCurrentFile() {
        Tab tab = this.editorTabPane.getSelectionModel().getSelectedItem();
        if (tab == null) {
            return null;
        }
        String path = tab.getId();
        return new File(path);
    }
}
