
package dev.candar.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.AccessControlException;
import java.util.Dictionary;

import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;        

public class JFileExplorer {
    public static Font bigFont = new Font("Calibri", Font.PLAIN,  16);
    public static Font smallFont = new Font("Calibri", Font.PLAIN,  11);

    public static Icon dirIcon = UIManager.getIcon("FileView.directoryIcon");
    public static Icon fileIcon = UIManager.getIcon("FileView.fileIcon");
    public static Icon homeIcon = UIManager.getIcon("FileChooser.homeFolderIcon");
    public static Icon saveIcon = UIManager.getIcon("FileView.floppyDriveIcon");
    public static Icon computerIcon = UIManager.getIcon("FileView.computerIcon");
    public static Icon diskIcon = UIManager.getIcon("FileView.hardDriveIcon");



    public static JFrame frame;

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("JFileExplorer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        // contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        createLeftPane(contentPane);

        frame.setSize(850, 600);
        frame.setMinimumSize(new Dimension(750, 550));
        frame.setVisible(true);
    }

    private static void createLeftPane(Container contentPane) {
        JPanel outerGridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcOuter = new GridBagConstraints();

        outerGridPanel.setMinimumSize(new Dimension(200, 0));
        outerGridPanel.setPreferredSize(new Dimension(200, 0));

        gbcOuter.fill = GridBagConstraints.HORIZONTAL;
        gbcOuter.weightx = 1;
        gbcOuter.weighty = 0;
        gbcOuter.gridy = 0;

        JButton fileExplorerLabel = new JButton("JFileExplorer");
        fileExplorerLabel.setFocusPainted(false);
        fileExplorerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        fileExplorerLabel.setFont(bigFont);

        fileExplorerLabel.setIcon(new ImageIcon(Utils.iconToImage(dirIcon).getScaledInstance(20, 20, Image.SCALE_FAST)));
        fileExplorerLabel.setIconTextGap(10);
        
        outerGridPanel.add(fileExplorerLabel, gbcOuter);


        gbcOuter.weightx = 1;
        gbcOuter.weighty = 1;
        gbcOuter.gridy = 1;
        gbcOuter.fill = GridBagConstraints.BOTH;

        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        leftPanel.setBackground(Color.decode("#E3ECF5"));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#7A8A99")));

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy = 0;

        JPanel margin = new JPanel();
        margin.setPreferredSize(new Dimension(0, 20));
        margin.setOpaque(false);

        leftPanel.add(margin, gc);


        Object[][] entries = {
            {"Home",      homeIcon,     },
            {"Documents", fileIcon      },
            {"Downloads", saveIcon      },
            {"Computer",  computerIcon  },
        };

        for (int i = 0; i < entries.length; i++) {
            gc.weightx = 1;
            gc.gridy = i * 2 + 1;

            JButton button = new JButton((String) entries[i][0], (Icon) entries[i][1]);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setIconTextGap(10);
            button.setPreferredSize(new Dimension(0, 30));
            leftPanel.add(button, gc);


            gc.gridy += 1;

            JPanel bMargin = new JPanel();
            bMargin.setPreferredSize(new Dimension(0, 15));
            bMargin.setOpaque(false);

            leftPanel.add(bMargin, gc);
        }

        gc.weighty = 1;
        gc.gridy += 1;

        leftPanel.add(new Container(), gc);

        gc.weighty = 0;
        gc.gridy += 1;

        JPanel spacePanelOuter = new JPanel();
        spacePanelOuter.setBackground(Color.decode("#E3ECF5"));
        spacePanelOuter.setBorder(BorderFactory.createLineBorder(Color.decode("#7A8A99")));
        spacePanelOuter.setPreferredSize(new Dimension(0, 75));
        spacePanelOuter.setLayout(new BorderLayout());

        JPanel spacePanel = new JPanel();
        spacePanel.setLayout(new BoxLayout(spacePanel, BoxLayout.Y_AXIS));
        spacePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        spacePanel.setOpaque(false);

        JLabel avSpace = new JLabel("Disk Space:");
        avSpace.setIcon(diskIcon);
        avSpace.setIconTextGap(10);
        spacePanel.add(avSpace);

        margin = new JPanel();
        margin.setPreferredSize(new Dimension(0, 8));
        margin.setMaximumSize(new Dimension(0, 8));
        spacePanel.add(margin);

        JProgressBar diskProgress = new JProgressBar();
        diskProgress.setValue((int) ( (45. / 95.) * 100 ) );
        diskProgress.setMaximum(100);
        diskProgress.setString("45GB / 95GB");
        diskProgress.setStringPainted(true);
        spacePanel.add(diskProgress);

        spacePanelOuter.add(spacePanel, BorderLayout.CENTER);
        leftPanel.add(spacePanelOuter, gc);

        gc.gridy += 1;

        margin = new JPanel();
        margin.setPreferredSize(new Dimension(0, 20));
        margin.setOpaque(false);

        leftPanel.add(margin, gc);


        outerGridPanel.add(leftPanel, gbcOuter);
        contentPane.add(outerGridPanel, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}