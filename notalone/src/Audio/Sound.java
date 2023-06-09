package Audio;

import Exceptions.NotCriticalExceptionHandler;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.FileNotFoundException;
import java.util.Objects;

/*!
    \brief Sound.

    Implements the sound class and provides methods for: setting, playing, looping, stopping, and
    closing a sound.
 */
public class Sound {
    private Clip clip;

    //! \brief No-Args constructor
    public Sound() {}

    /*!
        \brief Parameterized constructor

        Calls the \ref setClip(String) function with the received parameter as argument.
     */
    public Sound(String fileName) {
        setClip(fileName);
    }

    //! \brief Sets and opens a sound with the specified name received as argument.

    public void setClip(String fileName) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/sounds/" + fileName)));
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);

            clip = AudioSystem.getClip();
            clip.open(dais);
        } catch (Exception e) {
            String s = "Could not load the sound: " + fileName + "\n";
            NotCriticalExceptionHandler.handle(new FileNotFoundException(s));
        }
    }

    /*!
        \brief Play sound.

        If the sound exists then it is stopped and started again, otherwise it returns.
     */
    public void play() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    //! \brief Loops the sound.
    public void loopPlaying() {
        if (clip == null) return;
        if (clip.getFrameLength() == clip.getFramePosition()) {
            clip.setFramePosition(0);
        }
        if (clip.getFramePosition() == 0)
            clip.start();
    }

    //! \brief Loops the sound.
    public void stop() {
        if (clip == null) return;
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
    }

    //! \brief Stops the sound.
    public void close() {
        try {
            stop();
            clip.close();
        } catch (Exception e) {
            NotCriticalExceptionHandler.handle(e);
        }
    }
}