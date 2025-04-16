package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import sustech.cs304.App;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Hyperlink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IDEController {
    @FXML
    private AnchorPane backgroundPane, modePane, editorPane, profilePane;

    List<Node> ideContent;
    private Parent classContent, userHomeContent, chatContent;

    @FXML
    private FileTreeController fileTreeController;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private EditorController editorController;

    @FXML
    private PDFReaderController pdfReaderController;

    @FXML
    private JeditermController jeditermController;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Hyperlink openLink;

    @FXML
    private ImageView codeImage, chatImage, classImage, userImage, settingImage;

    private boolean isDragging = false;
    private double initialY;
    private double initialHeight;
    private static final double DRAG_THRESHOLD = 10.0;
    private AnchorPane terminalBackPane;
    private TabPane editorTabPane;

    @FXML
    private void initialize() {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            backgroundPane.setPrefHeight(1048);
            backgroundPane.setLayoutY(-32);
        }

        this.terminalBackPane = (AnchorPane) backgroundPane.lookup("#terminalBackPane");
        this.editorTabPane = (TabPane) backgroundPane.lookup("#editorTabPane");

        menuBarController.setIdeController(this);
        fileTreeController.setIdeController(this);
        editorController.setIdeController(this);
        jeditermController.setIdeController(this);

        editorController.setBackground("vs");

        ideContent = new ArrayList<>(modePane.getChildren());
        try {
            classContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/class.fxml")));
            userHomeContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/UserHome.fxml")));
            chatContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/chatWelcome.fxml")));
        } catch(IOException e) {
            e.printStackTrace();
        }

        App.primaryStage.setOnCloseRequest(event -> {
            jeditermController.close();
        });

        switchToEditor();
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

    public PDFReaderController getMYpdfReaderController() {
        return pdfReaderController;
    }

    public JeditermController getJeditermController() {
        return jeditermController;
    }

    @FXML
    private void switchToEditor() {
        menuBarController.changeMode("editor");
        modePane.getChildren().clear();
        modePane.getChildren().addAll(ideContent);
    }

    @FXML
    private void switchToChat() {
        menuBarController.changeMode("chat");
        modePane.getChildren().clear();
        modePane.getChildren().addAll(chatContent);
        AnchorPane.setTopAnchor(chatContent, 0.0);
        AnchorPane.setBottomAnchor(chatContent, 0.0);
        AnchorPane.setLeftAnchor(chatContent, 0.0);
        AnchorPane.setRightAnchor(chatContent, 0.0);
    }

    @FXML
    private void switchToClass() {
        menuBarController.changeMode("class");
        modePane.getChildren().clear();
        modePane.getChildren().addAll(classContent);
        AnchorPane.setTopAnchor(classContent, 0.0);
        AnchorPane.setBottomAnchor(classContent, 0.0);
        AnchorPane.setLeftAnchor(classContent, 0.0);
        AnchorPane.setRightAnchor(classContent, 0.0);
    }

    @FXML
    private void switchToUserhome() {
        menuBarController.changeMode("userHome");
        modePane.getChildren().clear();
        modePane.getChildren().addAll(userHomeContent);
        AnchorPane.setTopAnchor(userHomeContent, 0.0);
        AnchorPane.setBottomAnchor(userHomeContent, 0.0);
        AnchorPane.setLeftAnchor(userHomeContent, 0.0);
        AnchorPane.setRightAnchor(userHomeContent, 0.0);
    }

    public void changeImageColor(String theme) {
        if ("vs-dark".equals(theme) || "hc-black".equals(theme)) {
            theme = "white";
        } else if ("vs".equals(theme)) {
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

    public void checkIfDragging(MouseEvent event) {
        if (event.getY() <= DRAG_THRESHOLD) {
            isDragging = true;
            initialY = event.getSceneY();
            initialHeight = terminalBackPane.getPrefHeight();
            event.consume();
        }
    }

    public void dragTerminal(MouseEvent event) {
        if (isDragging) {
            double deltaY = initialY - event.getSceneY();
            double newHeight = Math.max(0, Math.min(initialHeight + deltaY, editorPane.getHeight()));
            terminalBackPane.setPrefHeight(newHeight);
            AnchorPane.setBottomAnchor(editorTabPane, newHeight);
            event.consume();
        }
    }

    public void openTerminal() {
        terminalBackPane.setVisible(true);
        double terminalHeight = terminalBackPane.getPrefHeight();
        AnchorPane.setBottomAnchor(editorTabPane, terminalHeight);
    }

    public void closeTerminal() {
        terminalBackPane.setVisible(false);
        AnchorPane.setBottomAnchor(editorTabPane, 0.0);
    }
}
