package PhysicalObjects;

import GameStateStuff.OptionsState;
import Main.SoundClip;
import MapStuff.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jacob on 2017-05-04.
 * Även denna klass är ett barn till MapObject
 */
public class LazerBullet extends MapObject{


    // FIELDS
    private SoundClip HitSound = new SoundClip("/Audio/Effects/Squish.mp3");


    //säger till om lazern har träffat något
    private  boolean hit;

    // Om eller inte vi ska ta bort lazerskottet från spelet
    private boolean remove;

    //Vi har två "sets" av bufferedimage arrays.
    // 1. för den vanliga spriten
    private BufferedImage[] sprites;
    // 2. för den animationen som spelas när lasern träffar något
    private BufferedImage[] hitSprites;




    // Constructor
    public LazerBullet(TileMap tm, boolean right) {

        super(tm);

        moveSpeed = 3.8;
        if(right) dx = moveSpeed;
        else dx = -moveSpeed;

        width = 30;
        height = 30;

        //den riktiga höjden och vidden
        cwidth = 14;
        cheight = 14;

        //load sprites
        try{

            //först måste vi läsa in hela spritessheeten
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/lazerball.png"));

            //Nu måste vi

            sprites = new BufferedImage[4];
            for(int i = 0; i < sprites.length; i++) {

                //JAg tror det är här den choppar upp bilden, inte 100 på det
                sprites[i] = spritesheet.getSubimage(

                        i * width,
                        0,
                        width,
                        height
                );
            }

            hitSprites = new BufferedImage[3];
            for(int i = 0; i < hitSprites.length; i++) {

                hitSprites[i] = spritesheet.getSubimage(

                        i * width,
                        height,
                        width,
                        height
                );
            }

            // set the animationstuff
            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);



        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    // det här är funktionen som blir kallad för att lista ut om lasern har träffat något eller inte
    public void setHit() {
        if(hit) return;
        if(OptionsState.SoundOptions[OptionsState.currentSoundChoice].equals("On")){
            HitSound.play();
        }
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        // vi måste får det hela att sluta röra sig
        dx = 0;
    }

    // om eller inte vi ska ta bort lasern från kartan
    public boolean shouldRemove() {
        return remove;
    }

    public void update() {


        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //Den här ser till att lazern försvinner
        if(dx == 0 && !hit) {
            setHit();
        }

        animation.update();
        if(hit && animation.hasPlayedOnce()) {

            remove = true;


        }


    }

    public void draw(Graphics2D g) {

        setMapPosition();

        //Kallar på drawmedtoden i suprklassen mapobject
        //ritar allt åt rätt håll
        super.draw(g);


    }









}