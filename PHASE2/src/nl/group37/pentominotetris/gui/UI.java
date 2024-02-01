package gui; /**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import gui.components.TButton;
import main.TetrisGame;
import scores.ReadScores;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This class takes care of all the graphics to display a certain state.
 * Initially, you do not need to modify (or event understand) this class in Phase 1. You will learn more about GUIs in Period 2, in the Introduction to Computer Science 2 course.
 */
public class UI extends JPanel
{
    private JFrame window;
    private int[][] state;
    private int[][] nextBlock;
    private int size;

    public static Font gameFont;

    private int offsetX;
    private int offsetY;

    private int offsetNextX;
    private int offsetNextY;

    private static JLabel playersName;
    private static JLabel time;
    private static JLabel score;
    private static JLabel level;
    private static JLabel highscore;

    private static TButton botSwitch;
    private static TButton pauseGame;
    private static TButton newGame;

    private static TButton optimalOrder;
    private static JLabel gameOver;

    private static JLabel top1, top2, top3;
    private ImageIcon background;
    private Clip backgroundMusic;

    /**
     * Constructor for the GUI. Sets everything up
     * @param x x position of the GUI
     * @param y y position of the GUI
     * @param _size size of the GUI
     */
    public UI(int x, int y, int _size)
    {
        background = new ImageIcon(getClass().getResource("assets/background.png"));
        loadBackgroundMusic("assets/background_music.wav");
        


        state = new int[x][y];
        nextBlock = new int[5][3];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }

        for (int i = 0; i < nextBlock.length; i++) {
            for (int j = 0; j < nextBlock[i].length; j++) {
                nextBlock[i][j] = -1;
            }
        }

        size = _size;
        setPreferredSize(new Dimension((x * size)+550, (y * size)+100));

        offsetX = 200;
        offsetY = 50;
        offsetNextX = offsetX + (x*size) + 39;
        offsetNextY = 327;

        window = new JFrame("Pentomino");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(this);
        window.pack();
        window.setVisible(true);
        window.setLocationRelativeTo(null);

