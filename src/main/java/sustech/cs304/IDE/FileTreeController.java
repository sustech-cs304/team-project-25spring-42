package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileTreeController {

    @FXML
    private TreeView<String> treeView;

    private TreeItem<String> rootItem;

    @FXML
    private void initialize() {
        rootItem = new TreeItem<>("Root");
        rootItem.setExpanded(true);

        treeView.setRoot(rootItem);
    }

    protected void handleSelectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Folder");

        Stage primaryStage = (Stage) treeView.getScene().getWindow();

        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        rootItem.setValue(selectedDirectory.getName());
        if (selectedDirectory != null) {
            rootItem.getChildren().clear();
            buildFileTree(rootItem, selectedDirectory);
        }
        rootItem.setExpanded(true);
    }

    private void buildFileTree(TreeItem<String> parentItem, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                TreeItem<String> fileItem = new TreeItem<>(file.getName());

                if (file.isDirectory()) {
                    buildFileTree(fileItem, file);
                }
                parentItem.getChildren().add(fileItem);
            }
        }
    }

}
