package sustech.cs304.controller.components.treeItems;

import javafx.scene.control.TreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class FileTreeItem extends AbstractTreeItem {

    public FileTreeItem(FileTreeNode fileTreeNode) {
        this.setValue(fileTreeNode);
    }
    
    @Override
    public ContextMenu getMenu() {
        MenuItem copy = new MenuItem("Copy");
        MenuItem rename = new MenuItem("Rename");
        copy.setOnAction(event -> {
        });
        rename.setOnAction(event -> {
        });
        return new ContextMenu(copy, rename);
    }
}

abstract class AbstractTreeItem extends TreeItem<FileTreeNode> {
    public abstract ContextMenu getMenu();
}

class BoxTreeItem extends AbstractTreeItem{
    public BoxTreeItem(FileTreeNode fileTreeNode) {
        this.setValue(fileTreeNode);
    }

    @Override
    public ContextMenu getMenu() {
        return new ContextMenu(new MenuItem("test"));
    }
}
