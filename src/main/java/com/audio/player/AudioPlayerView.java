package com.audio.player;

import javax.swing.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The view is responsible for displaying the user interface and capturing
 * user interactions, delegating the actual business logic to the controller.
 */
public class AudioPlayerView extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(AudioPlayerView.class.getName());
    private AudioPlayerController controller;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton fastForwardButton;
    private JButton rewindButton;
    private JButton openFileButton;

    /**
     * Constructor that initializes the view with a reference to the controller.
     *
     * @param controller The controller that handles business logic for the audio
     *                   player.
     */
    public AudioPlayerView(AudioPlayerController controller) {
        this.controller = controller;
        initUI();
    }

    /**
     * Initializes the user interface components and layouts.
     */
    private void initUI() {
        setTitle("Audio Player");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        playButton = createIconButton("play.png", "Play");
        pauseButton = createIconButton("pause.png", "Pause");
        stopButton = createIconButton("stop.png", "Stop");
        fastForwardButton = createIconButton("fast_forward.png", "Fast Forward");
        rewindButton = createIconButton("rewind.png", "Rewind");
        openFileButton = createIconButton("open_file.png", "Open File");

        // Layout for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openFileButton);
        buttonPanel.add(rewindButton);
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(fastForwardButton);

        // Action Listeners
        openFileButton.addActionListener(e -> openFile());
        playButton.addActionListener(e -> controller.play());
        pauseButton.addActionListener(e -> controller.pause());
        stopButton.addActionListener(e -> controller.stop());
        fastForwardButton.addActionListener(e -> controller.fastForward(5000000)); // Fast forward 5 seconds
        rewindButton.addActionListener(e -> controller.rewind(5000000)); // Rewind 5 seconds

        // Add button panel to the frame
        add(buttonPanel);

        // Pack and set visible
        pack();
        setVisible(true);
    }

    /**
     * Opens a file chooser dialog to select an audio file and instructs the
     * controller to load the selected file.
     */
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            LOGGER.log(Level.INFO, "Selected file: {0}", selectedFile.getAbsolutePath());
            controller.loadFile(selectedFile);
        } else if (result == JFileChooser.CANCEL_OPTION) {
            LOGGER.fine("File selection cancelled by user.");
        }
    }

    /**
     * Creates a JButton with an icon and tooltip. If the icon cannot be loaded,
     * the button will display the tooltip text instead.
     *
     * @param iconName The name of the icon file to be used as the button image.
     * @param toolTip  The text to be displayed when the mouse hovers over the
     *                 button.
     * @return A JButton instance with either an icon or text label.
     */
    private JButton createIconButton(String iconName, String toolTip) {
        String iconPath = "/icons/" + iconName;
        ImageIcon icon = null;
        try {
            java.net.URL imgUrl = getClass().getResource(iconPath);
            if (imgUrl != null) {
                icon = new ImageIcon(imgUrl);
            } else {
                LOGGER.log(Level.WARNING, "Icon resource not found: {0}", iconPath);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading icon: " + iconPath, e);
        }

        JButton button = new JButton();
        if (icon != null) {
            button.setIcon(icon);
        } else {
            // Fallback to text if the icon cannot be loaded
            button.setText(toolTip);
            LOGGER.log(Level.INFO, "Using text as fallback for missing icon: {0}", toolTip);
        }

        button.setToolTipText(toolTip);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

}
