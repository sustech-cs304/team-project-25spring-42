package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import eu.mihosoft.monacofx.MonacoFX;

public class IDEController {
    
    @FXML
    private AnchorPane editorPane;

    @FXML
    private void initialize() {
        EditorController editorController = new EditorController();
        MonacoFX monacoFX = editorController.getMonacoFX();
        monacoFX.prefWidthProperty().bind(editorPane.widthProperty());
        monacoFX.prefHeightProperty().bind(editorPane.heightProperty());
        editorPane.getChildren().add(monacoFX);
        monacoFX.getEditor().getDocument().setText(
                "#include <stdio.h>\n" +
                "int main() {\n" +
                "   // printf() displays the string inside quotation\n" +
                "   printf(\"Hello, World!\");\n" +
                "   return 0;\n" +
                "}");
        monacoFX.getEditor().setCurrentLanguage("c");
        monacoFX.getEditor().setCurrentTheme("vs-dark");
    }

}
