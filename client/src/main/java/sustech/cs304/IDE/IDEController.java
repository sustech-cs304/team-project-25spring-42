package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import sustech.cs304.pdfReader.pdfReaderController;
import sustech.cs304.terminal.JeditermController;
import sustech.cs304.userhome.UserHomeController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IDEController {
    @FXML
    private AnchorPane backgroundPane, modePane, editorPane, profilePane;

    List<Node> ideContent;
    private Parent classContent, userHomeContent;

    @FXML
    private FileTreeController fileTreeController;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private EditorController editorController;

    @FXML
    private pdfReaderController MYpdfReaderController;

    @FXML
    private JeditermController jeditermController;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Hyperlink openLink;

    private String css;

    @FXML
    private void initialize() {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            backgroundPane.setPrefHeight(1048);
            backgroundPane.setLayoutY(-32);
        }

        menuBarController.setIdeController(this);
        fileTreeController.setIdeController(this);
        editorController.setIdeController(this);
        jeditermController.setIdeController(this);
        editorController.setBackground("vs-dark");

        ideContent = new ArrayList<>(modePane.getChildren());
        try {
            classContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/class.fxml")));
            userHomeContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/UserHome.fxml")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTheme(String theme) {
        editorController.setBackground(theme);
        editorController.setTheme(theme);
        Scene scene = backgroundPane.getScene();
        if (scene != null) {
            scene.getStylesheets().remove(css);
        }
        css = this.getClass().getResource("/css/style-" + theme + ".css").toExternalForm();
        scene.getStylesheets().add(css);
    }

    @FXML
    private void openFolder() {
        if (menuBarController != null) {
            menuBarController.openFolder();
        }
    }

    public void openEditor() {
        welcomeLabel.setVisible(false);
        openLink.setVisible(false);
        welcomeLabel.setManaged(false);
        openLink.setManaged(false);
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    public FileTreeController getFileTreeController() {
        return fileTreeController;
    }

    public EditorController getEditorController() {
        return editorController;
    }

    public pdfReaderController getMYpdfReaderController() {
        return MYpdfReaderController;
    }

    public JeditermController getJeditermController() {
        return jeditermController;
    }

    @FXML
    private void switchToEditor() {
        modePane.getChildren().clear();
        modePane.getChildren().addAll(ideContent);
    }

    @FXML
    private void switchToClass() {
        modePane.getChildren().clear();
        modePane.getChildren().addAll(classContent);
        AnchorPane.setTopAnchor(classContent, 0.0);
        AnchorPane.setBottomAnchor(classContent, 0.0);
        AnchorPane.setLeftAnchor(classContent, 0.0);
        AnchorPane.setRightAnchor(classContent, 0.0);
    }

    @FXML
    private void switchToUserhome() {
        modePane.getChildren().clear();
        modePane.getChildren().addAll(userHomeContent);
        AnchorPane.setTopAnchor(userHomeContent, 0.0);
        AnchorPane.setBottomAnchor(userHomeContent, 0.0);
        AnchorPane.setLeftAnchor(userHomeContent, 0.0);
        AnchorPane.setRightAnchor(userHomeContent, 0.0);
    }

}
