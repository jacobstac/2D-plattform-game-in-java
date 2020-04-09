package GameStateStuff;

import Main.GameFrame;
import Main.SoundClip;
import MapStuff.BackGround;
import MapStuff.GifBackground;
import MapStuff.TileMap;
import PhysicalObjects.Enemy;
import PhysicalObjects.Explosion;
import PhysicalObjects.HUD;
import PhysicalObjects.PhysicalObjects.Enemies.Sten;
import PhysicalObjects.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * UNDER CONSTRUCTION! KOMMER KOMMER BYGGAS SENT I PROJEKTET
 */
public class Level1State extends GameState{

    private int isKen = 1;

    // spelaren
    private Player player;

    //Fiende
    private Sten s, s1, s2, s3, s4;

    //Skapar en Arraylist med alla fiender.
    private ArrayList<Enemy> enemies;

    private ArrayList<Explosion> explosions;

    // create HUD
    private HUD hud;


    // Background
    private BackGround bg;


    private boolean SoundStarted = false;

    //Stuff for intro animation
    private GifBackground gifBG;

    //Detta är giffen som säger vilken nivå vi är på
    private String[] urls = {
            "/Backgrounds/LevelGI/LevelGIF0000.png",
            "/Backgrounds/LevelGI/LevelGIF0001.png",
            "/Backgrounds/LevelGI/LevelGIF0002.png",
            "/Backgrounds/LevelGI/LevelGIF0003.png",
            "/Backgrounds/LevelGI/LevelGIF0004.png",
            "/Backgrounds/LevelGI/LevelGIF0005.png",
            "/Backgrounds/LevelGI/LevelGIF0006.png",
            "/Backgrounds/LevelGI/LevelGIF0007.png",
            "/Backgrounds/LevelGI/LevelGIF0008.png",
            "/Backgrounds/LevelGI/LevelGIF0009.png",
            "/Backgrounds/LevelGI/LevelGIF0010.png",
            "/Backgrounds/LevelGI/LevelGIF0010A.png",
            "/Backgrounds/LevelGI/LevelGIF0010B.png",
            "/Backgrounds/LevelGI/LevelGIF0010C.png",
            "/Backgrounds/LevelGI/LevelGIF0011.png",
            "/Backgrounds/LevelGI/LevelGIF0012.png",
            "/Backgrounds/LevelGI/LevelGIF0013.png",
            "/Backgrounds/LevelGI/LevelGIF0014.png",
            "/Backgrounds/LevelGI/LevelGIF0015.png",
            "/Backgrounds/LevelGI/LevelGIF0016.png",
            "/Backgrounds/LevelGI/LevelGIF0017.png",
            "/Backgrounds/LevelGI/LevelGIF0018.png",
            "/Backgrounds/LevelGI/LevelGIF0019.png",
            "/Backgrounds/LevelGI/LevelGIF0020.png",
            "/Backgrounds/LevelGI/LevelGIF0021.png",
            "/Backgrounds/LevelGI/LevelGIF0022.png",
            "/Backgrounds/LevelGI/LevelGIF0023.png",
            "/Backgrounds/LevelGI/LevelGIF0024.png",
            "/Backgrounds/LevelGI/LevelGIF0025.png",
            "/Backgrounds/LevelGI/LevelGIF0026.png",
    };

    private int GifBgFps = 13;

    private int counter = 0;

    private TileMap tileMap;

    private SoundClip LaserSound = new SoundClip("/Audio/Laser.mp3");

    private SoundClip BGSong = new SoundClip("/Audio/Song1.mp3");

    private SoundClip EmptyClip = new SoundClip("/Audio/Effects/EmptyClip.mp3");

    private int currentChoice = 1;


