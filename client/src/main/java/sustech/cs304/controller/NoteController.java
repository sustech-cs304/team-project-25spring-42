package sustech.cs304.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NoteController {

    private static NoteController instance;
    private Stage stage;
    private TextArea noteArea;
    private WebView markdownView;

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    private NoteController(Stage owner) {
        stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Note (Markdown)");
        stage.setResizable(true);
        stage.setWidth(800);
        stage.setHeight(500);

        noteArea = new TextArea();
        noteArea.setPromptText("Enter Markdown...");
        noteArea.setWrapText(true);

        markdownView = new WebView();
        markdownView.setContextMenuEnabled(false);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(noteArea, markdownView);
        splitPane.setDividerPositions(0.5);

        VBox layout = new VBox(splitPane);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER_LEFT);

        Scene scene = new Scene(layout);
        stage.setScene(scene);

        noteArea.textProperty().addListener((obs, oldText, newText) -> updateMarkdown(newText));

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.close();
                instance = null;
            }
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if ((event.isMetaDown() || event.isControlDown()) && event.getCode() == KeyCode.T) {
                stage.hide();
                event.consume();
            }
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if ((event.isMetaDown() || event.isControlDown()) && event.getCode() == KeyCode.S) {
                NoteController.saveToFile(stage);
                event.consume();
            }
        });

        stage.setOnShown(e -> noteArea.requestFocus());

        stage.setOnCloseRequest(e -> {
            noteArea.clear();
            markdownView.getEngine().loadContent("");
            instance = null;
        });
    }

    private void updateMarkdown(String markdownText) {
        // String html = renderer.render(parser.parse(markdownText));
        // markdownView.getEngine().loadContent(html);
        String html = renderer.render(parser.parse(markdownText));
        String htmlWrapped = """
            <html>
            <head>
                <style>
                    body {
                        font-family: sans-serif;
                        white-space: pre-wrap;
                        word-wrap: break-word;
                        padding: 10px;
                    }
                </style>
            </head>
            <body>%s</body>
            </html>
            """.formatted(html);

        markdownView.getEngine().loadContent(htmlWrapped);
    }

    public static void toggle(Stage owner) {
        if (instance == null) {
            instance = new NoteController(owner);
            instance.stage.show();
        } else if (instance.stage.isShowing()) {
            instance.stage.hide();
        } else {
            instance.stage.show();
            instance.stage.toFront();
        }
    }

    public static boolean isOpen() {
        return instance != null && instance.stage.isShowing();
    }

    public static void close() {
        if (instance != null) {
            instance.stage.close();
        }
    }

    public static String getText() {
        return (instance != null) ? instance.noteArea.getText() : "";
    }

    public static void setText(String text) {
        if (instance != null) {
            instance.noteArea.setText(text);
        }
    }

    public static void saveToFile(Stage owner) {
        if (instance == null || instance.noteArea == null) {
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Notes As Markdown");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Markdown Files", "*.md")
        );
        fileChooser.setInitialFileName("notes.md");

        File file = fileChooser.showSaveDialog(owner);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(instance.noteArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
}
