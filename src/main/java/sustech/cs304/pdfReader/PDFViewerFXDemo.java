package sustech.cs304.pdfReader;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;

public class PDFViewerFXDemo extends Application {

    private ImageView imageView;
    private float zoomLevel = 1.0f;
    private PDDocument document;
    private PDFRenderer renderer;
    private int currentPage = 0; // Current page to display

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PDF Viewer (JavaFX)");

        imageView = new ImageView();
        imageView.setPreserveRatio(true);

        ScrollPane scrollPane = new ScrollPane(imageView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Button zoomInButton = new Button("Zoom In");
        Button zoomOutButton = new Button("Zoom Out");
        Button prevPageButton = new Button("Previous Page");
        Button nextPageButton = new Button("Next Page");


        zoomInButton.setOnAction(e -> {
            zoomLevel += 0.1f;
            renderPage();
        });

        zoomOutButton.setOnAction(e -> {
            zoomLevel = Math.max(zoomLevel - 0.1f, 0.1f); // Prevent zoom level from being less than 0.1
            renderPage();
        });

        prevPageButton.setOnAction(e -> {
            if (currentPage > 0) {
                currentPage--;
                renderPage();
            }
        });

        nextPageButton.setOnAction(e -> {
            if (document != null && currentPage < document.getNumberOfPages() - 1) {
                currentPage++;
                renderPage();
            }
        });


        HBox controlPanel = new HBox(10, zoomInButton, zoomOutButton, prevPageButton, nextPageButton);
        controlPanel.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setTop(controlPanel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            openPDF(file);
        }
    }

    private void openPDF(File file) {
        try {
            document = Loader.loadPDF(file);
            renderer = new PDFRenderer(document);
            renderPage();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to open PDF: " + e.getMessage());
        }
    }

    private void renderPage() {
        try {
            if (renderer != null) {
                BufferedImage bufferedImage = renderer.renderImage(currentPage, zoomLevel);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to render PDF page: " + e.getMessage());
        }
    }
}