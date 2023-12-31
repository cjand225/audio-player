package com.audio.player;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
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
    private ProgressSlider progressSlider;

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

        // Initialize the slider
        progressSlider = new ProgressSlider(controller);

        // Create and add the menu bar
        JMenuBar menuBar = new JMenuBar();
        FileMenu fileMenu = new FileMenu(controller);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Layout for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(rewindButton);
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(fastForwardButton);

        // Layout for Overall
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(progressSlider, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        playButton.addActionListener(e -> playAudio());
        pauseButton.addActionListener(e -> pauseAudio());
        stopButton.addActionListener(e -> stopAudio());
        fastForwardButton.addActionListener(e -> controller.fastForward(5000000)); // Fast forward 5 seconds
        rewindButton.addActionListener(e -> controller.rewind(5000000)); // Rewind 5 seconds

        // Add main panel to the frame
        add(mainPanel);

        // Pack and set visible
        pack();
        setVisible(true);
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

    /**
     * Handles the play action: starts audio playback, the playback timer, and
     * updates the slider state.
     */
    private void playAudio() {
        controller.play();
        progressSlider.play();
    }

    /**
     * Handles the stop action: stops audio playback, the playback timer, and
     * updates the slider state.
     */
    private void stopAudio() {
        controller.stop();
        progressSlider.stop();
    }

    /**
     * Handles the pause action: pauses audio playback, stops the playback timer,
     * and updates the slider state.
     */
    private void pauseAudio() {
        controller.pause();
        progressSlider.pause();
    }

}
