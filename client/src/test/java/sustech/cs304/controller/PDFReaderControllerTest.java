package sustech.cs304.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Test;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import junit.framework.TestCase;
import sustech.cs304.controller.PDFReaderController.TextLine;

public class PDFReaderControllerTest extends TestCase {
    private PDFReaderController controller;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new PDFReaderController();
    }

    @Test
    void testSetIdeController() throws Exception {
        AnchorPane rootAnchorPane = new AnchorPane();
        ScrollPane scrollPane = new ScrollPane();
        AnchorPane pdfReaderPane = new AnchorPane();
        IDEController ideController = new IDEController();
        PDDocument document = new PDDocument(); // Mocked document for testing
        PDFRenderer renderer = new PDFRenderer(document);
        int currentPage = 0;
        ImageView pdfImageView = new ImageView();
        double zoomFactor = 1.0;
    
        controller = new PDFReaderController(rootAnchorPane, scrollPane, pdfReaderPane, ideController, document, renderer, currentPage, zoomFactor, pdfImageView);
        controller.extractAndDrawCodeBlocks(0);
        List<TextLine> lines  = new ArrayList<>();
        lines.add(new TextLine(10, 20, 100, 15, "Sample code line 1"));
        controller.mergeLines(lines);
        String language = controller.detectLanguage("cout<<\"Hello, World!\";");
        assertEquals("cpp", language);
        // controller.setFile(new java.io.File(getClass().getResource("/img/gemini.png").toURI()));

    }

    public void testOtherMethods() {
        // Skipped: depends on JavaFX UI and PDFBox, cannot be tested in normal JUnit environment
    }

    // private static class TextLine {
    //     double x, y, width, height;
    //     String content;
    //
    //     public TextLine(double x, double y, double width, double height, String content) {
    //         this.x = x;
    //         this.y = y;
    //         this.width = width;
    //         this.height = height;
    //         this.content = content;
    //     }
    // }

} 
