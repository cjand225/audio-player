package com.audio.player;

import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * This class represents a slider component in the audio player UI that
 * displays and controls the current position of the audio playback.
 */
public class ProgressSlider extends JPanel {

    private static final Logger LOGGER = Logger.getLogger(ProgressSlider.class.getName());
    private AudioPlayerController controller;
    private JSlider progressSlider;
    private Timer updateTimer;
    private JLabel durationLabel;
    private JLabel remainderLabel;
    private boolean isUserInteraction;

    /**
     * Constructs a ProgressSlider with a reference to the audio player controller.
     *
     * @param controller The controller handling the audio playback logic.
     */
    public ProgressSlider(AudioPlayerController controller) {
        this.controller = controller;
        init();
    }

    /**
     * Initializes the slider component, configuring its properties and layout.
     */
    private void init() {
        LOGGER.log(Level.INFO, "Initializing ProgressSlider");

        this.setLayout(new BorderLayout());

        progressSlider = new JSlider();
        progressSlider.setMinimum(0);
        progressSlider.setMaximum(100);
        progressSlider.setValue(0);
        progressSlider.setEnabled(false);

        JPanel leftLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        durationLabel = new JLabel();
        leftLabelPanel.add(durationLabel);

        JPanel rightLabelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        remainderLabel = new JLabel();
        rightLabelPanel.add(remainderLabel);

        updateLabels(0, 0);

        // Add a change listener to handle user interactions with the slider
        progressSlider.addChangeListener(e -> updateClipPosition());

        // Add the slider to the panel
        this.add(leftLabelPanel, BorderLayout.LINE_START);
        this.add(rightLabelPanel, BorderLayout.LINE_END);
        this.add(progressSlider, BorderLayout.PAGE_START);
    }

    /**
     * Starts a timer to periodically update the position of the song progress
     * slider based on the current playback position.
     */
    private void startPlaybackTimer() {
        LOGGER.log(Level.INFO, "Starting playback timer");
        updateTimer = new Timer(1000, e -> updateSliderPosition());
        updateTimer.start();
    }

    /**
     * Stops the playback timer.
     */
    private void stopPlaybackTimer() {
        LOGGER.log(Level.INFO, "Stopping playback timer");
        if (updateTimer != null) {
            updateTimer.stop();
        }
    }

    /**
     * Updates the position of the song progress slider based on the current
     * playback position of the audio clip.
     */
    private void updateSliderPosition() {

        if (!controller.isClipLoaded())
            return;

        if (progressSlider.getValueIsAdjusting())
            return;

        isUserInteraction = false;

        int songDuration = (int) (controller.getClipDuration() / 1_000_000);
        int currentPositionSeconds = (int) (controller.getPlaybackPosition() / 1_000_000);
        LOGGER.log(Level.INFO, "Updating slider position: " + currentPositionSeconds);
        progressSlider.setValue(currentPositionSeconds);

        updateLabels(currentPositionSeconds, songDuration);

        isUserInteraction = true;
    }

    /**
     * Updates the playback position of the audio clip based on the current
     * position of the progress slider.
     */
    private void updateClipPosition() {

        if (!controller.isClipLoaded())
            return;

        if (progressSlider.getValueIsAdjusting() || !isUserInteraction)
            return;

        long newPositionMicroseconds = (long) progressSlider.getValue() * 1_000_000L;
        LOGGER.log(Level.INFO, "User updated clip position: " + newPositionMicroseconds);
        controller.setPlaybackPosition(newPositionMicroseconds);
    }

    /**
     * Updates the state of the progress slider, enabling or disabling it based
     * on whether an audio clip is currently loaded. Also sets the maximum value
     * of the slider based on the duration of the loaded audio clip.
     */
    private void updateSliderState() {
        boolean isLoaded = controller.isClipLoaded();

        if (!isLoaded) {
            LOGGER.log(Level.INFO, "Updating slider state - No clip loaded");
            progressSlider.setValue(0);
            progressSlider.setEnabled(false);
            updateLabels(0, 0);
            return;
        }

        if (!progressSlider.getValueIsAdjusting()) {
            progressSlider.setEnabled(isLoaded);

            int songDuration = (int) (controller.getClipDuration() / 1_000_000);
            int songPosition = (int) (controller.getPlaybackPosition() / 1_000_000);

            LOGGER.log(Level.INFO, "Updating slider state - Clip loaded with duration: " + songDuration);

            progressSlider.setMaximum(songDuration);
            updateLabels(songPosition, songDuration);
        }

    }

    /**
     * Initiates playback, starts the playback timer, and updates the slider state.
     */
    public void play() {
        LOGGER.log(Level.INFO, "Play command received");
        startPlaybackTimer();
        updateSliderState();
    }

    /**
     * Pauses the playback, stops the playback timer, and updates the slider state.
     */
    public void pause() {
        LOGGER.log(Level.INFO, "Pause command received");
        stopPlaybackTimer();
        updateSliderState();
    }

    /**
     * Stops the playback, stops the playback timer, resets the slider to its
     * initial state, and updates the slider state.
     */
    public void stop() {
        LOGGER.log(Level.INFO, "Stop command received");
        stopPlaybackTimer();
        updateSliderState();
        updateSliderPosition();
    }

    /**
     * Formats the given time in seconds into a string representation.
     * 
     * This method converts a time duration in seconds to a formatted string in the
     * format of "M:SS" or "0:SS".
     * If the total time is less than 10 minutes, it omits the leading zero for
     * minutes.
     *
     * @param totalSeconds The total time in seconds to be formatted.
     * @return A string representing the formatted time.
     *         If the minutes are greater than 0, it returns a format of "M:SS".
     *         If the minutes are 0, it returns a format of "0:SS", ensuring that
     *         the minutes are always displayed.
     */
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        // Format the time, omitting leading zeros for minutes if it's less than 10
        if (minutes > 0) {
            return String.format("%d:%02d", minutes, seconds);
        } else {
            return String.format("0:%02d", seconds);
        }
    }

    /**
     * Updates the duration and remainder labels based on the current position and
     * total duration of the song.
     *
     * @param currentPositionSeconds The current position in the song in seconds.
     * @param totalDurationSeconds   The total duration of the song in seconds.
     */
    private void updateLabels(int currentPositionSeconds, int totalDurationSeconds) {
        durationLabel.setText(formatTime(currentPositionSeconds));
        int remainingTimeSeconds = totalDurationSeconds - currentPositionSeconds;
        remainderLabel.setText('-' + formatTime(remainingTimeSeconds));
    }

}
