package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;

import javafx.application.Platform;

import eu.mihosoft.monacofx.MonacoFX;

public class IDEController {

    @FXML
    private AnchorPane backgroundPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private void initialize() {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            backgroundPane.setPrefHeight(1048);
            backgroundPane.setLayoutY(-32);
        }
    }
    
    // @FXML
    // private AnchorPane editorPane;
    //
    // @FXML
    // private void initialize() {
    // }

}
