import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// Student ID: 22305226
// Student Name: Maheen Ahmed

// Reference used: https://stackoverflow.com/a/64509714

public class Sound {
    
    File benBulletSoundFile = new File("res/ben.wav");
    File gwenBulletSoundFile = new File("res/laser9.wav");
    File themeMusicFile = new File("res/theme.wav");

    public void benBulletSound() {
        playSound(benBulletSoundFile);
    }

    public void gwenBulletSound() {
        playSound(gwenBulletSoundFile);
    }

    public void themeMusic() {
        playSound(themeMusicFile);
    }

    public void playSound(File soundFile) {
        
        AudioInputStream ais = null;
        Clip clip = null;

        try {
            ais = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }  catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        clip.setFramePosition(0);
        clip.start();
    }
}
