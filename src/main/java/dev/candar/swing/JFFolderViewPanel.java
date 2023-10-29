package dev.candar.swing;

import javax.swing.*;

import dev.candar.swing.Utils.OS;

import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

class JFFolderViewPanel extends JPanel {
	JFileExplorer fileExplorer;

	JFFile[] fileViews;
	File[] filesToBeCopied;

	JFActionMenu folderActionMenu = new JFActionMenu(JFActionMenu.ActionType.FOLDER);
	JFActionMenu singleFileActionMenu = new JFActionMenu(JFActionMenu.ActionType.SINGLE_FILE);
	JFActionMenu multiFilesActionMenu = new JFActionMenu(JFActionMenu.ActionType.MULTI_FILES);

	JFFolderViewPanel() {
		folderActionMenu.folderViewPanel = this;
		singleFileActionMenu.folderViewPanel = this;
		multiFilesActionMenu.folderViewPanel = this;

		setLayout(new FlowLayout(FlowLayout.LEFT));
		UIManager.put("PopupMenu.consumeEventOnClose", Boolean.TRUE);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!e.isControlDown())
					loseAllFileSelects();

				if (e.isPopupTrigger()) {
					loseAllFileSelects();
					showActionMenu(e.getComponent(), e.getX(), e.getY());
				}
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

				@Override
				public void mousePressed(MouseEvent e) {

					if (fileView.selected) {
						// double clicked, maybe add time logic so that double click is quick
						if (e.isPopupTrigger()) {
						} else if (e.isControlDown())
							fileView.onSelectLost();
						else {
							loseAllFileSelects();
							fileView.onSelect();

							openSelectedFile();
						}

					} else {
						// file once clicked
						if (!e.isControlDown())
							loseAllFileSelects();
						fileView.onSelect();
					}

					if (e.isPopupTrigger()) {
						showActionMenu(e.getComponent(), e.getX(), e.getY());
					}

				}

			});

			fileViews[i] = fileView;
			add(fileView);
		}

	}

	JFFile[] getSelectedFiles() {
		ArrayList<JFFile> selectedFiles = new ArrayList<JFFile>();

		for (JFFile file : fileViews)
			if (file.selected)
				selectedFiles.add(file);

		return selectedFiles.toArray(JFFile[]::new);
	}

	void showActionMenu(Component component, int x, int y) {
		JFFile[] selectedFiles = getSelectedFiles();

		if (selectedFiles.length == 0)
			folderActionMenu.show(component, x, y);
		if (selectedFiles.length == 1)
			singleFileActionMenu.show(component, x, y);
		if (selectedFiles.length >= 2)
			multiFilesActionMenu.show(component, x, y);

	}

	void openSelectedFile() {
		JFFile[] selectedFiles = getSelectedFiles();

		if (selectedFiles.length != 1)
			throw new Error("openSelectedFile has more than one file or no files!");

        File file = selectedFiles[0].file;

		if (file.isDirectory()) {
            fileExplorer.setPath(file.toPath());
		} else {
			try {
				Desktop.getDesktop().open(file);
			} catch (Exception exception) {
				exception.printStackTrace();
				// TODO: handle exception
			}
		}
	}

    void showCreateFolderPrompt() {
        String folderName = JOptionPane.showInputDialog(this, "Folder name:");
        if (folderName == null || folderName == "") return;

        boolean created = new File(fileExplorer.currentPath.toFile(), folderName).mkdir();

        if (!created) JOptionPane.showMessageDialog(null, "Folder could not be created", "Error", JOptionPane.ERROR_MESSAGE);

        fileExplorer.setPath(fileExplorer.currentPath); // reload page
    }

    void showCreateFilePrompt() {
        String fileName = JOptionPane.showInputDialog(this, "File name:");
        if (fileName == null || fileName == "") return;

        try {
            new File(fileExplorer.currentPath.toFile(), fileName).createNewFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File could not be created", "Error", JOptionPane.ERROR_MESSAGE);
        }

        fileExplorer.setPath(fileExplorer.currentPath); // reload page
    }

    void deleteSelectedFiles() {
        JFFile[] files = getSelectedFiles();

        for (JFFile file : files) file.file.delete();

        fileExplorer.setPath(fileExplorer.currentPath); // reload page
    }

	void copySelectedFiles() {
		JFFile[] files = getSelectedFiles();

		filesToBeCopied = Arrays.stream(files).map(jfFile -> jfFile.file).toArray(File[]::new);
		System.out.println(filesToBeCopied.length);
	}

	void pasteFiles() {
		if (filesToBeCopied == null) return;

		try {
			for (File fileToCopy : filesToBeCopied) {
				Files.copy(fileToCopy.toPath(), fileExplorer.currentPath.resolve(fileToCopy.getName()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileExplorer.setPath(fileExplorer.currentPath); // reload page
	}

    void launchTerminal() {
        OS os = Utils.getOS();

        if (os == OS.WINDOWS) {
            try {
                Runtime.getRuntime().exec("cmd /c start cmd.exe", null, fileExplorer.currentPath.toFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (os == OS.LINUX || os == OS.SOLARIS) {
            String emulator = null;

            if (new File("/usr/bin/xterm").exists()) emulator = "/usr/bin/xterm";
            if (new File("/usr/bin/x-terminal-emulator").exists()) emulator = "/usr/bin/x-terminal-emulator";
            if (new File("/usr/bin/gnome-terminal").exists()) emulator = "/usr/bin/gnome-terminal";

            try {
                if (emulator == null) throw new Error("No terminal emulator found");
                Runtime.getRuntime().exec(emulator, null, fileExplorer.currentPath.toFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (os == OS.MAC) {
            try {
                Runtime.getRuntime().exec("/usr/bin/open -a Terminal .", null, fileExplorer.currentPath.toFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

}