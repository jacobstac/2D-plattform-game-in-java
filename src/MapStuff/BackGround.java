package MapStuff;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Antonivar on 2017-04-20.
 */
public class BackGround {

    private BufferedImage image;

    private double x = 0;
    private double y = 0;

    public BackGround(String s){

        try{
            image = ImageIO.read(getClass().getResourceAsStream(s));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g){
        g.drawImage(image, (int)x, (int)y, null);
    }

}