        try {
            InputStream is = getClass().getResourceAsStream("assets/Early GameBoy.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(gameFont);

        buildUI();
    }

    private void buildUI() {
        setLayout(null);

        int buttonHeight = 30;
        int buttonWidth = 150;
        int marginTop = 50;
        int marginX = 20;
        int marginY = 10;

        int xCount = 1;
        int yCount = 1;


        playersName = new JLabel("Player's Name");
        playersName.setBounds(marginX*xCount+10,marginTop,buttonWidth, buttonHeight);
        playersName.setFont(gameFont);
        playersName.setForeground(Color.white);
        this.add(playersName);

        gameOver = new JLabel();
        gameOver.setFont(gameFont);
        gameOver.setForeground(Color.white);
        gameOver.setBounds(marginX*11,marginTop-40,buttonWidth, buttonHeight);
        this.add(gameOver);


        yCount++;

        TButton changeName = new TButton("Change Name", 1);
        changeName.setBounds(marginX*xCount,(marginY+buttonHeight)*yCount,buttonWidth, buttonHeight);
        changeName.setFocusable(false);
        this.add(changeName);

        yCount +=6;

        newGame = new TButton("New Game", 1);
        newGame.setBounds(marginX*xCount,(marginY+buttonHeight)*yCount,buttonWidth, buttonHeight);
        this.add(newGame);

        yCount ++;
        pauseGame = new TButton("Pause Game", 1);
        pauseGame.setBounds(marginX*xCount,(marginY+buttonHeight)*yCount,buttonWidth, buttonHeight);
        pauseGame.setFocusable(false);
        this.add(pauseGame);


        yCount += 6;
        botSwitch = new TButton("Bot: OFF", 1);
        botSwitch.setBounds(marginX*xCount,(marginY+buttonHeight)*yCount,buttonWidth, buttonHeight);
        botSwitch.setFocusable(false);
        this.add(botSwitch);

        yCount ++;

        optimalOrder = new TButton("OptOr: OFF", 1);
        optimalOrder.setBounds(marginX*xCount,(marginY+buttonHeight)*yCount,buttonWidth, buttonHeight);
        optimalOrder.setFocusable(false);
        this.add(optimalOrder);


        xCount = 2;
        yCount = 2;
        time = new JLabel("Time: --:--:--");
        time.setFont(gameFont);
        time.setBounds((marginX+buttonWidth+50)*xCount,marginTop,buttonWidth+100, buttonHeight);
        time.setForeground(Color.white);

        this.add(time);

        score = new JLabel("Score: -");
        score.setFont(gameFont);
        score.setBounds((marginX+buttonWidth+50)*xCount,(marginY+buttonHeight)*yCount,buttonWidth, buttonHeight);
        score.setForeground(Color.white);

        this.add(score);

        xCount ++;
        level = new JLabel("Level: -");
        level.setFont(gameFont);
        level.setBounds((marginX+buttonWidth+40)*xCount,(marginY+buttonHeight)*yCount,buttonWidth, buttonHeight);
        level.setForeground(Color.white);

        this.add(level);

        xCount--;
        yCount ++;
        highscore = new JLabel("Leaderboard:");
        highscore.setFont(gameFont);
        highscore.setBounds((marginX+buttonWidth+50)*xCount,(marginY+buttonHeight)*yCount,buttonWidth+100, buttonHeight);
        highscore.setForeground(Color.white);

        this.add(highscore);

        yCount++;
        top1 = new JLabel("1.  Laurens : 234324");
        top1.setFont(gameFont);
        top1.setForeground(Color.decode("#059E38"));
        top1.setBounds((marginX+buttonWidth+60)*xCount,(marginY+buttonHeight)*yCount,buttonWidth+100, buttonHeight);
        this.add(top1);

        yCount++;
        top2 = new JLabel("2.  Laurens : 34545");
        top2.setFont(gameFont);
        top2.setForeground(Color.decode("#07A5E9"));
        top2.setBounds((marginX+buttonWidth+60)*xCount,(marginY+buttonHeight)*yCount,buttonWidth+100, buttonHeight);
        this.add(top2);

        yCount++;
        top3 = new JLabel("3.  Laurens : 34545");
        top3.setFont(gameFont);
        top3.setForeground(Color.decode("#E99A07"));
        top3.setBounds((marginX+buttonWidth+60)*xCount,(marginY+buttonHeight)*yCount,buttonWidth+100, buttonHeight);
        this.add(top3);


        yCount ++;
        JLabel nextPentomino = new JLabel("Next Pentomino:");
        nextPentomino.setFont(gameFont);
        nextPentomino.setBounds((marginX+buttonWidth+50)*xCount,(marginY+buttonHeight)*yCount,buttonWidth+100, buttonHeight);
        nextPentomino.setForeground(Color.white);

        this.add(nextPentomino);


        yCount += 5;
        JLabel controls = new JLabel("Controls:");
        controls.setFont(gameFont);
        controls.setBounds((marginX+buttonWidth+50)*xCount,(marginY+buttonHeight)*yCount,buttonWidth, buttonHeight);
        controls.setForeground(Color.white);

        this.add(controls);


        JLabel picLabel = new JLabel(new ImageIcon(getClass().getResource("assets/controls.png")));
        picLabel.setBounds((marginX+buttonWidth+5)*xCount,(marginY+buttonHeight)*yCount,434, 244);
        this.add(picLabel);


        changeName.addActionListener(e -> changeNamePressed());
        newGame.addActionListener(e -> newGamePressed());
        pauseGame.addActionListener(e -> pauseGamePressed());
        botSwitch.addActionListener(e -> botSwitchPressed());
        optimalOrder.addActionListener(e -> optimalOrderPressed());
    }

    private void changeNamePressed() {
        TetrisGame.board.setPlayersName(JOptionPane.showInputDialog("Your Name:"));
    }

    private void newGamePressed() {
        TetrisGame.newGame();
    }
    private void loadBackgroundMusic(String fileName) {
    try {
        InputStream is = getClass().getResourceAsStream(fileName);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        backgroundMusic = AudioSystem.getClip();
        backgroundMusic.open(audioInputStream);
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
    }
}

    private void pauseGamePressed() {
        TetrisGame.pauseGame();

        if(TetrisGame.timer != null)
            pauseGame.setText(TetrisGame.timer.isRunning() ? "Pause game" : "Resume");
    }

    private void botSwitchPressed() {
        if(TetrisGame.isBotActive())
            TetrisGame.setBotActive(false);
        else
            TetrisGame.setBotActive(true);
        botSwitch.setText(TetrisGame.isBotActive() ? "Bot: ON" : "Bot: OFF");
    }

    private void optimalOrderPressed() {
        if(TetrisGame.isOptimalOrderActive())
            TetrisGame.setOptimalOrder(false);
        else
            TetrisGame.setOptimalOrder(true);
        optimalOrder.setText(TetrisGame.isOptimalOrderActive() ? "OptOr: ON" : "OptOr: OFF");

        System.out.println("optimal order");
    }

    public void updateScoreboard() {
        ArrayList<String> topScores = ReadScores.readScores();

        top1.setText(topScores.get(0));
        top2.setText(topScores.get(1));
        top3.setText(topScores.get(2));
    }

    /**
     * This function is called BY THE SYSTEM if required for a new frame, uses the state stored by the nl.group37.pentominotetris.gui.UI class.
     */
    public void paintComponent(Graphics g)
    {
        Graphics2D localGraphics2D = (Graphics2D) g;

        localGraphics2D.setColor(Color.WHITE);
        localGraphics2D.fill(getVisibleRect());

       g.drawImage(background.getImage(), 0, 0, null);

        //draw lines
        localGraphics2D.setColor(Color.GRAY);
        for (int i = 0; i <= state.length; i++)
        {
            localGraphics2D.drawLine((i * size)+offsetX, offsetY, (i * size)+offsetX, (state[0].length * size)+offsetY);
        }
        for (int i = 0; i <= state[0].length; i++)
        {
            localGraphics2D.drawLine(offsetX, (i * size)+offsetY, (state.length * size)+offsetX, (i * size)+offsetY);
        }

        //draw blocks
        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[0].length; j++)
            {
                localGraphics2D.setColor(GetColorOfID(state[i][j]));
                localGraphics2D.fill(new Rectangle2D.Double((i * size + 1)+offsetX, (j * size + 1)+offsetY, size - 1, size - 1));
            }
        }


