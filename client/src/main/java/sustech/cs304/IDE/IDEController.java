package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private ImageView codeImage, chatImage, classImage, userImage, settingImage;

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

    public void changeImageColor(String theme) {
        if (theme == "vs-dark" || theme == "hc-black") {
            theme = "white";
        } else if (theme == "vs") {
            theme = "black";
        } else {
            throw new IllegalArgumentException("Invalid theme: " + theme);
        }
        Image codeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/code-" + theme + ".png")));
        Image chatImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/chat-" + theme + ".png")));
        Image classImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/class-" + theme + ".png")));
        Image userImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/user-" + theme + ".png")));
        Image settingImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/setting-" + theme + ".png")));
        this.codeImage.setImage(codeImage);
        this.chatImage.setImage(chatImage);
        this.classImage.setImage(classImage);
        this.userImage.setImage(userImage);
        this.settingImage.setImage(settingImage);
    }
}
