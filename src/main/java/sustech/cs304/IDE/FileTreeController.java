package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.util.List;
import java.nio.charset.Charset;

public class FileTreeController {

    @FXML
    private TreeView<FileTreeNode> treeView;

    private TreeItem<FileTreeNode> rootItem;

    private EditorController editorController;

    @FXML
    private void initialize() {
        FileTreeNode rootNode = new FileTreeNode("Folder", null);
        rootItem = new TreeItem<>(rootNode);
        rootItem.setExpanded(true);

        treeView.setRoot(rootItem);
    }

    protected void handleSelectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Folder");

        Stage primaryStage = (Stage) treeView.getScene().getWindow();

        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            rootItem.setValue(new FileTreeNode(selectedDirectory.getName(), selectedDirectory.getAbsolutePath()));
            rootItem.getChildren().clear();
            buildFileTree(rootItem, selectedDirectory);
        }
        rootItem.setExpanded(true);

        treeView.setOnMouseClicked(event -> {
            TreeItem<FileTreeNode> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                FileTreeNode node = selectedItem.getValue();
                File selectedFile = new File(node.getPath());
                if (selectedFile.exists()) {
                    openFile(selectedFile);
                }
            }
        });
    }

    private void buildFileTree(TreeItem<FileTreeNode> parentItem, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                FileTreeNode node = new FileTreeNode(file.getName(), file.getAbsolutePath());
                TreeItem<FileTreeNode> fileItem = new TreeItem<>(node);

                if (file.isDirectory()) {
                    buildFileTree(fileItem, file);
                }
                parentItem.getChildren().add(fileItem);
            }
        }
    }

    private void openFile(File file) {
        if (file.isDirectory()) {
            return;
        } else {
            try {
                Charset targetCharset = Charset.forName("UTF-8");
                List<String> lines = Files.readAllLines(file.toPath(), targetCharset);
                editorController.setText(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setEditorController(EditorController editorController) {
        this.editorController = editorController;
    }

}

class FileTreeNode {
    private String name;
    private String path;

    public FileTreeNode(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return name;
    }
}
