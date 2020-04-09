package PhysicalObjects;

import GameStateStuff.GameStateHandler;
import GameStateStuff.Level1State;
import GameStateStuff.OptionsState;
import Main.SoundClip;
import MapStuff.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Created by Jacob on 2017-05-02.
 * Ärver från MapObject
 *
 *
 */
public class Player extends MapObject {


    private int AntonsTestSak;

    //SoundEffects
    private SoundClip LaserBlast = new SoundClip("/Audio/Effects/LaserBlast.mp3");

    private GameStateHandler gsm;
    private Level1State lvl1;


    //platyer type variables
    private int health;
    private int maxHealth;
    private int lazer;
    private int maxLazer;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;

    // Lazer
    private boolean firing; //for keyboard input
    private int lazerCost;
    private int lazerDamage;

    //skapar en arraylist av lazerskott
    private ArrayList<LazerBullet> lazerBullet;


    // swordAttack
    private boolean swordAttack;
    private int swordDamage;
    private int swordRange;


    //Gliding...? Ska vi ha med detta i början av spelet? vore nice att ha som en
    //sak man får senare. och sen kan återkomma till tidare områden, nå ställen man inte kunde innan??
    private boolean gliding;

    // Animations.
    // skapa en arraylist av bufferedimage
    // detta är enums som påminner om gameStateManager
    private ArrayList<BufferedImage[]> sprites;
    //vi behöver ha antalet frames inuti varje animation action
    private final int[] numFrames = {

            6, // IDLE
            10, // WALKING
            2, // JUMPING
            2, // FALLING
            4, // GLIDING
            2, // LAZERSHOT
            8,  // SWORDATTACK



    };

    // Animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 6;
    private static final int JUMPING = 4;
    private static final int FALLING = 3;
    private static final int GLIDING = 2;
    private static final int LAZERSHOT = 5;
    private static final int SWORDATTACK = 1;


