package sustech.cs304.IDE;

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
        monacoFX.getEditor().getDocument().setText(
                "#include <stdio.h>\n" +
                "int main() {\n" +
                "   // printf() displays the string inside quotation\n" +
                "   printf(\"Hello, World!\");\n" +
                "   return 0;\n" +
                "}");
        monacoFX.getEditor().setCurrentLanguage("c");
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
}
