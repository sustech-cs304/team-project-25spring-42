package sustech.cs304.IDE.components;

import javafx.scene.control.TreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.event.Event;


public class ProviderTreeItem extends AbstractTreeItem {
    
    public ProviderTreeItem(String name, String path) {
        this.setValue(new FileTreeNode(name, path));
    }

    @Override
    public ContextMenu getMenu(){
        MenuItem addInbox = new MenuItem("add inbox");
        addInbox.setOnAction(new EventHandler() {
            public void handle(Event t) {
                BoxTreeItem newBox = new BoxTreeItem("inbox");
                    getChildren().add(newBox);
            }
        });
        return new ContextMenu(addInbox);
    }
}

final class TreeCellImpl extends TreeCell<FileTreeNode> {

    @Override
    public void updateItem(FileTreeNode fileTreeNode, boolean empty) {
        super.updateItem(fileTreeNode, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem() == null ? "" : getItem().toString());
            setGraphic(getTreeItem().getGraphic());
            setContextMenu(((AbstractTreeItem) getTreeItem()).getMenu());
        }
    }
}

abstract class AbstractTreeItem extends TreeItem<FileTreeNode> {
    public abstract ContextMenu getMenu();
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
