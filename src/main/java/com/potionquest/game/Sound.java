package com.potionquest.game;

import java.io.BufferedInputStream;
import javax.sound.sampled.*;
import java.io.IOException;

public class Sound {
    private String soundFile;
    private Clip clip;
    private float vols = 0f;
    public boolean muted = false;

    public void playSound() {
        setSoundFile("/Medieval_game.wav");
        try {
            //noinspection ConstantConditions
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(this.getClass().getResourceAsStream(getSoundFile())));
            setClip(AudioSystem.getClip());
            getClip().open(ais);
            getClip().start();
            getClip().loop(Clip.LOOP_CONTINUOUSLY);
            final FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-35.0f);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
    }
    public void turnUpVolume() {
        if(getClip() != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if(muted) {
                volumeControl.setValue(Math.min(vols+10.0f, volumeControl.getMaximum()));
                muted = false;
            } else {
                volumeControl.setValue(Math.min(volumeControl.getValue()+10.0f, volumeControl.getMaximum()));
            }
        }
    }

    public void turnDownVolume() {
        if(getClip() != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if(muted) {
                volumeControl.setValue(Math.max(vols-10.0f, volumeControl.getMinimum()));
                muted = false;
            } else {
                volumeControl.setValue(Math.max(volumeControl.getValue()-10.0f, volumeControl.getMinimum()));
            }
        }
    }

    public void muteToggle() {
        if(getClip() != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if(!muted) {
                vols = volumeControl.getValue();
                volumeControl.setValue(volumeControl.getMinimum());
                muted = true;
            }  else {
                volumeControl.setValue(vols);
                muted = false;
            }
        }
    }

    public int getVolume() {
        int volume = 0;
        if(getClip() != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume = (int) volumeControl.getMaximum();
        }
        return volume;
    }

    public String getVolumePercentage() {
        int percentage = 0;
        if(getClip() != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float volume = volumeControl.getValue();

            var range = (int) (volumeControl.getMaximum() - volumeControl.getMinimum());
            var currentValue = (int) (volume - volumeControl.getMinimum());

            percentage = currentValue * 100 / range;
        }
        return String.valueOf(percentage);
    }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    public String getSoundFile() {
        return this.soundFile;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public Clip getClip() {
        return this.clip;
    }
}
