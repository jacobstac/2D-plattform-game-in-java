package PhysicalObjects;

import java.awt.image.BufferedImage;

/**
 * Created by Jacob on 2017-05-02.
 *  Allt den här klassen gör är att hantera sprite-animations
 */
public class Animation {


    private BufferedImage[]  frames;
    private int currentFrame;

    //timer between frames
    private long startTime;
    private long delay;


    // Visar om animationen skett. Detta är nyttigt till exempel vid ATTACK
    // Då den bara behöver ske en gång
    private boolean playedOnce;


    // Constructor
    public Animation() {

        playedOnce = false;


    }


    public void setFrames(BufferedImage[] frames) {

        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long d) {
        delay = d;
    }

    public void setFrame(int i) {
        currentFrame = i;
    }

    //Kommer att hantera logiken om eller inte att gå till nästa frame
    public void update() {

        if(delay == -1) return; //then there is no animation, just return

        long elapsed = (System.nanoTime() - startTime) / 1000000;

        //om tiden är större än delay så måste vi gå till nästa ruta
        if(elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        //Vi måste även se till att vi inte går förbi mängden frames vi har
        if(currentFrame == frames.length) {
            //Då måste vi loopa, tillbaka till frame med index 0
            currentFrame = 0;
            playedOnce = true;
        }


    }

    public int getFrame() {
        return currentFrame;
    }

    // Denna är viktig, den kommer hämta den bilden vi kommer behöva rita
    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    // Kommer returnera om animationen spelats upp innan eller inte
    public boolean hasPlayedOnce() {
        return playedOnce;
    }




}


