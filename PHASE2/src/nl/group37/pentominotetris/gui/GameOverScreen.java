package gui;

import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JFrame{


    public GameOverScreen() {
        Container c = getContentPane();
        c.setBackground (Color.RED);

        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("assets/game over.gif"));
        label.setIcon(icon);
        c.add(label);



        setTitle ("Game Over");
        setSize (360, 360);
        setLocationRelativeTo(null);
        setVisible (true);
        //setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        try {
            Thread.sleep(5000);
            dispose();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new GameOverScreen();

    }
}