    public Level1State(GameStateHandler gsm){
        this.gsm = gsm;

        try{

            tileMap = new TileMap(30);
            tileMap.loadTiles("/TileSheet/test.png");
            tileMap.loadMap("/Maps/test.map");
            tileMap.setPosisition(0, 0);
            player = new Player(tileMap);
            // här skapas fienderna
            enemies = new ArrayList<Enemy>();
            s = new Sten(tileMap);
            s1 = new Sten(tileMap);
            s2 = new Sten(tileMap);
            s3 = new Sten(tileMap);
            s4 = new Sten(tileMap);
            enemies.add(s);
            enemies.add(s1);
            enemies.add(s2);
            enemies.add(s3);
            enemies.add(s4);

            gifBG = new GifBackground(urls,GifBgFps);
            bg = new BackGround("/Backgrounds/BackgroundNight.png");


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

        // intierar skapning av spelare

        //Positionera spelaren rätt
        if(gsm.getPrevState() == 0) {
            player.setPosition(200, 200);
            s.setPosition(120, 120);
            s1.setPosition(500, 200);
            s2.setPosition(300, 150);
            s3.setPosition(150, 150);
            s4.setPosition(350, 150);
        }
        else {
            System.out.println(player.getx() + " "  + player.gety());
            player.setPosition(player.getx(),player.gety());
            s.setPosition(s.getx(),s.gety());
            s1.setPosition(s1.getx(),s1.gety());
            s2.setPosition(s2.getx(),s2.gety());
            s3.setPosition(s3.getx(),s3.gety());
            s4.setPosition(s4.getx(),s4.gety());
        }

        explosions = new ArrayList<>();

        hud = new HUD(player);



    }


    /**
     *
     * Inte fel med att köra WIDTH? Då det inte är den bredden på banan?
     */
    @Override
    public void update() {
        player.update();

        tileMap.setPosisition(

                GameFrame.WIDTHLV1 / 2- player.getx(),
                GameFrame.HEIGHTLV1 / 2 - player.gety()
        );

        // set background
        // så den rör sig lite när man gåt
        //TODO se video 6


        // attack enemies  (pass over the arrayList of enemies
        player.checkAttack(enemies);


        // update all enemies
        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if(e.isDead()) {
                enemies.remove(i);
                i--;


                // Jag castar om double till int här annars fungerar inte explosions.add(new Explosion
                int intx = (int) e.getx();
                int inty = (int) e.gety();


                explosions.add(new Explosion(intx, inty));
            }

        }


        // update explosions
        for(int i = 0; i < explosions.size(); i++) {

            explosions.get(i).update();
            if(explosions.get(i).shouldRemove()) {
                explosions.remove(i);
            }

        }



        //TODO PAPPA FIXAR
        if(player.gety() > 400) {
            prepareStateChange();
            gsm.setState(4);
        }


    }

    @Override
    public void draw(Graphics2D g) {


        if(counter <= (urls.length/GifBgFps)*60 && gsm.getPrevState()==0){
            if(counter==0 && gsm.getCurrentState() == 1){
                if(OptionsState.SoundOptions[OptionsState.currentSoundChoice].equals("On")) {
                    LaserSound.play();
                }
            }

            gifBG.drawNextAndStop(g);
            counter++;
        }else {
            if(!SoundStarted){
                System.out.println("PlayedBGSONG");
                if(OptionsState.SoundOptions[OptionsState.currentSoundChoice].equals("On")){
                    BGSong.loop();
                }
                SoundStarted = true;
            }
            bg.draw(g);
            tileMap.draw(g);
            player.draw(g);

            // draw enemies
            for(int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g);

            }

            // draw explosions
            for(int i = 0; i < explosions.size(); i++ ) {
                explosions.get(i).draw(g);
            }


            // draw HUD
            hud.draw(g);
        }


    }

    @Override
    public void keyPressed(int k) {

        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_SPACE) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_R) player.setSwordAttack();
        if(k == KeyEvent.VK_F){

            if(player.getLazer()>200){
                player.setFiring();
            }else if(OptionsState.SoundOptions[OptionsState.currentSoundChoice].equals("On")){
                EmptyClip.play();
            }
        }


        if(k == KeyEvent.VK_ESCAPE){
            prepareStateChange();
            gsm.setState(2);

        }


        if(k == KeyEvent.VK_SPACE){

        }
    }
    @Override
    public void keyReleased(int k) {

        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_SPACE) player.setJumping(false);
        if(k == KeyEvent.VK_E) player.setGliding(false);


    }
    private void prepareStateChange(){
        player.stopPlayer();
        gifBG.setPlayedOnce(false);
        LaserSound.stop();
        BGSong.stop();
        counter= 0;
        SoundStarted = false;

    }


}