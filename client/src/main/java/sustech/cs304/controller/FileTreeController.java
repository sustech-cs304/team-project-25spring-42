package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TreeCell;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.util.List;
import java.nio.charset.Charset;

import sustech.cs304.controller.components.treeItems.DirTreeItem;
import sustech.cs304.controller.components.treeItems.FileTreeItem;
import sustech.cs304.controller.components.treeItems.FileTreeNode;
import sustech.cs304.controller.components.treeItems.TreeCellImpl;
import sustech.cs304.utils.FileUtils;
import java.util.Arrays;
import java.util.Comparator;

public class FileTreeController {

    @FXML
    private TreeView<FileTreeNode> treeView;

    private DirTreeItem rootItem;

    private IDEController ideController;

    private boolean showHiddenFiles = false;

    @FXML
    private void initialize() {
        FileTreeNode rootNode = new FileTreeNode("Folder", null);
        rootItem = new DirTreeItem(rootNode);
        rootItem.setExpanded(true);

        treeView.setRoot(rootItem);
    }

    public void handleSelectFolder() {
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
                    try {
                        openFile(selectedFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        treeView.setCellFactory(new Callback<TreeView<FileTreeNode>,TreeCell<FileTreeNode>>(){
            @Override
            public TreeCell<FileTreeNode> call(TreeView<FileTreeNode> p) {
                return new TreeCellImpl();
            }
        });
    }

    private void buildFileTree(TreeItem<FileTreeNode> parentItem, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    if (file1.isDirectory() && !file2.isDirectory()) {
                        return -1;
                    } else if (!file1.isDirectory() && file2.isDirectory()) {
                        return 1;
                    }
                    return file1.getName().compareToIgnoreCase(file2.getName());
                }
            });

            for (File file : files) {
                if (!showHiddenFiles && FileUtils.ifDotFile(file)) {
                    continue;
                }
                if (file.isDirectory()) {
                    DirTreeItem dirItem = new DirTreeItem(new FileTreeNode(file.getName(), file.getAbsolutePath()));
                    parentItem.getChildren().add(dirItem);
                    buildFileTree(dirItem, file);
                } else {
                    FileTreeItem fileItem = new FileTreeItem(new FileTreeNode(file.getName(), file.getAbsolutePath()));
                    parentItem.getChildren().add(fileItem);
                }
            }
        }
    }

    private void openFile(File file) throws IOException {
        if (file.isDirectory()) {
            return;
        } else {
            ideController.openEditor();
            String extension = FileUtils.getExtension(file);
            if(extension.equals("pdf") ){
                ideController.getEditorController().addPDFPage(file);
            } else if (extension.equals("pptx") || extension.equals("ppt")) {
                ideController.getEditorController().addPPTPage(file);
            } else {
                try {
                    List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("ISO-8859-1"));
                    ideController.getEditorController().addPage(lines, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }
}
