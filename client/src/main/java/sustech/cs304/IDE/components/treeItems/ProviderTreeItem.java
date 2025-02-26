package sustech.cs304.IDE.components.treeItems;

import javafx.scene.control.TreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class ProviderTreeItem extends AbstractTreeItem {

    public ProviderTreeItem(FileTreeNode fileTreeNode) {
        this.setValue(fileTreeNode);
    }
    
    @Override
    public ContextMenu getMenu() {
        MenuItem addInbox = new MenuItem("add inbox");
        addInbox.setOnAction(event -> {
            BoxTreeItem newBox = new BoxTreeItem(new FileTreeNode("inbox", "inbox"));
            getChildren().add(newBox);
        });
        return new ContextMenu(addInbox);
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
