package com.audio.player;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Controller for the AudioPlayer application.
 * It mediates the communication between the View and the Model.
 */
public class AudioPlayerController {

    private static final Logger LOGGER = Logger.getLogger(AudioPlayerController.class.getName());
    private AudioPlayer model;

    /**
     * Constructor for the controller.
     *
     * @param model The AudioPlayer model this controller will interact with.
     */
    public AudioPlayerController(AudioPlayer model) {
        this.model = model;
        LOGGER.info("AudioPlayerController initialized.");
    }

    /**
     * Loads an audio file into the player.
     *
     * @param file The File object representing the audio file to be played.
     */
    public void loadFile(File file) {
        try {
            model.openFile(file);
            LOGGER.log(Level.INFO, "Loaded audio file: " + file.getName());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading audio file: " + file.getName(), e);
        }
    }

    /**
     * Plays the loaded audio file.
     */
    public void play() {
        if (isClipLoaded()) {
            model.play();
            LOGGER.log(Level.INFO, "Playback started.");
        } else {
            LOGGER.log(Level.WARNING, "Playback attempted without a loaded audio clip.");
        }
    }

    /**
     * Pauses the playback.
     */
    public void pause() {
        if (isClipLoaded()) {
            model.pause();
            LOGGER.log(Level.INFO, "Playback paused.");
        } else {
            LOGGER.log(Level.WARNING, "Pause attempted without a loaded audio clip.");
        }
    }

    /**
     * Stops the playback and resets to the beginning.
     */
    public void stop() {
        if (isClipLoaded()) {
            model.stop();
            LOGGER.log(Level.INFO, "Playback stopped and reset.");
        } else {
            LOGGER.log(Level.WARNING, "Stop attempted without a loaded audio clip.");
        }
    }

    /**
     * Fast forwards the playback by a certain amount.
     *
     * @param amount The amount to fast forward.
     */
    public void fastForward(long amount) {
        if (isClipLoaded()) {
            model.fastForward(amount);
            LOGGER.log(Level.INFO, "Fast forwarded playback by " + amount + " microseconds.");
        } else {
            LOGGER.log(Level.WARNING, "Fast forward attempted without a loaded audio clip.");
        }
    }

    /**
     * Rewinds the playback by a certain amount.
     *
     * @param amount The amount to rewind.
     */
    public void rewind(long amount) {
        if (isClipLoaded()) {
            model.rewind(amount);
            LOGGER.log(Level.INFO, "Rewound playback by " + amount + " microseconds.");
        } else {
            LOGGER.log(Level.WARNING, "Rewind attempted without a loaded audio clip.");
        }
    }

    /**
     * Sets the current position of playback.
     *
     * @param position The position to set the playback to.
     */
    public void setPlaybackPosition(long position) {
        if (isClipLoaded()) {
            model.setClipPosition(position);
            LOGGER.log(Level.INFO, "Set playback position to " + position + " microseconds.");
        } else {
            LOGGER.log(Level.WARNING, "Setting playback position attempted without a loaded audio clip.");
        }
    }

    /**
     * Retrieves the current position of playback.
     *
     * @return The current playback position.
     */
    public long getPlaybackPosition() {
        if (isClipLoaded()) {
            long position = model.getClipCurrentTime();
            LOGGER.log(Level.INFO, "Retrieved current playback position: " + position);
            return position;
        } else {
            LOGGER.log(Level.WARNING, "Retrieving playback position attempted without a loaded audio clip.");
            return 0;
        }
    }

    /**
     * Retrieves the duration of the loaded audio clip.
     *
     * @return The duration of the clip.
     */
    public long getClipDuration() {
        if (isClipLoaded()) {
            long duration = model.getClipDuration();
            LOGGER.log(Level.INFO, "Retrieved audio clip duration: " + duration);
            return duration;
        } else {
            LOGGER.log(Level.WARNING, "Retrieving clip duration attempted without a loaded audio clip.");
            return 0;
        }
    }

    /**
     * Checks if the audio is currently playing.
     *
     * @return true if the audio is playing, false otherwise.
     */
    public boolean isPlaying() {
        boolean playing = model.isPlaying();
        LOGGER.log(Level.INFO, "Playback is " + (playing ? "currently" : "not") + " playing.");
        return playing;
    }

    /**
     * Checks if an audio clip is loaded.
     *
     * @return true if a clip is loaded, false otherwise.
     */
    public boolean isClipLoaded() {
        return model.isLoaded();
    }

    /**
     * Closes the audio clip and releases resources.
     */
    public void closeClip() {
        model.closeClip();
    }
}
