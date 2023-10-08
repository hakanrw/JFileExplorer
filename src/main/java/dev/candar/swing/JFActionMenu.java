package dev.candar.swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.MenuDragMouseEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

class JFActionMenu  extends JPopupMenu {
    boolean visible = false;

    static enum ActionType {
        FOLDER,
        SINGLE_FILE,
        MULTI_FILES
    }

    JFFolderViewPanel folderViewPanel;

    JMenuItem newFolderItem = new JMenuItemFixed("New folder");
    JMenuItem newFileItem = new JMenuItemFixed("New file");
    JMenuItem openInTerminalItem = new JMenuItemFixed("Open in terminal");

    JMenuItem openItem = new JMenuItemFixed("Open");
    JMenuItem renameItem = new JMenuItemFixed("Rename");
    JMenuItem cutItem = new JMenuItemFixed("Cut");
    JMenuItem copyItem = new JMenuItemFixed("Copy");
    JMenuItem deleteItem = new JMenuItemFixed("Delete");

    public JFActionMenu(ActionType type) {

        if (type == ActionType.FOLDER) {
            add(newFolderItem);
            add(newFileItem);
            addSeparator();
            add(openInTerminalItem);
        }

        if (type == ActionType.SINGLE_FILE) {
            add(openItem);
            addSeparator();
            add(cutItem);
            add(copyItem);
            addSeparator();
            add(renameItem);
            addSeparator();
            add(deleteItem);
        }

        if (type == ActionType.MULTI_FILES) {
            add(cutItem);
            add(copyItem);
            addSeparator();
            add(deleteItem);
        }

        openItem.addActionListener((action) -> {
            folderViewPanel.openSelectedFile();
        });
        
    }
}

class JMenuItemFixed extends JMenuItem {

    JMenuItemFixed(String text) {
        super(text);
    }

    // without this, when menu item is clicked, it will click the component under it as well
    // because swing is stupid
    @Override
    public void processMenuDragMouseEvent(MenuDragMouseEvent evt) {
        if (evt.getID() == MouseEvent.MOUSE_RELEASED) { 
            return;
        }
        super.processMenuDragMouseEvent(evt);
    }
    

}