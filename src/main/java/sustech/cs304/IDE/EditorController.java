package sustech.cs304.IDE;

import java.util.List;

import eu.mihosoft.monacofx.MonacoFX;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


public class EditorController {

    @FXML
    private AnchorPane editorPane;

    private MonacoFX monacoFX;

    @FXML
    private void initialize() {
        monacoFX = new MonacoFX();
        monacoFX.prefWidthProperty().bind(editorPane.widthProperty());
        monacoFX.prefHeightProperty().bind(editorPane.heightProperty());
        this.editorPane.getChildren().add(monacoFX);
        monacoFX.getEditor().setCurrentLanguage("java");
        monacoFX.getEditor().setCurrentTheme("vs-light");
    }

    public MonacoFX getMonacoFX() {
        return monacoFX;
    }

    public void setMonacoFX(MonacoFX monacoFX) {
        this.monacoFX = monacoFX;
    }

    public void setTheme(String theme) {
        monacoFX.getEditor().setCurrentTheme(theme);
    }

    public void setLanguage(String language) {
        monacoFX.getEditor().setCurrentLanguage(language);
    }

    public void setText(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        monacoFX.getEditor().getDocument().setText(sb.toString());
    }
}
