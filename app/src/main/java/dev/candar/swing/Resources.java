package dev.candar.swing;

import java.awt.Color;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.UIManager;

class Resources {
    public static Color accentColor = Color.decode("#7A8A99");
    public static Color secondaryAccentColor = Color.decode("#C9DBEB");
    public static Color primaryColor = Color.decode("#E3ECF5");
    
    public static Font bigFont = new Font("Calibri", Font.PLAIN,  16);
    public static Font smallFont = new Font("Calibri", Font.PLAIN,  11);

    public static Icon dirIcon = UIManager.getIcon("FileView.directoryIcon");
    public static Icon fileIcon = UIManager.getIcon("FileView.fileIcon");
    public static Icon homeIcon = UIManager.getIcon("FileChooser.homeFolderIcon");
    public static Icon saveIcon = UIManager.getIcon("FileView.floppyDriveIcon");
    public static Icon computerIcon = UIManager.getIcon("FileView.computerIcon");
    public static Icon diskIcon = UIManager.getIcon("FileView.hardDriveIcon");

}
