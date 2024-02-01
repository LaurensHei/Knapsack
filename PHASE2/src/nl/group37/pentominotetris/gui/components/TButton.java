package gui.components;

import javax.swing.*;

import gui.UI;

import java.awt.*;

public class TButton extends JButton{

    Font gameFont;
    /**  constuctor     **/
    public TButton(String text, int color){
        super(text);
        ImageIcon icon = new ImageIcon(getClass().getResource("../assets/button.png"));
        setIcon(icon);

        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
        setFocusable(false);
        setForeground(Color.white);

        setFont(UI.gameFont);

    }


}
