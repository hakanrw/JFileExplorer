package dev.candar.swing;

import javax.swing.*;
import java.awt.*;
import java.io.File;

class JFFile extends JPanel {

    File file;

    boolean hover = false;
    boolean selected = false;

    JFFile(File file) {
        this(file, false);
    }

    JFFile(File file, boolean dim) {
        this.file = file;
        
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(80, 110));
        setBorder(BorderFactory.createEmptyBorder(10, 2, 10, 2));
        GridBagConstraints gc = new GridBagConstraints();

        boolean isDirectory = file.isDirectory();

        gc.gridy = 1;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel icon = new JLabel( 
            (dim ?
                isDirectory ? Resources.folderCutImageIcon : Resources.fileCutImageIcon :
                isDirectory ? Resources.folderImageIcon : Resources.fileImageIcon)
        );

        add(icon, gc);

        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;

        JLabel label = new JLabel(file.getName(), SwingConstants.CENTER);

        if (dim) {
            label.setForeground(new Color(0, 0, 0, 120));
        }

        add(label, gc);
    }

    void onHover() {
        hover = true;
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Resources.accentColor), 
            BorderFactory.createEmptyBorder(9, 2, 9, 2)));
    }

    void onHoverLost() {
        hover = false;
        if (selected) return;

        setBorder(BorderFactory.createEmptyBorder(10, 2, 10, 2));
        setBackground(null);
    }

    void onSelect() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Resources.accentColor), 
            BorderFactory.createEmptyBorder(9, 2, 9, 2)));
        setBackground(Resources.secondaryAccentColor);

        selected = true;
    }

    void onSelectLost() {
        setBorder(BorderFactory.createEmptyBorder(10, 2, 10, 2));
        setBackground(null);
        selected = false;

        if (hover) onHover();
        else onHoverLost();
    }
  
}
