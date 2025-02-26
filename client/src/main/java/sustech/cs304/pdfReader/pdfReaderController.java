package sustech.cs304.pdfReader;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class pdfReaderController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ScrollPane pdfScrollPane;

    @FXML
    private AnchorPane pdfReaderPane;

    private PDDocument document;
    private PDFRenderer renderer;
    private int currentPage = 0;
    private double zoomFactor = 1.0;
    private ImageView pdfImageView;

    public void initialize() {

        pdfImageView = new ImageView();
        pdfReaderPane.getChildren().add(pdfImageView);


        pdfScrollPane.setFitToWidth(true);
        pdfScrollPane.setFitToHeight(true);
        pdfScrollPane.setPannable(true);


        pdfReaderPane.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showContextMenu);

        try {

            File pdfFile = new File("/Users/ylers/Desktop/paper/textF-ch7.3.2.pdf"); // 修改为你的 PDF 文件路径
            document = Loader.loadPDF(pdfFile);
            renderer = new PDFRenderer(document);
            renderPage(currentPage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load PDF file: " + e.getMessage());
        }
    }

    public void getFile(File file){
        try {

            File pdfFile = file;
            document = Loader.loadPDF(pdfFile);
            renderer = new PDFRenderer(document);
            currentPage = 0;
            renderPage(currentPage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load PDF file: " + e.getMessage());
        }

    }

    private void renderPage(int pageIndex) throws IOException {

        BufferedImage bufferedImage = renderer.renderImageWithDPI(pageIndex, (float) (zoomFactor * 72));
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);


        pdfImageView.setImage(image);
        pdfImageView.setFitWidth(bufferedImage.getWidth());
        pdfImageView.setFitHeight(bufferedImage.getHeight());


        pdfReaderPane.setPrefWidth(bufferedImage.getWidth());
        pdfReaderPane.setPrefHeight(bufferedImage.getHeight());
    }

    private void showContextMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem zoomIn = new MenuItem("Zoom In");
            zoomIn.setOnAction(e -> {
                zoomFactor = Math.min(zoomFactor * 1.25, 4.0);
                try {
                    renderPage(currentPage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            MenuItem zoomOut = new MenuItem("Zoom Out");
            zoomOut.setOnAction(e -> {
                zoomFactor = Math.max(zoomFactor / 1.25, 0.25);
                try {
                    renderPage(currentPage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            MenuItem nextPage = new MenuItem("Next Page");
            nextPage.setOnAction(e -> {
                if (currentPage < document.getNumberOfPages() - 1) {
                    currentPage++;
                    try {
                        renderPage(currentPage);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            MenuItem previousPage = new MenuItem("Previous Page");
            previousPage.setOnAction(e -> {
                if (currentPage > 0) {
                    currentPage--;
                    try {
                        renderPage(currentPage);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            contextMenu.getItems().addAll(zoomIn, zoomOut, nextPage, previousPage);
            contextMenu.show(pdfReaderPane, event.getScreenX(), event.getScreenY());
        }
    }
}