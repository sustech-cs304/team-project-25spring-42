package sustech.cs304.IDE;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sustech.cs304.IDE.components.treeItems.ProviderTreeItem;
import sustech.cs304.IDE.components.treeItems.FileTreeNode;
import sustech.cs304.IDE.components.treeItems.TreeCellImpl;
import javafx.util.Callback;
import javafx.scene.control.TreeCell;
import sustech.cs304.pdfReader.pdfReaderController;

import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.util.List;
import java.nio.charset.Charset;

public class FileTreeController {

    @FXML
    private TreeView<FileTreeNode> treeView;

    private ProviderTreeItem rootItem;

    private EditorController editorController;

    public pdfReaderController getMYpdfReaderController() {
        return MYpdfReaderController;
    }

    public void setMYpdfReaderController(pdfReaderController MYpdfReaderController) {
        this.MYpdfReaderController = MYpdfReaderController;
    }

    private pdfReaderController MYpdfReaderController;

    @FXML
    private void initialize() {
        FileTreeNode rootNode = new FileTreeNode("Folder", null);
        rootItem = new ProviderTreeItem(rootNode);
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
                    openFile(selectedFile);
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

    public void setEditorController(EditorController editorController) {
        this.editorController = editorController;
    }

    private void buildFileTree(TreeItem<FileTreeNode> parentItem, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                ProviderTreeItem fileItem = new ProviderTreeItem(new FileTreeNode(file.getName(), file.getAbsolutePath()));
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
            String extension = getExtension(file);
            if(extension.equals("pdf") ){
                MYpdfReaderController.getFile(file);


            }else{

                try {
                    List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("ISO-8859-1"));
                    editorController.setText(lines);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private String getExtension(File file){
        String fileName = file.getName();
        String extension = "";
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) { // 确保文件名中包含 "."
            extension = fileName.substring(lastDotIndex + 1);
        }
        return extension;

    }
}
