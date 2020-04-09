package Main;

import GameStateStuff.GameStateHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Panelen för spelet, här kommer spelets Main-Loop at vara.
 */
public class GameFrame extends JPanel implements KeyListener, Runnable {

    //Dimensioner för fönster #1
    public static  int WIDTH = 320;
    public static  int HEIGHT = WIDTH / 12 * 9;
    public static  int scale = 3;
    //Dimensioner för fönster #2
    public static int WIDTHLV1 = 640;
    public static int HEIGHTLV1 = WIDTHLV1 / 16 * 9;
    public static int scaleLV1 = 2;


    private static final long serialVersionUID = -7488199732910386374L;

    //Main spel-tråd
    private Thread thread;
    private boolean running;
    private int fps = 60;
    private long targetTime = 1000 / fps;

    //Bilden
    private BufferedImage image1, image;
    private Graphics2D g, g1;
    //private BufferStrategy strategy; // eventuellt för trippelBuffering om vi får problem med uppdatering av bilden.

    //Game-state-hanteraren
    private GameStateHandler gsm;

    public GameFrame() {
        super();
        setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));
        setFocusable(true);
        requestFocus();
    }
    /**
     * Osäker på exakt hur addNotify funkar, men den här initerar tråden som spelet ska köras på.
     */
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }
    /**
     * Initierar alla nödvändiga värden
     */
    private void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        image1 = new BufferedImage(WIDTHLV1,HEIGHTLV1, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g1 = (Graphics2D) image1.getGraphics();
        running = true;
        gsm = new GameStateHandler();
    }
    /**
     * Kallar update på gameStateHandlern som i sin tur kallar på update i current state
     */
    private void update() {
        gsm.update();
    }

    /**
     * Samma som ovan fast med draw
     */
    private void draw() {
       if(gsm.getCurrentState()==0){
           gsm.draw(g);
       }else{
           gsm.draw(g1);
       }
    }


    /**
     * Hämtar samtlig grafik och ritar till skärmen
     */


    private void drawToScreen() {
        Graphics g2 = getGraphics();

        if(gsm.getCurrentState()==0){
            g2.drawImage(image, 0, 0, WIDTH*scale, HEIGHT*scale, null);

        }else {
            g2.drawImage(image1, 0, 0, WIDTHLV1 * scaleLV1, HEIGHTLV1 * scaleLV1, null);
        }
        g2.dispose();
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {gsm.keyPresssed(e.getKeyCode());}

    @Override
    public void keyReleased(KeyEvent e) {gsm.keyReleased(e.getKeyCode());}

    /**
     * Game-loop, begränsad till 60 iterationer per sekund, körs tills programmet avslutas.
     */
    @Override
    public void run() {
        this.repaint();
        init();

        long start;
        long elapsed;
        long wait;

        while (running) {
            start = System.nanoTime();
            update();
            draw();
            drawToScreen();
            elapsed = System.nanoTime() - start;
            wait = Math.abs(targetTime - elapsed / 1000000);

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
