package com.audio.player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FileMenu class represents the menu for file operations in the audio player.
 * It allows users to open new audio files and exit the application.
 */
public class FileMenu extends JMenu {
    private static final Logger LOGGER = Logger.getLogger(FileMenu.class.getName());
    private final AudioPlayerController controller;

    /**
     * Constructs a FileMenu with a reference to the audio player controller.
     *
     * @param controller The controller handling the audio player's business logic.
     */
    public FileMenu(AudioPlayerController controller) {
        super("File");
        this.controller = controller;
        initializeMenu();
    }

    /**
     * Initializes the menu items and their action listeners.
     */
    private void initializeMenu() {
        JMenuItem openItem = new JMenuItem("Open File");
        openItem.addActionListener(e -> openFile());
        add(openItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            LOGGER.log(Level.INFO, "Exiting application");
            System.exit(0);
        });
        add(exitItem);
    }

    /**
     * Opens a file chooser dialog to select an audio file and instructs the
     * controller to load the selected file. Logs the selection process.
     */
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();

        // Set up a file filter for supported audio formats
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Audio Files", "wav");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            LOGGER.log(Level.INFO, "Selected file: {0}", selectedFile.getAbsolutePath());
            controller.loadFile(selectedFile);
        } else if (result == JFileChooser.CANCEL_OPTION) {
            LOGGER.log(Level.FINE, "File selection cancelled by user.");
        }
    }
}
