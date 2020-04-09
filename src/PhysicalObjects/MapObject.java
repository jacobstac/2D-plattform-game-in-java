package PhysicalObjects;

import Main.GameFrame;
import MapStuff.*;
//import javafx.animation.Animation;

import java.awt.*;

/** TODO måste ordna så att Fiender/Player ska kunna attackera/skada/nudda/påverka varandra
 * Detta är en superklass som alla kartobjekt kom att härstamma från
 * Det kan handla om spelaren, fiender, explosioner och
 * Created by Jacob on 2017-04-28.
 */
public abstract class MapObject {

    private double xTempOld = 0;

    //tilestuff.
    //Eftersom detta är en abstract superclass så måste alla
    //allting vara "protected" så att alla subclasser faktiskt kan se detta

    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap;
    protected double ymap;

    //Postion och vektor
    protected double x;
    protected double y;
    //riktiningen objektet är påväg till
    protected double dx;
    protected double dy;

    // dimensioner. Mest för att läsa in "SPITE SHEETS" som vi kommer att använda
    protected int width;
    protected int height;

    //Collision box
    //Det är detta som kommer att avgöra kollisioner med tiles eller andra entiteter
    //Det är dessa dimensioner som är det "riktiga" dimensionerna

    protected int cwidth;
    protected int cheight;

    //Collision
    protected int currRow;
    protected int currCol;
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    // Animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;

    // movement
    // boleans that decide what the object s doin
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    //Movement attribute
    //Physics
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed; //Gravity
    protected double maxFallSpeed;
    protected double jumpStart; //Hur högt objekt kan hoppa
    protected double stopJumpSpeed; //Bror på hur länge man håller i hoppknappem


    //Constructor
    public MapObject(TileMap tm) {

        tileMap = tm;
        tileSize = tm.getTileSize();

    }

    //Kollar om den kan krocka med andra kartObjekt
    //Returnerar true om objekten nuddar
    public boolean intersects(MapObject o) {

        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);

    }

    public Rectangle getRectangle(){

        return new Rectangle(
                (int)x - cwidth,
                (int)y - cheight,
                cwidth,
                cheight
        );

    }
    //
    public void calculateCorners(double x, double y) {



       //ta reda på
        int leftTile = (int)(x - cwidth / 2) / tileSize;
        int rightTile = ((int)(x + cwidth / 2 - 1) / tileSize); //-1 så att vi inte går in i nästa column
        int topTile = (int)(y - cheight / 2) / tileSize;
        int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
        /**
         * TODO
         *
         *
         * Ta bort kommentar nedan när du ordnat metodern getNumRows och  getNumCols
         */
        if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
            topLeft = topRight = bottomLeft = bottomRight = false;
            return;
        }

        //tl = topleft osv.
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);

        //Nu kan vi set the 4 booleans för dessa
        //t.ex om vi hoppar mot en kant på topLeft så blir den true
        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;

    }

    //Kollar om vi har springit in i normal eller blocked tile
    public void checkTileMapCollision() {


        //först måste vi kolla vilken kolonn vi är på
        currCol = (int)x / tileSize;
        //rad
        currRow = (int)y / tileSize;

        //Next destionation position
        xdest = x + dx;
        ydest = y + dy;

        //temp för att hålla reda på orginal x och y för att vi kommer att
        //göra final changes till x och y genom att använda xtemp ytemp
        xtemp = x;
        ytemp = y;

        calculateCorners(x, ydest);
        //Betyder att vi är påväg uppåt
        if(dy < 0) {
            //Om nästa ställe är topRight || topLeft, då måste vi stanna
            if(topRight || topLeft) {

                //Stanna = hastighet = 0
                dy = 0;
                //Då stannar vi precis innan det blockerade objektet
                ytemp = currRow * tileSize+cheight / 2;
            }else {
                //Annars är vi fria att fortsätta i den riktningen
                ytemp += dy;

            }

        }
        //NU är vi påväg ner
        if(dy > 0) {
            //NU har vi landat på ett hörn
            //Check the bottem corners
            if(bottomRight || bottomLeft) {
                //Stanna = hastighet = 0
                dy = 0;
                //Falling är falsk då vi landat på något
                falling = false;
                //+1 då vi hamnat precis på en tile
                ytemp = (currRow + 1) * tileSize - cheight /2;

            }else {
                //Annars är vi fria att falla
                ytemp += dy;

            }

        }

        calculateCorners(xdest, y);
        //nu går vi åt vänster
        if(dx < 0) {
            //Kolla left corners
            if(topLeft || bottomLeft){
                //då måste vi stanna.
                dx = 0;
                xtemp = currCol * tileSize + cwidth / 2;

            }else {
                //Annars är vi fria att gå mot vänster
                xtemp += dx;
            }
        }
        //nu går vi mot höger
        if(dx > 0) {
            if(topRight || bottomRight){
                dx = 0;
                xtemp = (currCol + 1) * tileSize - cwidth / 2 ;
            }else {
                xtemp += dx;
            }
        }

        if(!falling) {

            calculateCorners(x, ydest +1);
            //om vi inte står på "solid ground":
            if(!bottomRight && !bottomLeft) {
                falling = true;

            }

        }



    }


    public double getx() {
        return x;
    }

    public double gety(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public int getCWidth(){
        return width;
    }

    public int getCHeight(){
        return cheight;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public void setVector (double dx, double dy) {

        this.dx = dx;
        this.dy = dy;
    }

    //Detta är VÄLDIGT viktigt. Varje MapObject har två olika positioner
    //Ena är  global position som är regual x och y
    //de andr aär lokala position vilket är deras position plus tileMps posittion
    public void setMapPosition() {
        xmap = tileMap.getX();
        ymap = tileMap.getY();

        //hjälper oss att rita personen. om vå gubbe är utanför banan.
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void setDown(boolean b) {
        down = b;
    }

    public void setJumping(boolean b) {
        jumping = b;
    }

    //Vi vill inte rita objekt som int eär på skärmen
    public boolean notOnScreen() {
        return  x + xmap + width < 0 ||                 //om objektet bortom vänster sida av skärmen
                x + xmap - width > GameFrame.WIDTHLV1 ||   //om objektet bortom höger sida av skärmen
                y + ymap + height < 0 ||                //om objektet ovanför skärmen
                y + ymap - height > GameFrame.HEIGHTLV1 ;   //om objektet under skärmen

    }

    public void draw(Graphics2D g) {
        if(facingRight) {
            g.drawImage(
                    animation.getImage(),
                    (int)(x + xmap - width / 2),
                    (int)(y + ymap - height / 2),
                    null

            );
        }
        else {
            g.drawImage(
                    animation.getImage(),
                    (int)(x + xmap - width / 2 + width),
                    (int)(y + ymap - height / 2),
                    -width,
                    height,
                    null



            );
        }
    }








}
