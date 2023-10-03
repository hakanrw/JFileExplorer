package dev.candar.swing;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

class JFLeftPanel extends JPanel {
    JFileExplorer fileExplorer;

    JFLeftPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbcOuter = new GridBagConstraints();

        setMinimumSize(new Dimension(200, 0));
        setPreferredSize(new Dimension(200, 0));

        gbcOuter.fill = GridBagConstraints.HORIZONTAL;
        gbcOuter.weightx = 1;
        gbcOuter.weighty = 0;
        gbcOuter.gridy = 0;

        JButton fileExplorerLabel = new JButton("JFileExplorer");
        fileExplorerLabel.setFocusPainted(false);
        fileExplorerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        fileExplorerLabel.setFont(Resources.bigFont);
        fileExplorerLabel.setIcon(
            new ImageIcon(Utils.iconToImage(Resources.dirIcon).getScaledInstance(20, 20, Image.SCALE_FAST))
        );
        fileExplorerLabel.setIconTextGap(10);
        
        add(fileExplorerLabel, gbcOuter);


        gbcOuter.weightx = 1;
        gbcOuter.weighty = 1;
        gbcOuter.gridy = 1;
        gbcOuter.fill = GridBagConstraints.BOTH;

        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        leftPanel.setBackground(Resources.primaryColor);
        leftPanel.setBorder(BorderFactory.createLineBorder(Resources.accentColor));

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy = 0;

        JPanel margin = new JPanel();
        margin.setPreferredSize(new Dimension(0, 20));
        margin.setOpaque(false);

        leftPanel.add(margin, gc);


        Object[][] entries = {
            {"Home",      Resources.homeIcon,     },
            {"Documents", Resources.fileIcon      },
            {"Downloads", Resources.saveIcon      },
            {"Computer",  Resources.computerIcon  },
        };

        for (int i = 0; i < entries.length; i++) {
            gc.weightx = 1;
            gc.gridy = i * 2 + 1;

            JButton button = new JButton((String) entries[i][0], (Icon) entries[i][1]);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setIconTextGap(10);
            button.setPreferredSize(new Dimension(0, 30));
            button.addActionListener((action) -> {
                fileExplorer.setPath(Paths.get("/home/hakan"));
            });

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
        spacePanelOuter.setBackground(Resources.primaryColor);
        spacePanelOuter.setBorder(BorderFactory.createLineBorder(Resources.accentColor));
        spacePanelOuter.setPreferredSize(new Dimension(0, 75));
        spacePanelOuter.setLayout(new BorderLayout());

        JPanel spacePanel = new JPanel();
        spacePanel.setLayout(new BoxLayout(spacePanel, BoxLayout.Y_AXIS));
        spacePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        spacePanel.setOpaque(false);

        JLabel avSpace = new JLabel("Disk Space:");
        avSpace.setIcon(Resources.diskIcon);
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


        add(leftPanel, gbcOuter);
    }

    void setPath(Path path) {
        
    }
  
}
