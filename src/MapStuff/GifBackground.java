package MapStuff;

import java.awt.*;

/**
 * Created by Antonivar on 2017-04-26.
 */
public class GifBackground {

    private BackGround[] backGrounds;
    private int numFrames;
    private int currentFrame;
    private int counter;
    private int FPS;
    private boolean playedOnce;

    public GifBackground(String[] urls, int FPS) {
        playedOnce = false;
        this.FPS = FPS;
        currentFrame = 0;
        numFrames = urls.length;
        backGrounds = new BackGround[numFrames];
        for (int i = 0; i < numFrames; i++) {
            backGrounds[i] = new BackGround(urls[i]);
        }
    }

    public void drawNextAndStop(Graphics2D g) {

        if (!playedOnce) {
            counter++;
            backGrounds[currentFrame].draw(g);

            if (counter == (60 / FPS) -1) {
                currentFrame++;
                counter = 0;
            }
        }
        if (currentFrame == numFrames) {
            playedOnce = true;
        }


    }
    public boolean getPlayedOnce(){
        return playedOnce;
    }

    public void drawNext(Graphics2D g) {
        counter++;
        backGrounds[currentFrame].draw(g);

        if (counter == (60 / FPS) - 1) {
            currentFrame++;
            counter = 0;
        }
        if (currentFrame == numFrames - 1) {
            currentFrame = 0;
        }

    }

    public void setPlayedOnce(boolean playedOnce) {
        this.playedOnce = playedOnce;
        currentFrame = 0;
        counter = 0;
    }

}
