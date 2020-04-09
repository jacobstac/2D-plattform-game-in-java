package PhysicalObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jacob on 2017-05-05.
 *
 * Visuell HUD för spelaren där man senare ska se liv, poäng, mana, ammo, lvl, osv
 */
public class HUD {


    //all information på skärmen om spelaren

    private Player player;

    private BufferedImage image;
    private Font font;



    public HUD(Player p) {

       player = p;
       try {

           image = ImageIO.read(

                   getClass().getResourceAsStream(
                           "/HUD/hud.png"
                   )

           );
           font = new Font("Arial", Font.BOLD, 12);





       } catch (Exception e) {

           e.printStackTrace();

       }



    }

    public void draw(Graphics2D g) {


        g.drawImage(image, 0, 10, null);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(player.getHealth() + " / " + player.getMaxHealth(), 30, 25);
        g.drawString(player.getLazer() / 100 + " / " +player.getMaxLazer() /100, 30, 45);

    }









}
