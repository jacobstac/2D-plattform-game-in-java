package MapStuff;

import Main.GameFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Antonivar on 2017-04-22.
 */
public class TileMap {

    private Random rand;
    private int lightFlicker = 0;

    // Position
    private double x;
    private double y;

    //bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private double tween;
    private double smoothScrool;
    /**
     * Kartan som är en array av siffror som representerar var respektive numrerade tile kommer att placerar ut.
     */
    private int[][] map;
    private int tileSize;  // Storleken på varje tile? vilken dimension?? vad menas? Tror de ska vara fyrkanter alltså måttet för både height och width
    private int numRows;    // Antalet rader i kartan.
    private int numCols;       // Antalet kolumner i kartan.

    //Width för tile?
    private int width;
    private int height;

    //image for tileSet
    private BufferedImage tileSet; //Bilden med tiles som sedan kommer delas upp till separata "brickor" för att rendera till vår karta
    private int numTilesAcross;
    private Tile[] tilesNormal;  //Matris med tiles dessa laddas in under loadTiles
    private Tile[] tilesBlocked;
    //Antal tiles som inte är blockerade


    //DrawingStuff
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    public TileMap(int tileSize) {
        rand = new Random();
        this.tileSize = tileSize;
        numRowsToDraw = GameFrame.HEIGHTLV1 / tileSize + 2;  //Vi tar reda på hur många rader och kolumner som vi ska rita och lägger till två extra som en saftey messure.
        numColsToDraw = GameFrame.WIDTHLV1 / tileSize + 2;
        tween = 0.07; //smoothScrool

    }

    public void loadTiles(String s) {
        try {
            tileSet = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileSet.getWidth() / tileSize;
            tilesNormal = new Tile[numTilesAcross];
            tilesBlocked = new Tile[numTilesAcross];
            BufferedImage subImage;
            /**
             * Loop som ska ladd in alla subimages till vår matris med bilder "tiles" vå går från kolumn 0 till antalet tiles som finns i hela bilden.
             * Tiles
             *Förstsa raden i matrisen kommer innehålla de blocks som inte är blockerade och andra raden kommer innehålla de blocks som är blockerade.
             */
            for (int col = 0; col < numTilesAcross; col++) {
                subImage = tileSet.getSubimage(col * tileSize, 0, tileSize, tileSize);                    //den här hämtar alltså allt som finns på den översta raden i vår tileSet
                tilesNormal[col] = new Tile(subImage, Tile.NORMAL);
                subImage = tileSet.getSubimage(col * tileSize, tileSize,tileSize,tileSize);
                tilesBlocked[col] = new Tile(subImage, Tile.BLOCKED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metod för att ladda in vår .map som innehåller siffror för vart varje tile ska vara. Metoden hämtar in filen via en sökväg och läser sen av varje rad i filen.
     * @param s är sökvägen till .map-filen
     */
    public void loadMap(String s){
        try{

            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            numCols = Integer.parseInt(reader.readLine());  //Det två första raderna i vår .map fil motsvarar antalet kolumner med rutor i vår map respektive antalet rader.
            numRows = Integer.parseInt(reader.readLine());
            map = new int[numRows][numCols];     // Alokerar plats för samtliga Integers i vår .map
            width = numCols * tileSize;
            height = numRows * tileSize;

            xmin = GameFrame.WIDTHLV1-width;
            xmax = 0;
            ymin = GameFrame.HEIGHTLV1-height;
            ymin = 0;


            String whiteSpacePattern = "[,]";                            //Regex för att hitta ett eller fler mellanslag
            for(int row = 0; row < numRows;row++){
                String line = reader.readLine();
                String[] tokens = line.split(whiteSpacePattern);          //Splittar upp alla strängar i tokens och sparar dem i en array
                for(int col = 0; col < numCols;col++){
                    map[row][col] = Integer.parseInt(tokens[col]);         //Laddar in varje token i varje kolumn på varje rad. Efter denna iteration har vi vår matris med Integers som representerar kartan.
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getType(int row, int col) {
        int rc = map[row][col];
        if(rc>= numTilesAcross)
            return Tile.BLOCKED;
        else return Tile.NORMAL;
    }


    public void setPosisition(double x, double y){
        this.x += (x - this.x) * tween; // smoothScrool;
        this.y += (y -this.y) * tween; // smoothScrool;
        fixBounds();

        // TODO  nedan, vad menas med (int) minus något. (int) har väl inget värde??????
        // TODO  nedan, vad menas med (int) minus något. (int) har väl inget värde??????
        // TODO  nedan, vad menas med (int) minus något. (int) har väl inget värde??????


        colOffset = (int)-this.x / tileSize;
        rowOffset = (int)-this.y / tileSize;

    }

    /**
     *
     * Metod som i en loop ritar alla tiles och genererar på så sätt mappen.
     * @param g
     */
    public void draw(Graphics2D g){

        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row ++){

            if(row >= numRows)break;

            for(int col = colOffset; col < colOffset + numColsToDraw; col++){

                Tile tileToDraw;

                if(col>= numCols)break;
                if((map[row][col]) == 0) continue;

                if(map[row][col]==34){
                    if(rand.nextInt(10000)>9980 || (lightFlicker < 300 && lightFlicker > 0)){
                        if(rand.nextInt(10000)>8500 && lightFlicker <120){
                            tileToDraw = tilesBlocked[34-22];

                        }else {
                            tileToDraw = tilesBlocked[33 - 22];
                        }
                        lightFlicker ++;
                        g.drawImage(tileToDraw.getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
                        if(lightFlicker==299){
                            lightFlicker = 0;
                        }
                    }else{
                        tileToDraw = tilesBlocked[34-22];
                        g.drawImage(tileToDraw.getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
                    }

                }else {

                    if (getType(row, col) == Tile.NORMAL) {

                        tileToDraw = tilesNormal[map[row][col] - 1];

                    } else {
                        tileToDraw = tilesBlocked[map[row][col] - 22];
                    }
                    g.drawImage(tileToDraw.getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);

                }

            }

        }
    }

    private void fixBounds(){

        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x>xmax) x = xmax;
        if(y>ymax) y = ymax;
    }

    /**
     * Lite Getters som kan bli nödvändiga
     *
     */
    public int getTileSize(){return tileSize;}
    public double getX() {return x;}
    public double getY() {return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getNumRows() { return numRows; }
    public int getNumCols() { return numCols; }


    //public void setSmoothScrool(double smoothScrool){this.smoothScrool = smoothScrool;}


    public void setTween(double t)
    {
        tween = t;
    }


}