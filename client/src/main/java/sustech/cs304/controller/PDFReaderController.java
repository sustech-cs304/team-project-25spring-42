package sustech.cs304.controller;

import javafx.animation.PauseTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import eu.mihosoft.monacofx.MonacoFX;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * PDF reader controller, responsible for displaying PDF files, navigation, and code block extraction.
 */
public class PDFReaderController {

    @FXML
    public AnchorPane rootAnchorPane;

    @FXML
    private ScrollPane pdfScrollPane;

    @FXML
    private AnchorPane pdfReaderPane;

    private IDEController ideController;

    private PDDocument document;
    private PDFRenderer renderer;
    private int currentPage = 0;
    private double zoomFactor = 1.0;
    private ImageView pdfImageView;

    private List<CodeBlock> codeBlocks = new ArrayList<>();

    private static final Map<String, String[]> LANGUAGE_KEYWORDS = Map.of(
        "java", new String[]{"class", "public", "static", "void", "import", "new", "return", ";", "{", "}"},
        "python", new String[]{"def", "import", "self", ":", "print", "return", "#"},
        "cpp", new String[]{"#include", "std::", "cout", "cin", ";", "{", "}"},
        "javaScript", new String[]{"function", "var", "let", "const", ";", "{", "}"}
    );

    /**
     * Initializes the PDF reader, sets up image view and keyboard events.
     */
    public void initialize() {
        pdfImageView = new ImageView();
        pdfReaderPane.getChildren().add(pdfImageView);

        pdfScrollPane.setFitToWidth(true);
        pdfScrollPane.setFitToHeight(true);
        pdfScrollPane.setPannable(true);

        pdfScrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> updateImageViewPosition());
        setKeyboardEvents();
    }

    private void setKeyboardEvents() {
        pdfScrollPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    if (currentPage > 0) {
                        currentPage--;
                        try {
                            renderPage(currentPage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case RIGHT:
                    if (currentPage < document.getNumberOfPages() - 1) {
                        currentPage++;
                        try {
                            renderPage(currentPage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        });
        pdfImageView.setOnZoom(event -> {
            zoomFactor = Math.min(Math.max(zoomFactor * event.getZoomFactor(), 0.25), 4.0);
            try {
                renderPage(currentPage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            event.consume();
        });
    }

    /**
     * Sets the PDF file to display.
     * @param file The PDF file
     */
    public void setFile(File file) {
        try {
            document = Loader.loadPDF(file);
            renderer = new PDFRenderer(document);
            currentPage = 0;
            renderPage(currentPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders the specified page of the PDF.
     * @param pageIndex The page index (0-based)
     * @throws IOException if rendering fails
     */
    private void renderPage(int pageIndex) throws IOException {
        BufferedImage bufferedImage = renderer.renderImageWithDPI(pageIndex, (float) (zoomFactor * 72));
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);

        pdfImageView.setImage(image);
        pdfImageView.setFitWidth(bufferedImage.getWidth());
        pdfImageView.setFitHeight(bufferedImage.getHeight());

        pdfReaderPane.setPrefWidth(bufferedImage.getWidth());
        pdfReaderPane.setPrefHeight(bufferedImage.getHeight());

        updateImageViewPosition();
        extractAndDrawCodeBlocks(pageIndex);
    }

    /**
     * Extracts code blocks from the current page and draws overlays.
     * @param pageIndex The page index
     * @throws IOException if extraction fails
     */
    private void extractAndDrawCodeBlocks(int pageIndex) throws IOException {
        pdfReaderPane.getChildren().removeIf(n -> n instanceof Rectangle || n instanceof Button);
        codeBlocks.clear();

        List<TextLine> lines = new ArrayList<>();

        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                if (text.trim().isEmpty()) return;

                float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
                float y = textPositions.get(0).getYDirAdj();
                float height = textPositions.get(0).getHeightDir();

                for (TextPosition tp : textPositions) {
                    float x = tp.getXDirAdj();
                    minX = Math.min(minX, x);
                    maxX = Math.max(maxX, x + tp.getWidthDirAdj());
                }

                String lineText = text.trim();
                double width = maxX - minX;

                if (lineText.startsWith(" ") || lineText.startsWith("\t") || containsCodeIndicators(lineText)) {
                    lines.add(new TextLine(minX, y - height, width, height, lineText));
                }
            }
        };
        stripper.setSortByPosition(true);
        stripper.setStartPage(pageIndex + 1);
        stripper.setEndPage(pageIndex + 1);
        stripper.getText(document);

        lines.sort(Comparator.comparingDouble(l -> l.y));

        List<CodeBlock> tempBlocks = new ArrayList<>();
        List<TextLine> currentBlock = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            TextLine line = lines.get(i);
            if (currentBlock.isEmpty()) {
                currentBlock.add(line);
            } else {
                TextLine last = currentBlock.get(currentBlock.size() - 1);
                // 判断是否连续行（行间距和缩进相近）
                if (Math.abs(line.x - last.x) <= 15.0) {
                    currentBlock.add(line);
                } else {
                    if (isLikelyCodeBlock(currentBlock)) {
                        tempBlocks.add(mergeLines(currentBlock));
                    }
                    currentBlock.clear();
                    currentBlock.add(line);
                }
            }
        }
        if (isLikelyCodeBlock(currentBlock)) {
            tempBlocks.add(mergeLines(currentBlock));
        }

        double offsetX = pdfImageView.getLayoutX();
        double offsetY = pdfImageView.getLayoutY();

        for (CodeBlock cb : tempBlocks) {
            Rectangle rect = new Rectangle(
                cb.x * zoomFactor + offsetX,
                cb.y * zoomFactor + offsetY,
                cb.width * zoomFactor,
                cb.height * zoomFactor
            );
            rect.setFill(Color.TRANSPARENT);
            rect.setStroke(Color.TRANSPARENT);
            rect.setStrokeWidth(1.0);
            rect.getStrokeDashArray().addAll(4.0, 4.0);
            rect.setVisible(true);
            pdfReaderPane.getChildren().add(rect);

            String lang = detectLanguage(cb.content);

            Button btn = new Button("View code (" + lang + ")");
            btn.setLayoutX(rect.getX() + rect.getWidth() - 100);
            btn.setLayoutY(rect.getY() + rect.getHeight() + 5);
            btn.setOnAction(e -> showCodePopup(cb.content, lang));
            btn.setVisible(false);
            pdfReaderPane.getChildren().add(btn);

            PauseTransition hideDelay = new PauseTransition(Duration.millis(150));

            rect.setOnMouseEntered(e -> {
                rect.setStroke(Color.LIGHTGRAY);
                btn.setVisible(true);
                hideDelay.stop();
            });
            rect.setOnMouseExited(e -> {
                hideDelay.setOnFinished(ev -> {
                    rect.setStroke(Color.TRANSPARENT);
                    btn.setVisible(false);
                });
                hideDelay.play();
            });

            btn.setOnMouseEntered(e -> {
                rect.setStroke(Color.LIGHTGRAY);
                btn.setVisible(true);
                hideDelay.stop();
            });
            btn.setOnMouseExited(e -> {
                hideDelay.setOnFinished(ev -> {
                    rect.setStroke(Color.TRANSPARENT);
                    btn.setVisible(false);
                });
                hideDelay.play();
            });
          }
    }

    private boolean containsCodeIndicators(String text) {
        return text.matches(".*[;{}()<>].*") || text.matches(".*\\b(class|def|if|else|for|while|return|import|public|private)\\b.*");
    }

    private boolean isLikelyCodeBlock(List<TextLine> lines) {
        if (lines.size() < 2) return false;

        int codeLineCount = 0;
        for (TextLine line : lines) {
            if (containsCodeIndicators(line.content)) codeLineCount++;
        }
        return codeLineCount >= lines.size() / 2;
    }

    private void showCodePopup(String code, String language) {
        Stage popup = new Stage();
        popup.initOwner(pdfReaderPane.getScene().getWindow());
        popup.initModality(Modality.NONE);

        popup.setTitle("code content - " + language);

        code = code.replace("\t", "    ");

        MonacoFX monaco = new MonacoFX();
        monaco.getEditor().setCurrentLanguage(language);
        monaco.getEditor().getDocument().setText(code);
        monaco.getEditor().setCurrentTheme("vs");

        Scene scene = new Scene(new VBox(monaco), 600, 400);
        popup.setScene(scene);

        scene.setOnKeyPressed(event -> {
            boolean isSave = false;
            boolean isRun = false;
            if ((event.isControlDown() || event.isMetaDown()) && event.getCode().toString().equals("S")) {
                isSave = true;
            } else if ((event.isControlDown() || event.isMetaDown()) && event.getCode().toString().equals("R")) {
                isRun = true;
            }

            if (isSave) {
                event.consume();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Code File");
                fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Code File", "*." + getExtensionByLanguage(language))
                );
                File file = fileChooser.showSaveDialog(popup);
                if (file != null) {
                    try {
                        java.nio.file.Files.writeString(file.toPath(), monaco.getEditor().getDocument().getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Save Error: " + e.getMessage());
                        alert.initOwner(popup);
                        alert.showAndWait();
                    }
                }
            }
            if (isRun) {
                event.consume();
                try {
                    File tempFile = File.createTempFile("code_run_", "." + getExtensionByLanguage(language));
                    java.nio.file.Files.writeString(tempFile.toPath(), monaco.getEditor().getDocument().getText());

                    List<File> tempOutputs = new ArrayList<>();
                    String cmd = buildExecutionCommand(language, tempFile.getAbsolutePath(), tempOutputs);

                    if (cmd == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Unsupported language: " + language);
                        alert.initOwner(popup);
                        alert.showAndWait();
                        return;
                    }

                    ideController.getJeditermController().open();
                    ideController.getJeditermController().executeCommand(cmd);

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            tempFile.delete();
                            for (File f : tempOutputs) {
                                if (f.exists()) f.delete();
                            }
                        }
                    }, 5000); // 延迟 5 秒

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Execution Error: " + e.getMessage());
                    alert.initOwner(popup);
                    alert.showAndWait();
                }
            }

        });

        popup.show();
    }

    private String getExtensionByLanguage(String language) {
        switch (language.toLowerCase()) {
            case "java": return "java";
            case "cpp":
            case "c++": return "cpp";
            case "c": return "c";
            case "python": return "py";
            case "bash": return "sh";
            default: return "txt";
        }
    }

    private String buildExecutionCommand(String language, String filePath, List<File> tempOutputs) {
        File sourceFile = new File(filePath);
        String basePath = sourceFile.getParent();

        switch (language.toLowerCase()) {
            case "python":
                return "python \"" + filePath + "\"";
            case "java":
                // Java 会生成 .class 文件
                File classFile = new File(basePath, "Main" + ".class");
                tempOutputs.add(classFile);
                return "javac \"" + filePath + "\" && java -cp \"" + basePath + "\" " + "Main";
            case "c":
            case "cpp":
            case "c++":
                // C/C++ 会生成 .out 文件
                File outFile = new File(filePath + ".out");
                tempOutputs.add(outFile);
                String compiler = language.equalsIgnoreCase("c") ? "gcc" : "g++";
                return compiler + " \"" + filePath + "\" -o \"" + outFile.getAbsolutePath() + "\" && \"" + outFile.getAbsolutePath() + "\"";
            case "bash":
                return "bash \"" + filePath + "\"";
            default:
                return null;
        }
    }

    private void updateImageViewPosition() {
        double offsetX = (pdfScrollPane.getViewportBounds().getWidth() - pdfImageView.getFitWidth()) / 2;
        double offsetY = (pdfScrollPane.getViewportBounds().getHeight() - pdfImageView.getFitHeight()) / 2;

        pdfImageView.setLayoutX(Math.max(offsetX, 0));
        pdfImageView.setLayoutY(Math.max(offsetY, 0));
    }

    private CodeBlock mergeLines(List<TextLine> lines) {
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        StringBuilder sb = new StringBuilder();

        for (TextLine line : lines) {
            minX = Math.min(minX, line.x);
            maxX = Math.max(maxX, line.x + line.width);
            minY = Math.min(minY, line.y);
            maxY = Math.max(maxY, line.y + line.height);
            sb.append(line.content).append("\n");
        }

        return new CodeBlock(minX, minY, maxX - minX, maxY - minY, sb.toString().trim());
    }

    private String detectLanguage(String code) {
        String lowerCode = code.toLowerCase();
        Map<String, Integer> scores = new HashMap<>();
        for (var entry : LANGUAGE_KEYWORDS.entrySet()) {
            int score = 0;
            for (String kw : entry.getValue()) {
                if (lowerCode.contains(kw)) score++;
            }
            scores.put(entry.getKey(), score);
        }

        return scores.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .filter(e -> e.getValue() > 1)
            .map(Map.Entry::getKey)
            .orElse("Unknown");
    }

    private static class TextLine {
        double x, y, width, height;
        String content;

        public TextLine(double x, double y, double width, double height, String content) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.content = content;
        }
    }

    private static class CodeBlock {
        double x, y, width, height;
        String content;

        public CodeBlock(double x, double y, double width, double height, String content) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.content = content;
        }
    }

    /**
     * Sets the IDE controller reference.
     * @param ideController The IDE controller
     */
    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }
}
