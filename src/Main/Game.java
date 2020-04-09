package Main;

import javax.swing.*;
import java.awt.image.BufferStrategy;

/**
 * Main-klassen för det här projektet, kommer endast vara till för att starta igång spelet och
 * initera det nödvändiga i vår JFRAME.
 **/
public class Game {

    public static JFrame window;


    public static void main(String[] args){

        window = new JFrame("A Bad Hair day");
        window.setContentPane(new GameFrame()); // Detta
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); //Just nu ska fönstret gå att justera
        window.pack();
        window.setVisible(true);

    }


}
