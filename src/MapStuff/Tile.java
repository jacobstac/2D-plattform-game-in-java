package MapStuff;

import java.awt.image.BufferedImage;

/**
 * Created by Antonivar on 2017-04-22.
 */
public class Tile {

    private BufferedImage image;
    private int type;

    //Typer av tiles
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;

    public Tile(BufferedImage image, int type){
        this.image = image;
        this.type = type;
    }
    //GETTERS
    public BufferedImage getImage(){
        return image;
    }
    public int getType(){return type;}

}
