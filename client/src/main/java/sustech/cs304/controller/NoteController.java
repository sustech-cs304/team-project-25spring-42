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

/**
 * Note controller, responsible for displaying and editing markdown notes in a separate window.
 */
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

    /**
     * Updates the markdown preview area when the text changes.
     * @param markdownText The markdown text to render
     */
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

    /**
     * Toggles the note window. If not open, opens it; otherwise hides or brings to front.
     * @param owner The owner stage
     */
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

    /**
     * Checks if the note window is open.
     * @return true if open, false otherwise
     */
    public static boolean isOpen() {
        return instance != null && instance.stage.isShowing();
    }

    /**
     * Closes the note window if open.
     */
    public static void close() {
        if (instance != null) {
            instance.stage.close();
        }
    }

    /**
     * Gets the current text in the note area.
     * @return The note text
     */
    public static String getText() {
        return (instance != null) ? instance.noteArea.getText() : "";
    }

    /**
     * Sets the text in the note area.
     * @param text The text to set
     */
    public static void setText(String text) {
        if (instance != null) {
            instance.noteArea.setText(text);
        }
    }

    /**
     * Saves the note content to a markdown file.
     * @param owner The owner stage for the file dialog
     */
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
