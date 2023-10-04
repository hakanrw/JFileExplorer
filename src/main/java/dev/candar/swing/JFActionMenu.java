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


    public JFActionMenu() {
        JMenuItem openItem = new JMenuItemFixed("Open");
        JMenuItem renameItem = new JMenuItemFixed("Rename");
        JMenuItem cutItem = new JMenuItemFixed("Cut");
        JMenuItem copyItem = new JMenuItemFixed("Copy");
        JMenuItem deleteItem = new JMenuItemFixed("Delete");
        add(openItem);
        addSeparator();
        add(cutItem);
        add(copyItem);
        addSeparator();
        add(renameItem);
        addSeparator();
        add(deleteItem);

        
        
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