package sustech.cs304.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.MenuItem;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * AI-generated-content
 * tool: chatGPT
 * version: latest
 * usage: we ask chatGPT to generate a PPT reader controller
 * But we modify it a lot.
 */
public class PPTReaderController {

    @FXML public AnchorPane rootAnchorPane;
    @FXML private ScrollPane pptScrollPane;
    @FXML private AnchorPane pptReaderPane;
    @FXML private ImageView pptImageView;

    private XMLSlideShow ppt;
    private List<XSLFSlide> slides;
    private int currentPage = 0;
    private double zoomFactor = 1.0;

    /**
     * Initializes the PPT reader, sets up image view and keyboard events.
     * @throws IOException if initialization fails
     */
    public void initialize() throws IOException {
        pptImageView = new ImageView();
        pptReaderPane.getChildren().add(pptImageView);

        pptScrollPane.setFitToWidth(true);
        pptScrollPane.setFitToHeight(true);
        pptScrollPane.setPannable(true);

        pptReaderPane.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showContextMenu);
        pptScrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> updateImageViewPosition());
        setKeyboardEvents();
    }

    private void setKeyboardEvents() {
        pptScrollPane.setOnKeyPressed(event -> {
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
                    if (currentPage < slides.size() - 1) {
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
        pptImageView.setOnZoom(event -> {
            zoomFactor = Math.min(Math.max(zoomFactor * event.getZoomFactor(), 0.25), 4.0);
            try {
                renderPage(currentPage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            event.consume();
        });

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
                if (currentPage < slides.size() - 1) {
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
            contextMenu.show(pptReaderPane, event.getScreenX(), event.getScreenY());
        }
    }
    
    /**
     * Sets the PPT file to display.
     * @param file The PPT file
     */
    public void setFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            ppt = new XMLSlideShow(fis);
            slides = ppt.getSlides();
            currentPage = 0;
            renderPage(currentPage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load PPT file: " + e.getMessage());
        }
    }

    /**
     * Updates the position of the image view in the scroll pane.
     */
    private void updateImageViewPosition() {
        double offsetX = (pptScrollPane.getViewportBounds().getWidth() - pptImageView.getFitWidth()) / 2;
        double offsetY = (pptScrollPane.getViewportBounds().getHeight() - pptImageView.getFitHeight()) / 2;

        pptImageView.setLayoutX(Math.max(offsetX, 0));
        pptImageView.setLayoutY(Math.max(offsetY, 0));
    }

    /**
     * Renders the specified slide of the PPT.
     * @param index The slide index (0-based)
     * @throws IOException if rendering fails
     */
    private void renderPage(int index) throws IOException {
        Dimension pgsize = ppt.getPageSize();
        int width = (int) (pgsize.getWidth() * zoomFactor);
        int height = (int) (pgsize.getHeight() * zoomFactor);

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // graphics.scale(zoomFactor, zoomFactor);
        double scaleX = width / pgsize.getWidth();
        double scaleY = height / pgsize.getHeight();
        graphics.scale(scaleX, scaleY);

        slides.get(index).draw(graphics);
        graphics.dispose();

        Image fxImage = SwingFXUtils.toFXImage(img, null);

        pptImageView.setImage(fxImage);
        pptImageView.setFitWidth(width);
        pptImageView.setFitHeight(height);

        pptReaderPane.setPrefWidth(width);
        pptReaderPane.setPrefHeight(height);

        updateImageViewPosition();
    }
}
