package sustech.cs304.IDE.components.treeItems;

import javafx.scene.control.TreeCell;

public final class TreeCellImpl extends TreeCell<FileTreeNode> {

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
