package com.audio.player;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WavTrack extends AudioTrack {
    private static final Logger LOGGER = Logger.getLogger(AudioPlayer.class.getName());
    private Clip clip;
    private AudioInputStream audioStream;

    /**
     * Opens an audio file and prepares it for playback.
     *
     * @param file The audio file to be played.
     * @throws UnsupportedAudioFileException If the file format is not supported.
     * @throws IOException                   If an I/O error occurs.
     * @throws LineUnavailableException      If a line cannot be opened because it
     *                                       is unavailable.
     */
    @Override
    public void openFile(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (clip != null) {
            closeFile();
        }
        LOGGER.log(Level.INFO, "Opening audio file: " + file.getName());
        audioStream = AudioSystem.getAudioInputStream(file);
        DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioStream);
        LOGGER.log(Level.INFO, "Audio file opened and ready for playback");
    }

    /**
     * Closes the clip and audio stream and releases any system resources associated
     * with them.
     */
    @Override
    public void closeFile() {
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

    /**
     * Checks if an audio clip is loaded and ready to be played.
     *
     * @return true if the clip is loaded, false otherwise.
     */
    @Override
    public boolean isLoaded() {
        return clip != null;
    }

    /**
     * Starts playback of the audio file.
     */
    @Override
    public void play() {
        if (clip != null) {
            clip.start();
            LOGGER.log(Level.INFO, "Playback started");
        }
    }

    /**
     * Pauses playback of the audio file.
     */
    @Override
    public void pause() {
        if (clip != null) {
            clip.stop();
            LOGGER.log(Level.INFO, "Playback paused");
        }
    }

    /**
     * Stops playback of the audio file and resets to the beginning.
     */
    @Override
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
    @Override
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
    @Override
    public void rewind(long microseconds) {
        if (clip != null) {
            long newPosition = Math.max(clip.getMicrosecondPosition() - microseconds, 0);
            clip.setMicrosecondPosition(newPosition);
            LOGGER.log(Level.INFO, "Rewind by " + microseconds + " microseconds");
        }
    }

    /**
     * Retrieves the duration of the audio clip in microseconds.
     *
     * @return The duration of the clip in microseconds or 0 if the clip is not
     *         loaded.
     */
    @Override
    public long getDuration() {
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
    @Override
    public long getPosition() {
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
    @Override
    public void setPosition(long position) {
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
    @Override
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

}
