package GameStateStuff;

import MapStuff.BackGround;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Antonivar on 2017-05-04.
 */
public class PausState extends GameState {

    private BackGround bg;

    private int x, y;

    private Font fontForOptions;

    public PausState(GameStateHandler gsm){

        this.gsm = gsm;
        bg = new BackGround("/Backgrounds/BackgroundNight.png");
        fontForOptions = new Font("Century Gothic", Font.PLAIN, 12);

    }

    private String[] options = {
            "Get back to the action",
            "Options",
            "Quit to Main Menu",
            "Quit to OS",
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


        if(k == KeyEvent.VK_ESCAPE){
            gsm.setState(0);
        }

    }

    @Override
    public void keyReleased(int k) {

    }

    public void select(){
        if(currentChoice == 0){
            gsm.setState(1);
            gsm.getCurrentState();
        }
        if(currentChoice == 1){
            gsm.setState(3);
        }
        if(currentChoice ==2){
            gsm.setState(0);
        }
        if(currentChoice == options.length-1){
            System.exit(0);
        }
    }
}