    // Constructor
    public Player(TileMap tm) {


        super(tm); //Ordnar enligt superklassen

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 5;
        lazer = maxLazer = 2500;

        lazerCost = 200;
        lazerDamage = 5;

        lazerBullet = new ArrayList<LazerBullet>();

        swordDamage = 8;
        swordRange = 40;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Player/playersprites.png"



                    )

            );

            sprites = new ArrayList<BufferedImage[]>();




            for(int i=0; i < 7; i++ ) { // 7 eftersom vi har 7 animation actions

                BufferedImage[] bi = new BufferedImage[numFrames[i]];

                // nu måste vi läsa in frame soch visuella sprites
                for(int j=0; j<numFrames[i]; j++) {

                    if (i == 6) { //eftersom när i = 6 så är spriten 60 i width inte 30 som de andra
                        bi[j] = spritesheet.getSubimage(
                                j * 34,
                                i * height,
                                34,
                                height //Detta kan bli problem då alla sprites kanske inte är lika stora
                        );

                    }
                    else if (i == 5) { //eftersom när i = 6 så är spriten 60 i width inte 30 som de andra
                        bi[j] = spritesheet.getSubimage(
                                j * 40,
                                i * height,
                                40,
                                height //Detta kan bli problem då alla sprites kanske inte är lika stora
                        );

                    }

                    else
                    { //eftersom när i = 6 så är spriten 60 i width inte 30 som de andra
                        bi[j] = spritesheet.getSubimage(
                                j * width,
                                i * height,
                                width,
                                height //Detta kan bli problem då alla sprites kanske inte är lika stora
                        );

                    }
                }

                // mush add to the aimation
                sprites.add(bi);

            }

        } catch(Exception e) {

            e.printStackTrace();
        }



        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);







    }


    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getLazer() {
        return lazer;
    }

    public int getMaxLazer() {
        return maxLazer;
    }


    //keyboardinputs.
    public void setFiring() {
        firing = true;
    }

    public void setSwordAttack() {
        swordAttack = true;
    }

    //anledningen till att vi använder "boolean b" är för att vi ska kunna sluta glida närsomhelst
    //Medans en attack alltid följs igenom
    public void setGliding(boolean b) {
        gliding = true;
    }


    // som metodnamnet säger
    public void checkAttack(ArrayList<Enemy> enemies) {


        // loop trough enemies
        for(int i = 0; i < enemies.size(); i++) {

            Enemy e = enemies.get(i);

            // swordAttack check
            if(swordAttack){
                if(facingRight) {
                    if(e.getx() > x && e.getx() < x + swordRange && e.gety() > y - height/2 && e.gety() < y + height/2) {

                        // då slå med den dammage sword fåttt
                        e.hit(swordDamage);
                    }

                }
                else {
                    if(e.getx() < x && e.getx() > x + swordRange && e.gety() > y - height/2 && e.gety() < y + height/2) {

                        // då slå med den dammage sword fåttt
                        e.hit(swordDamage);
                    }

                }

            }


            //Lazershot
            for(int j = 0; j < lazerBullet.size(); j++) {
                if(lazerBullet.get(j).intersects(e)){

                    e.hit(lazerDamage);
                    lazerBullet.get(j).setHit();
                    break;




                }



            }

        }


    }




    // should determine where the player will be next genom att läsa keyboard input
    // till exempel pressing left, nästa position är till vänder
    private void getNextPosition() {

        // movement
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
        else {
            if(dx > 0) {
                dx -= stopSpeed;
                if(dx < 0) {
                    dx = 0;
                }
            }
            else if(dx < 0) {
                dx += stopSpeed;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }

        //check: cannot attack while moving unless you in the air
        // lägg till || currentAction == LAZERSHOT om du vill att spelaren står still när hon skjuter
        if((currentAction == SWORDATTACK ) && !(jumping || falling)) {
           dx = 0;
        }


        // jumping
        if(jumping && !falling) {
            dy = jumpStart;
            falling = true;
        }

        // falling
        if(falling) {

            if(dy > 0 && gliding) dy += fallSpeed * 0.1;
            else dy += fallSpeed;

            if(dy > 0) jumping = false;
            if(dy < 0 && !jumping) dy += stopJumpSpeed; //the longer you hold jumpbutton the lnger you jump
            if(dy > maxFallSpeed) dy = maxFallSpeed;


        }


    }

    //Detta är tydligen rätt så komplicerat
    public void update() {

        //Uppdatera position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        // Kolla att attack har slutat (så den inte bara fortsätter att attackera
        // när man klickat på attackera)
        if(currentAction == SWORDATTACK) {
            //Då måste vi kolla om animationen spelats en gång redan
            if(animation.hasPlayedOnce())  swordAttack = false;

        }
        // samma sak med lazerattacken
        if(currentAction == LAZERSHOT) {
            //Då måste vi kolla om animationen spelats en gång redan
            if(animation.hasPlayedOnce()) firing = false;
        }

        // LaserShot attack.

        // continious regenerate lazer energy
        lazer += 1;
        // Cap it at maxLazer
        if(lazer > maxLazer) lazer = maxLazer;

        if(firing && currentAction != LAZERSHOT) {
            // Vi måste kolla om vi har tillräckligt energi för att skjuta lazer
            if(lazer > lazerCost) {
                if(OptionsState.SoundOptions[OptionsState.currentSoundChoice].equals("On")){
                    LaserBlast.play();
                }
                lazer -= lazerCost;
                LazerBullet lb = new LazerBullet(tileMap, !facingRight);
                lb.setPosition(x, y);
                //lägger in den i arraylist
                lazerBullet.add(lb);
            }
        }
        // uppdatera lazershot när den skjuts
        for(int i = 0; i < lazerBullet.size(); i++) {
            lazerBullet.get(i).update();

            //göra så lazern försvinner
            if(lazerBullet.get(i).shouldRemove()) {
                lazerBullet.remove(i);
                i--;
            }

        }



        //set animation
        if(swordAttack) {
            if(currentAction != SWORDATTACK ) {
                currentAction = SWORDATTACK;
                animation.setFrames(sprites.get(SWORDATTACK));
                animation.setDelay(50);
                width = 60;

            }
        }
        else if(firing && (lazer > 1 )) {
            if(currentAction != LAZERSHOT) {
                currentAction = LAZERSHOT;
                animation.setFrames(sprites.get(LAZERSHOT));
                animation.setDelay(100);
                width = 30;
            }
        }
        else if(dy > 0){ //Falling
            if(gliding) {
                if(currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 30;
                }
            }
            else if(currentAction != FALLING){
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;

            }


        }
        else if(dy < 0) {
            if(currentAction != JUMPING){

                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;
            }

        }
        else if(left || right) {
            if(currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
                width = 34;  // 34 pixlar
            }
        }
        else {
            if(currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 30;

            }
        }

        animation.update();

        //Set direction
        if(currentAction != SWORDATTACK && currentAction != LAZERSHOT) {
            if(right) facingRight = false;
            if(left) facingRight = true;

        }


        //todo Jag försöker ordna så att man dör när man faller under banan.

        // if(gety() > 400) {
        // }


    }




    // Detta kommer att rita spelaren.
    public void draw(Graphics2D g) {

        //Detta är det första som ska köras vid varje MapObject draw
        setMapPosition();


        // Draw Lazer
        for(int i = 0; i < lazerBullet.size(); i++) {


            lazerBullet.get(i).draw(g);

        }

        // Draw player
        if(flinching) {
            long elapsed = (System.nanoTime()-flinchTimer) / 1000000;

            //Detta kommer att få spelaren att blinka varje 100ade millisekund. bra när spelaren blir slagen
            if(elapsed / 100 % 2 == 0) {
                return;
            }
        }

        //Kallar på drawmedtoden i suprklassen mapobject
        super.draw(g);
    }
    public void stopPlayer(){
        setLeft(false);
        setRight(false);
        setVector(0,0);
    }
}