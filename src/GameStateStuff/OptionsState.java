package GameStateStuff;

import Main.GameFrame;
import MapStuff.BackGround;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Antonivar on 2017-05-04.
 */
public class OptionsState extends GameState {

    private BackGround bg;

    private int x, y;

    private Font fontForOptions;

    public OptionsState(GameStateHandler gsm){

        this.gsm = gsm;
        bg = new BackGround("/Backgrounds/BackgroundNight.png");
        fontForOptions = new Font("Century Gothic", Font.PLAIN, 12);

    }

    private String[] options = {
            "Toogle Sound",
            "Toogle Difficulty",
            "Scale Frame",
            "Back",
    };

    public static int currentSoundChoice = 0;



    public static final String[] SoundOptions = {
            "On",
            "Off",
    };

    private int currentDiffChoice = 0;

    private int currentFrameChoiche = 0;

    private String[] DiffOptions = {
            "Easy",
            "Challenging",
            "Legendary",
            "Actually impossible",
    };

    private String[] FrameOptions ={
            "640 x 360",
            "1280 x 720",
            "1920 x 1080",
    };



    private int currentChoice = 0;


    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {

        bg.draw(g);

        g.setFont(fontForOptions);

        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {

                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }


        g.setColor(Color.BLACK);
        g.drawString(SoundOptions[currentSoundChoice], 245 , 140);
        g.drawString(DiffOptions[currentDiffChoice],245, 155);
        g.drawString(FrameOptions[currentFrameChoiche],245,170);




    }

    @Override
    public void keyPressed(int k) {

        if(k == KeyEvent.VK_SPACE){
            select();
        }

        if(k == KeyEvent.VK_ENTER){
            select();
        }


        if(k == KeyEvent.VK_DOWN){
            currentChoice ++;
            if(currentChoice == options.length){
                currentChoice = 0;
            }
        }

        if (k == KeyEvent.VK_UP) {

            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }


        if(k == KeyEvent.VK_ESCAPE){
            gsm.setState(0);
        }

    }

    @Override
    public void keyReleased(int k) {

    }

    public void select(){
        if(currentChoice == 0){
            currentSoundChoice ++;
            if(currentSoundChoice == SoundOptions.length){
                currentSoundChoice = 0;
            }
        }
        if(currentChoice == 1){
            currentDiffChoice ++;
            if(currentDiffChoice == DiffOptions.length){
                currentDiffChoice = 0;
            }

        }
        if (currentChoice == 2){
            currentFrameChoiche ++;
            if(currentFrameChoiche == 0){
                GameFrame.scaleLV1 = 1;
            }
            if(currentFrameChoiche == 1){
                GameFrame.scaleLV1 = 2;
            }
            if(currentFrameChoiche == 2){
                GameFrame.scaleLV1 = 3;
            }
            if(currentFrameChoiche == FrameOptions.length){
                currentFrameChoiche = 0;
            }
        }
        if(currentChoice == options.length-1){
            gsm.setState(gsm.getPrevState());
        }

    }
}
