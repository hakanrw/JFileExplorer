package dev.candar.swing;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;

public class JFileExplorer extends JFrame {

    public static JFileExplorer instance;

    JFLeftPanel leftPanel = new JFLeftPanel();
    JFContentPanel contentPanel = new JFContentPanel();

    Path currentPath;

    public JFileExplorer() {
        super("JFileExplorer");

        leftPanel.fileExplorer = this;
        contentPanel.fileExplorer = this;
        contentPanel.folderViewPanel.fileExplorer = this;
        contentPanel.filePathPanel.fileExplorer = this;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        // contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(contentPanel, BorderLayout.CENTER);

        setPath(Paths.get("").toAbsolutePath());


        setSize(830, 600);
        setMinimumSize(new Dimension(750, 550));
    }

    public void setPath(Path path) {
        currentPath = path;
        
        leftPanel.setPath(path);
        contentPanel.setPath(path);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                instance = new JFileExplorer();
                instance.setVisible(true);
            }
        });
    }
}