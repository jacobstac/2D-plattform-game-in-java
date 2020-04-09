package Main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



/**
 *
 * Klass som är till för att spela ljud
 */

public class SoundClip{
    private Clip clip;

    public SoundClip(String url){

        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(url));
            AudioFormat baseFormat = input.getFormat();
            AudioFormat decoded = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

            AudioInputStream dInput = AudioSystem.getAudioInputStream(decoded, input);
            clip = AudioSystem.getClip();
            clip.open(dInput);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void loop(){
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        if(clip.isRunning()){
            clip.stop();
        }
    }


    public void play() {
        if(clip == null)return;
        clip.setFramePosition(0);
        clip.start();

    }

}