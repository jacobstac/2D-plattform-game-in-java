package GameStateStuff;


/**
 * Created by Antonivar on 2017-04-20.
 */
public abstract class GameState {

    protected GameStateHandler gsm;

    public abstract void init();

    public abstract void update();

    public abstract void draw(java.awt.Graphics2D g);

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

    //public abstract void playBackgroundMusic();

}