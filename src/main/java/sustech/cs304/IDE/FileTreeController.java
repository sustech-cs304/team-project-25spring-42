package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.control.TreeCell;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class FileTreeController {

    @FXML
    private TreeView<FileTreeNode> treeView;

    private TreeItem<FileTreeNode> rootItem;

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

        // 为 TreeView 设置鼠标点击事件
        treeView.setOnMouseClicked(event -> {
            TreeItem<FileTreeNode> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                FileTreeNode node = selectedItem.getValue();  // 获取 FileTreeNode 对象
                File selectedFile = new File(node.getPath());  // 通过完整路径查找文件
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

    private File findFileByName(String name, File parentDir) {
        File[] files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File found = findFileByName(name, file);
                    if (found != null) {
                        return found;
                    }
                } else if (file.getName().equals(name)) {
                    return file;
                }
            }
        }
        return null;
    }

    // 打开文件的方法
    private void openFile(File file) {
        if (file.isDirectory()) {
            return;
        } else {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

class FileTreeNode {
    private String name;   // 文件名
    private String path;   // 文件完整路径

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
        return name;  // 在树形视图中显示文件名
    }
}
