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
    private ScrollPane pdfScrollPane; // ScrollPane，用于提供滚动功能

    @FXML
    private AnchorPane pdfReaderPane; // 用于显示 PDF 内容的 AnchorPane

    private PDDocument document;
    private PDFRenderer renderer;
    private int currentPage = 0;
    private double zoomFactor = 1.0;
    private ImageView pdfImageView;

    public void initialize() {
        // 初始化 ImageView
        pdfImageView = new ImageView();
        pdfReaderPane.getChildren().add(pdfImageView);

        // 设置 ScrollPane 的属性
        pdfScrollPane.setFitToWidth(true); // 允许水平滚动
        pdfScrollPane.setFitToHeight(true); // 允许垂直滚动
        pdfScrollPane.setPannable(true); // 允许拖动滚动

        // 绑定右键菜单事件
        pdfReaderPane.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showContextMenu);

        try {
            // 加载 PDF 文件
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
        // 渲染 PDF 页面到 BufferedImage
        BufferedImage bufferedImage = renderer.renderImageWithDPI(pageIndex, (float) (zoomFactor * 72));
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);

        // 设置 ImageView 的图像和尺寸
        pdfImageView.setImage(image);
        pdfImageView.setFitWidth(bufferedImage.getWidth());
        pdfImageView.setFitHeight(bufferedImage.getHeight());

        // 调整 pdfReaderPane 的尺寸以匹配 ImageView
        pdfReaderPane.setPrefWidth(bufferedImage.getWidth());
        pdfReaderPane.setPrefHeight(bufferedImage.getHeight());
    }

    private void showContextMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem zoomIn = new MenuItem("Zoom In");
            zoomIn.setOnAction(e -> {
                zoomFactor = Math.min(zoomFactor * 1.25, 4.0); // 限制最大缩放为 4 倍
                try {
                    renderPage(currentPage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            MenuItem zoomOut = new MenuItem("Zoom Out");
            zoomOut.setOnAction(e -> {
                zoomFactor = Math.max(zoomFactor / 1.25, 0.25); // 限制最小缩放为 0.25 倍
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