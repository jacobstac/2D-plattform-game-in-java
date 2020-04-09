package PhysicalObjects.PhysicalObjects.Enemies;

import MapStuff.TileMap;
import PhysicalObjects.Animation;
import PhysicalObjects.Enemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jacob on 2017-05-04.
 *
 * test fiendeklass
 * Detta är en grundläggande fiende som rör sig mellan väggar fram och tillbaka
 * Skapas i init i level1State

 */
public class Sten extends Enemy {

    // Fields


    // double tid1 = System.nanoTime();
    //Vi behöver en array som håller i alla spritebilder
    private BufferedImage[] sprites;




    // Constructor
    public Sten(TileMap tm) {

        super(tm);

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.1;
        maxFallSpeed = 4;

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        health = 2;
        damage = 1;


        // load sprites
        try {



            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Enemies/slugger.png")

            );

            sprites = new BufferedImage[2];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;

    }

    /**
     * Den här Papperskorgen ska gå mellan väggar. den ska gå mot en vägg och vända vid nudd
     * Det är det denna klass ska ordna
     */
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

        if(falling) {
            dy += fallSpeed;

            

        }



    }

    public void update() {

        // uppdatera position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //check flinching. Sve = svikta rygga tillbaka rycka till av smärta 
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400) {
                flinching = false;
            }

        }

        //if() {
//            right = true;
//            left = false;
//
//
//
//        }
//        if(bottomRight) {
//            right= false;
//            left = true;

       // }

        // Här kan vi sätta det faktiska rörelseschemat, rörelserna
        // if it is a wall go other direction
        if(right && (dx == 0 || !bottomLeft) ) {                // todo jag har lagt till || !bottomRight i ett försök att hindra fienden att hoppa från en kant....
            right= false;
            left = true;
            facingRight = false;

        } else if (left && (dx == 0 || !bottomRight)) {         // todo samma som ovan här

            right = true;
            left = false;
            facingRight = true;



        }

/*
        if(!bottomRight || !bottomLeft){
            if(facingRight){
                //right= false;
               // left = true;
                facingRight = false;

            }else {
               // right = true;
                //left = false;
                facingRight = true;
            }
        }*/


        // update animation
        animation.update();





    }



    public void draw(Graphics2D g) {

        // så inte fienden ritas när den är utanför det man ser på skärmen
        // todo nedan fungerar inte på ett bra sätt. fienderna försvinner utanför rutan.
        //if(notOnScreen()) return;

        setMapPosition();

        super.draw(g);
    }



}
