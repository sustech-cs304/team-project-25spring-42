package sustech.cs304.controller.components.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class DirTreeItem extends AbstractTreeItem {

    public DirTreeItem(FileTreeNode fileTreeNode) {
        this.setValue(fileTreeNode);
    }
    
    @Override
    public ContextMenu getMenu() {
        MenuItem copy = new MenuItem("Copy");
        MenuItem rename = new MenuItem("Rename");
        MenuItem newFile = new MenuItem("New File");
        copy.setOnAction(event -> {
        });
        rename.setOnAction(event -> {
        });
        newFile.setOnAction(event -> {
        });
        return new ContextMenu(copy, rename, newFile);
    }
}
