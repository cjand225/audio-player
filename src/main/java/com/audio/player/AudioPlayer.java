package com.audio.player;

import javax.sound.sampled.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for loading and playing audio files.
 * It provides methods to control playback such as play, pause, stop, rewind,
 * and fast forward.
 * It uses the javax.sound.sampled library to load and control audio data.
 */
public class AudioPlayer {

    private static final Logger LOGGER = Logger.getLogger(AudioPlayer.class.getName());
    private AudioTrack track;

    /**
     * Constructs a new AudioPlayer instance.
     */
    public AudioPlayer() {
        LOGGER.log(Level.INFO, "AudioPlayer instance created");
    }

    /**
     * Opens an audio file and prepares it for playback.
     *
     * @param file The audio file to be played.
     * @throws UnsupportedAudioFileException If the file format is not supported.
     * @throws IOException                   If an I/O error occurs.
     * @throws LineUnavailableException      If a line cannot be opened because it
     *                                       is unavailable.
     */
    public void openFile(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        String extension = getFileExtension(file);

        switch (extension.toLowerCase()) {
            case "wav":
                track = new WavTrack();
                track.openFile(file);
                LOGGER.log(Level.INFO, "Audio file opened and ready for playback");
            default:
                throw new UnsupportedAudioFileException("Unsupported format: " + extension);
        }

    }

    /**
     * Retrieves the extension of a file.
     *
     * @param file The file from which to extract the extension.
     * @return The file extension, or an empty string if the file does not have one.
     */
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        int lastIndexOfDot = fileName.lastIndexOf('.');

        // Check if the last dot is not the first character and there is a dot
        if (lastIndexOfDot > 0) {
            return fileName.substring(lastIndexOfDot + 1);
        } else {
            // Return an empty string if there is no extension
            return "";
        }
    }

    /**
     * Starts playback of the audio file.
     */
    public void play() {
        track.play();
    }

    /**
     * Pauses playback of the audio file.
     */
    public void pause() {
        track.pause();
    }

    /**
     * Stops playback of the audio file and resets to the beginning.
     */
    public void stop() {
        track.stop();
    }

    /**
     * Fast forwards the playback by a specified number of microseconds.
     *
     * @param microseconds The number of microseconds to fast forward.
     */
    public void fastForward(long microseconds) {
        track.fastForward(microseconds);
    }

    /**
     * Rewinds the playback by a specified number of microseconds.
     *
     * @param microseconds The number of microseconds to rewind.
     */
    public void rewind(long microseconds) {
        track.rewind(microseconds);
    }

    /**
     * Retrieves the duration of the audio clip in microseconds.
     *
     * @return The duration of the clip in microseconds or 0 if the clip is not
     *         loaded.
     */
    public long getClipDuration() {
        return track.getDuration();
    }

    /**
     * Retrieves the current playback position in microseconds.
     *
     * @return The current position in microseconds or 0 if the clip is not loaded.
     */
    public long getClipCurrentTime() {
        return track.getPosition();
    }

    /**
     * Sets the playback position to a specified location in microseconds.
     *
     * @param position The position in microseconds where the playback should move
     *                 to.
     */
    public void setClipPosition(long position) {
        track.setPosition(position);
    }

    /**
     * Checks if the audio is currently playing.
     *
     * @return true if the audio is playing, false otherwise.
     */
    public boolean isPlaying() {
        return track.isPlaying();
    }

    /**
     * Checks if an audio clip is loaded and ready to be played.
     *
     * @return true if the clip is loaded, false otherwise.
     */
    public boolean isLoaded() {
        return track.isLoaded();
    }

    /**
     * Closes the clip and audio stream and releases any system resources associated
     * with them.
     */
    public void closeClip() {
        track.closeFile();
    }
}
