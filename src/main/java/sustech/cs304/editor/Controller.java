package sustech.cs304.editor;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private VBox terminalBox;

    @FXML
    private AnchorPane terminalAnchorPane;

    @FXML
    private TextArea terminalText;

    @FXML
    private ScrollPane viewScrollPane;

    @FXML
    private ImageView pdfImageView;

    @FXML
    private AnchorPane contentAnchorPane; // AnchorPane inside ScrollPane

    private PDDocument pdfDocument;
    private PDFRenderer pdfRenderer;
    private int currentPage = 0;



    @FXML
    private VBox iconBar; // 左侧图标栏


    @FXML
    private void handleIconClick(ActionEvent event) {
        // 获取点击的按钮
        Button clickedButton = (Button) event.getSource();

        // 在终端输出按钮的文本
        terminalText.appendText("Clicked: " + clickedButton.getId() + "\n");
    }








    @FXML
    private void initialize() {
        // Initialize terminal text
        terminalText.setText("terminal\n");

        // Bind ImageView size to ScrollPane size
        viewScrollPane.widthProperty().addListener((obs, oldVal, newVal) -> adjustImageViewSize());
        viewScrollPane.heightProperty().addListener((obs, oldVal, newVal) -> adjustImageViewSize());
    }

    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                // Load the PDF document using PDFBox
                pdfDocument = Loader.loadPDF(file);
                pdfRenderer = new PDFRenderer(pdfDocument);

                // Display the first page
                displayPage(0);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Failed to open PDF: " + e.getMessage());
            }
        }
    }

    @FXML
    private void nextPage() {
        if (pdfDocument != null && currentPage < pdfDocument.getNumberOfPages() - 1) {
            displayPage(++currentPage);
        }
    }

    @FXML
    private void previousPage() {
        if (pdfDocument != null && currentPage > 0) {
            displayPage(--currentPage);
        }
    }

    @FXML
    private void zoomIn() {
        pdfImageView.setFitWidth(pdfImageView.getFitWidth() * 1.2);
        pdfImageView.setFitHeight(pdfImageView.getFitHeight() * 1.2);
    }

    @FXML
    private void zoomOut() {
        pdfImageView.setFitWidth(pdfImageView.getFitWidth() / 1.2);
        pdfImageView.setFitHeight(pdfImageView.getFitHeight() / 1.2);
    }

    private void adjustImageViewSize() {
        // 只有在没有手动调整 ImageView 的大小时才调整大小
        if (pdfImageView.getFitWidth() == 0 && pdfImageView.getFitHeight() == 0) {
            // Get the dimensions of the ScrollPane
            double scrollPaneWidth = viewScrollPane.getWidth();
            double scrollPaneHeight = viewScrollPane.getHeight();

            // Set the ImageView size to match the ScrollPane size
            pdfImageView.setFitWidth(scrollPaneWidth);
            pdfImageView.setFitHeight(scrollPaneHeight);
        }
    }

    private void displayPage(int pageIndex) {
        try {
            // Render the PDF page as an image
            Image image = SwingFXUtils.toFXImage(pdfRenderer.renderImage(pageIndex), null);
            pdfImageView.setImage(image);

            // Adjust the ImageView size to fit the ScrollPane
            adjustImageViewSize();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to render PDF page: " + e.getMessage());
        }
    }



    @FXML
    private void closePDF() {
        try {
            if (pdfDocument != null) {
                pdfDocument.close();
                pdfDocument = null;
                pdfImageView.setImage(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}