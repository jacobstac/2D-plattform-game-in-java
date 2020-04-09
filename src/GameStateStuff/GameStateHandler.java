package GameStateStuff;

import Main.Game;
import Main.GameFrame;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 */
public class GameStateHandler {

    private int prevState = MENUSTATE;
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    public static final int PAUSSTATE = 2;
    public static final int OPTIONSSTATE = 3;
    public static final int GAMEOVERSTATE = 4;
    private ArrayList<GameState> gameStates;
    private int currentState;

    public GameStateHandler() {

        gameStates = new ArrayList<>();

        currentState = MENUSTATE;

        gameStates.add(new MenuState(this));
        gameStates.add(new Level1State(this));
        gameStates.add(new PausState(this));
        gameStates.add(new OptionsState(this));
        gameStates.add(new GameOverState(this));

    }

   /* private void loadState(int state){
        switch (state)

    }
    private void unLoadState(int state){

    }*/



    public void setState(int state) {
        prevState = currentState;
        currentState = state;

        if(currentState==1 || currentState == 2 || currentState == 3 || currentState == 4){

            Game.window.setSize(new Dimension(GameFrame.WIDTHLV1*GameFrame.scaleLV1, GameFrame.HEIGHTLV1*GameFrame.scaleLV1));

        }else{

            Game.window.setSize(new Dimension(GameFrame.WIDTH*GameFrame.scale, GameFrame.HEIGHT*GameFrame.scale));
        }

        gameStates.get(currentState).init();

    }

    public int getCurrentState() {

        return currentState;
    }


    public void update() {
        gameStates.get(currentState).update();

    }

    public void draw(java.awt.Graphics2D g) {
        gameStates.get(currentState).draw(g);

    }

    public void keyPresssed(int k) {
        gameStates.get(currentState).keyPressed(k);

    }

    public int getPrevState(){
        return prevState;
    }

    public void keyReleased(int k) {
        gameStates.get(currentState).keyReleased(k);
    }
}
