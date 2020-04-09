package GameStateStuff;

import Main.SoundClip;
import MapStuff.BackGround;
import MapStuff.GifBackground;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Antonivar on 2017-05-04.
 */
public class GameOverState extends GameState {

    private BackGround bg;
    private GifBackground gifBackground;
    private int GifFPS = 2;

    private SoundClip GameOverSong;

    private String[] urls = {
            "/Backgrounds/GAMEOVER/gameover0000.png",
            "/Backgrounds/GAMEOVER/gameover0001.png",
            "/Backgrounds/GAMEOVER/gameover0002.png",
            "/Backgrounds/GAMEOVER/gameover0003.png",
            "/Backgrounds/GAMEOVER/gameover0004.png",
            "/Backgrounds/GAMEOVER/gameover0005.png",
            "/Backgrounds/GAMEOVER/gameover0006.png",
            "/Backgrounds/GAMEOVER/gameover0007.png",
            "/Backgrounds/GAMEOVER/gameover0008.png",
            "/Backgrounds/GAMEOVER/gameover0009.png",
            "/Backgrounds/GAMEOVER/gameover0010.png",


    };


    public GameOverState(GameStateHandler gsm){

        this.gsm = gsm;
        bg = new BackGround("/Backgrounds/GAMEOVER/gameover0010.png");
        gifBackground = new GifBackground(urls,GifFPS);
        GameOverSong = new SoundClip("/Audio/GAMEOVERSONGALT2.mp3");


    }



    @Override
    public void init() {
        if(OptionsState.SoundOptions[OptionsState.currentSoundChoice].equals("On")){
            GameOverSong.loop();
        }

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        if(gifBackground.getPlayedOnce()){
            bg.draw(g);
        }else{
            gifBackground.drawNextAndStop(g);
        }


    }

    @Override
    public void keyPressed(int k) {

        if(k == KeyEvent.VK_ESCAPE){
            GameOverSong.stop();
            gifBackground.setPlayedOnce(false);
            gsm.setState(0);

        }

    }

    @Override
    public void keyReleased(int k) {

    }

}
