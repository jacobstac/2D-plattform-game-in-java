package GameStateStuff;

import Main.SoundClip;
import MapStuff.BackGround;
import MapStuff.GifBackground;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Antonivar on 2017-04-22.
 */
public class MenuState extends GameState {

    private int counter = 0;

    //Saker för bakgrundsmusik

    private SoundClip MenuSong = new SoundClip("/Audio/MenuSong1.mp3");

    //saker för options
    private int currentChoice = 0;

    private String[] options = {
            "Start",
            "Options",
            "Quit",

    };

    //Saker för bakgrundsbild


    private BackGround testBg = new BackGround("/Backgrounds/Meny1/meny0021.png");

    private String[] urlsBg = {
            "/BackGrounds/Meny1/meny0000.png",
            "/BackGrounds/Meny1/meny0001.png",
            "/BackGrounds/Meny1/meny0002.png",
            "/BackGrounds/Meny1/meny0003.png",
            "/BackGrounds/Meny1/meny0004.png",
            "/BackGrounds/Meny1/meny0005.png",
            "/BackGrounds/Meny1/meny0006.png",
            "/BackGrounds/Meny1/meny0007.png",
            "/BackGrounds/Meny1/meny0008.png",
            "/BackGrounds/Meny1/meny0009.png",
            "/BackGrounds/Meny1/meny0010.png",
            "/BackGrounds/Meny1/meny0011.png",
            "/BackGrounds/Meny1/meny0012.png",
            "/BackGrounds/Meny1/meny0013.png",
            "/BackGrounds/Meny1/meny0014.png",
            "/BackGrounds/Meny1/meny0015.png",
            "/BackGrounds/Meny1/meny0016.png",
            "/BackGrounds/Meny1/meny0017.png",
            "/BackGrounds/Meny1/meny0018.png",
            "/BackGrounds/Meny1/meny0019.png",
            "/BackGrounds/Meny1/meny0020.png",
            "/BackGrounds/Meny1/meny0021.png",
            "/BackGrounds/Meny1/meny0022.png",
            "/BackGrounds/Meny1/meny0023.png",
            "/BackGrounds/Meny1/meny0024.png",
            "/BackGrounds/Meny1/meny0025.png",
            "/BackGrounds/Meny1/meny0026.png",
            "/BackGrounds/Meny1/meny0027.png",
            "/BackGrounds/Meny1/meny0028.png",
            "/BackGrounds/Meny1/meny0029.png",
    };

    private GifBackground gifTest;

    private Font fontForOptions;

    public MenuState(GameStateHandler gsm){
        if(OptionsState.SoundOptions[OptionsState.currentSoundChoice].equals("On")){
            MenuSong.loop();
        }

        this.gsm = gsm;

        try{
            gifTest = new GifBackground(urlsBg,8);
            fontForOptions = new Font("Century Gothic", Font.PLAIN, 12);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        if(OptionsState.SoundOptions[OptionsState.currentSoundChoice].equals("On")){
            MenuSong.loop();
        }

    }

    @Override
    public void update() {





    }

    @Override
    public void draw(Graphics2D g) {

        gifTest.drawNext(g);


        g.setFont(fontForOptions);

        for (int i = 0; i < options.length; i++) {

            if (i == currentChoice) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);

            }
            g.drawString(options[i], 145, 140 + i * 15);
        }
    }

    public void select(){
        if(currentChoice == 0){
            MenuSong.stop();
            gsm.setState(1);
        }
        if(currentChoice==1){
            MenuSong.stop();
            gsm.setState(3);
        }
        if(currentChoice == options.length-1){
            System.exit(0);
        }

    }

    @Override
    public void keyPressed(int k) {

        if(k==KeyEvent.VK_SPACE){
            select();
        }

        if(k==KeyEvent.VK_ENTER){
            select();
        }

        if(k == KeyEvent.VK_DOWN){
            //MenuSong.play(klickSound);
            currentChoice ++;
            if(currentChoice == options.length){
                currentChoice = 0;
            }
        }

        if (k == KeyEvent.VK_UP) {
            //MenuSong.play(klickSound);
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }

    }

    @Override
    public void keyReleased(int k) {

    }

}
