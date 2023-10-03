package dev.candar.swing;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

import javax.swing.*;

class JFContentPanel extends JPanel {
	JFileExplorer fileExplorer;

	JFFilePathPanel filePathPanel = new JFFilePathPanel();
	JFFolderViewPanel folderViewPanel = new JFFolderViewPanel();

	JFContentPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		gc.gridy = 0;
		gc.weightx = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;

		add(filePathPanel, gc);

		gc.gridy = 1;

		Container margin = new Container();
		margin.setPreferredSize(new Dimension(0, 5));
		margin.setMinimumSize(new Dimension(0, 5));
		add(margin, gc);

		gc.gridy = 2;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;

		add(folderViewPanel, gc);
	}

	void setPath(Path path) {
		filePathPanel.setPath(path);
		folderViewPanel.setPath(path);
	}

}

class JFFilePathPanel extends JPanel {
	JFileExplorer fileExplorer;

	JFFilePathPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBackground(Resources.primaryColor);
		setBorder(BorderFactory.createLineBorder(Resources.accentColor));

	}

	void setPath(Path path) {
		removeAll();

        Path[] paths = new Path[path.getNameCount()];

        Path traversedPath = path;
        
        for(int i = path.getNameCount() - 1; i >= 0; i--) {
            paths[i] = traversedPath;

            traversedPath = traversedPath.getParent();
        }

        if (paths.length == 0) {
            add(new JLabel(FileSystems.getDefault().getSeparator()));
        }


		for (Path p : paths) {
			add(new JLabel(FileSystems.getDefault().getSeparator()));
            
            JLabel pathLabel = new JLabel(p.getFileName().toString());
            pathLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    pathLabel.setOpaque(true);
                    pathLabel.setBackground(Resources.secondaryAccentColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    pathLabel.setOpaque(false);
                    pathLabel.setBackground(null);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    fileExplorer.setPath(p.toAbsolutePath());
                }
            });

			add(pathLabel);
		}
	}

}

class JFFolderViewPanel extends JPanel {
	JFileExplorer fileExplorer;

	JFFile[] fileViews;

	JFFolderViewPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                loseAllFileSelects();
			}
		});
	}

	void loseAllFileSelects() {
		for (JFFile fileView : fileViews) {
			fileView.onSelectLost();
		}
	}

	void setPath(Path path) {
        removeAll();

		File folder = path.toFile();
		File[] files = Arrays.stream(folder.listFiles()).filter(file -> !file.isHidden()).toArray(File[]::new); 

        Arrays.sort(files); 

		fileViews = new JFFile[files.length];

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			JFFile fileView = new JFFile(file);

			fileView.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					fileView.onHover();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					fileView.onHoverLost();
				}

				public void mouseClicked(MouseEvent e) {
                    if (fileView.selected) {
                        // double clicked, maybe add time logic so that double click is quick

                        if (file.isDirectory())
                            fileExplorer.setPath(file.toPath().toAbsolutePath());
                        else 
                            try {
                                Desktop.getDesktop().open(file);
                            } catch (Exception exception) {
                                // TODO: handle exception
                            }

                    } else {
                        loseAllFileSelects();
                        fileView.onSelect();
                    }
				}

			});

			fileViews[i] = fileView;
			add(fileView);
		}

	}

}