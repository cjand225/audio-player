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
    private Clip clip;
    private AudioInputStream audioStream;

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
        if (clip != null) {
            closeClip();
        }
        LOGGER.log(Level.INFO, "Opening audio file: " + file.getName());
        audioStream = AudioSystem.getAudioInputStream(file);
        DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioStream);
        LOGGER.log(Level.INFO, "Audio file opened and ready for playback");
    }

    /**
     * Starts playback of the audio file.
     */
    public void play() {
        if (clip != null) {
            clip.start();
            LOGGER.log(Level.INFO, "Playback started");
        }
    }

    /**
     * Pauses playback of the audio file.
     */
    public void pause() {
        if (clip != null) {
            clip.stop();
            LOGGER.log(Level.INFO, "Playback paused");
        }
    }

    /**
     * Stops playback of the audio file and resets to the beginning.
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            LOGGER.log(Level.INFO, "Playback stopped and reset");
        }
    }

    /**
     * Fast forwards the playback by a specified number of microseconds.
     *
     * @param microseconds The number of microseconds to fast forward.
     */
    public void fastForward(long microseconds) {
        if (clip != null) {
            long newPosition = Math.min(clip.getMicrosecondPosition() + microseconds, clip.getMicrosecondLength());
            clip.setMicrosecondPosition(newPosition);
            LOGGER.log(Level.INFO, "Fast forward by " + microseconds + " microseconds");
        }
    }

    /**
     * Rewinds the playback by a specified number of microseconds.
     *
     * @param microseconds The number of microseconds to rewind.
     */
    public void rewind(long microseconds) {
        if (clip != null) {
            long newPosition = Math.max(clip.getMicrosecondPosition() - microseconds, 0);
            clip.setMicrosecondPosition(newPosition);
            LOGGER.log(Level.INFO, "Rewind by " + microseconds + " microseconds");
        }
    }

    /**
     * Moves the playback position to the beginning of the audio stream.
     */
    public void begin() {
        if (clip != null) {
            clip.setMicrosecondPosition(0);
            LOGGER.log(Level.INFO, "Playback position moved to the beginning");
        }
    }

    /**
     * Moves the playback position to the end of the audio stream.
     */
    public void end() {
        if (clip != null) {
            clip.setMicrosecondPosition(clip.getMicrosecondLength());
            LOGGER.log(Level.INFO, "Playback position moved to the end");
        }
    }

    /**
     * Retrieves the duration of the audio clip in microseconds.
     *
     * @return The duration of the clip in microseconds or 0 if the clip is not
     *         loaded.
     */
    public long getClipDuration() {
        if (clip != null) {
            return clip.getMicrosecondLength();
        } else {
            return 0;
        }
    }

    /**
     * Retrieves the current playback position in microseconds.
     *
     * @return The current position in microseconds or 0 if the clip is not loaded.
     */
    public long getClipCurrentTime() {
        if (clip != null) {
            return clip.getMicrosecondPosition();
        } else {
            return 0;
        }
    }

    /**
     * Sets the playback position to a specified location in microseconds.
     *
     * @param position The position in microseconds where the playback should move
     *                 to.
     */
    public void setClipPosition(long position) {
        if (clip != null) {
            clip.setMicrosecondPosition(position);
            LOGGER.log(Level.INFO, "Playback position set to " + position + " microseconds");
        }
    }

    /**
     * Checks if the audio is currently playing.
     *
     * @return true if the audio is playing, false otherwise.
     */
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

    /**
     * Checks if an audio clip is loaded and ready to be played.
     *
     * @return true if the clip is loaded, false otherwise.
     */
    public boolean isLoaded() {
        return clip != null;
    }

    /**
     * Closes the clip and audio stream and releases any system resources associated
     * with them.
     */
    public void closeClip() {
        if (clip != null) {
            clip.stop();
            clip.close();
            LOGGER.log(Level.INFO, "Clip closed");
            clip = null;
        }
        if (audioStream != null) {
            try {
                audioStream.close();
                LOGGER.log(Level.INFO, "Audio stream closed");
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Failed to close audio stream", e);
            }
            audioStream = null;
        }
    }
}