        //draw lines for next block
        localGraphics2D.setColor(Color.GRAY);
        for (int i = 0; i <= nextBlock.length; i++)
        {
            localGraphics2D.drawLine((i * size)+offsetNextX, offsetNextY, (i * size)+offsetNextX, (nextBlock[0].length * size)+offsetNextY);
        }
        for (int i = 0; i <= nextBlock[0].length; i++)
        {
            localGraphics2D.drawLine(offsetNextX, (i * size)+offsetNextY, (nextBlock.length * size)+offsetNextX, (i * size)+offsetNextY);
        }

        //draw blocks for next block
        for (int i = 0; i < nextBlock.length; i++)
        {
            for (int j = 0; j < nextBlock[0].length; j++)
            {
                localGraphics2D.setColor(GetColorOfID(nextBlock[i][j]));
                localGraphics2D.fill(new Rectangle2D.Double((i * size + 1)+offsetNextX, (j * size + 1)+offsetNextY, size - 1, size - 1));
            }
        }
    }

    /**
     * Decodes the ID of a pentomino into a color
     * @param i ID of the pentomino to be colored
     * @return the color to represent the pentomino. It uses the class Color (more in ICS2 course in Period 2)
     */
    private Color GetColorOfID(int i)
    {
        if(i==0) {return Color.BLUE;}
        else if(i==1) {return Color.ORANGE;}
        else if(i==2) {return Color.CYAN;}
        else if(i==3) {return Color.GREEN;}
        else if(i==4) {return Color.MAGENTA;}
        else if(i==5) {return Color.PINK;}
        else if(i==6) {return Color.RED;}
        else if(i==7) {return Color.YELLOW;}
        else if(i==8) {return new Color(0, 0, 0);}
        else if(i==9) {return new Color(0, 0, 100);}
        else if(i==10) {return new Color(100, 0,0);}
        else if(i==11) {return new Color(0, 100, 0);}
        else {return Color.LIGHT_GRAY;}
    }

    /**
     * This function should be called to update the displayed state (makes a copy)
     * @param _state information about the new state of the GUI
     */
    public void setState(int[][] _state)
    {
        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[i].length; j++)
            {
                state[i][j] = _state[i][j];
            }
        }

        //Tells the system a frame update is required
        repaint();
    }

    /**
     * This function should be called to update the next block
     */

    public void setNextBlock(int[][] _nextBlock)
    {
        for (int i = 0; i < _nextBlock.length; i++)
        {
            for (int j = 0; j < _nextBlock[i].length; j++)
            {
                nextBlock[i][j] = _nextBlock[i][j];
            }
        }
        //Tells the system a frame update is required
        repaint();
    }



    public void setPlayersName(String value) {
        playersName.setText(value);
    }

    public void setGameOver(String value) {
        gameOver.setText(value);
    }


    public String getPlayersName() {
        return playersName.getText();
    }

    public void setTime(String value) {
        time.setText("Time: " + value);
    }

    public static String getTime() {
        return time.getText();
    }

    public void setScore(String value) {
        score.setText("Score: " + value);
    }

    public String getScore() {
        return score.getText();
    }

    public void setLevel(String value) {
        level.setText("Level: " + value);
    }

    public String getLevel() {
        return level.getText();
    }

    public void setHighscore(String value) {
        highscore.setText("Highscore: " + value);
    }

    public String getHighscore() {
        return highscore.getText();
    }
}
