package sustech.cs304.IDE;

import java.util.ArrayList;
import java.util.List;

import eu.mihosoft.monacofx.MonacoFX;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;


public class EditorController {

    @FXML
    private TabPane editorTabPane;

    private ArrayList<MonacoFX> monacoFXs;

    @FXML
    private void initialize() {
        monacoFXs = new ArrayList<>();
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

    public void addPage(List<String> lines) {
        MonacoFX monacoFX = new MonacoFX();
        AnchorPane editorPane = new AnchorPane();
        AnchorPane.setTopAnchor(monacoFX, 0.0);
        AnchorPane.setBottomAnchor(monacoFX, 0.0);
        AnchorPane.setLeftAnchor(monacoFX, 0.0);
        AnchorPane.setRightAnchor(monacoFX, 0.0);
        
        
        editorPane.getChildren().add(monacoFX);
        monacoFXs.add(monacoFX);

        Tab tab = new Tab("New Tab");
        tab.setContent(editorPane);
        editorTabPane.getTabs().add(tab);
        setText(monacoFX, lines);
    }
}
