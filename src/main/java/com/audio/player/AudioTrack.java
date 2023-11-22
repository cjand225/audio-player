package com.audio.player;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Abstract class representing a generic audio track. This class provides a
 * framework for audio playback functionality, including opening and closing
 * audio files, controlling playback, and querying playback information.
 * Implementing classes should provide concrete implementations for these
 * methods tailored to specific audio formats or playback requirements.
 */
public abstract class AudioTrack {

    /**
     * Opens an audio file and prepares it for playback.
     *
     * @param file The audio file to be played.
     * @throws UnsupportedAudioFileException If the file format is not supported.
     * @throws IOException                   If an I/O error occurs.
     * @throws LineUnavailableException      If a line cannot be opened because it
     *                                       is unavailable.
     */
    public abstract void openFile(File file)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException;

    /**
     * Closes the currently open audio file and releases any system resources
     * associated with it.
     */
    public abstract void closeFile();

    /**
     * Checks if an audio file is loaded and ready for playback.
     *
     * @return true if an audio file is loaded, false otherwise.
     */
    public abstract boolean isLoaded();

    /**
     * Starts or resumes playback of the loaded audio file.
     */
    public abstract void play();

    /**
     * Pauses the playback of the audio file, if playing.
     */
    public abstract void pause();

    /**
     * Stops playback of the audio file and resets it to the beginning.
     */
    public abstract void stop();

    /**
     * Fast forwards the playback by a specified number of microseconds.
     *
     * @param microseconds The number of microseconds to fast forward.
     */
    public abstract void fastForward(long microseconds);

    /**
     * Rewinds the playback by a specified number of microseconds.
     *
     * @param microseconds The number of microseconds to rewind.
     */
    public abstract void rewind(long microseconds);

    /**
     * Retrieves the duration of the audio clip in microseconds.
     *
     * @return The duration of the clip in microseconds, or 0 if no clip is loaded.
     */
    public abstract long getDuration();

    /**
     * Retrieves the current playback position in microseconds.
     *
     * @return The current playback position in microseconds, or 0 if no clip is
     *         loaded.
     */
    public abstract long getPosition();

    /**
     * Sets the playback position to a specified location in microseconds.
     *
     * @param position The playback position in microseconds to set.
     */
    public abstract void setPosition(long position);

    /**
     * Checks if the audio is currently playing.
     *
     * @return true if the audio is playing, false otherwise.
     */
    public abstract boolean isPlaying();

}
