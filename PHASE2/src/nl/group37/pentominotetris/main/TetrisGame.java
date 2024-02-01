package main;
 /**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import java.awt.DefaultKeyboardFocusManager;

import javax.swing.JOptionPane;

import controls.Controls;
import gui.UI;
import pentomino.Board;
import timer.Stopwatch;
import timer.TetrisTimer;

/**
 * This class is the starting point of the tetris game
 */
public class TetrisGame
{
    public static final int horizontalGridSize = 5;
    public static final int verticalGridSize = 15;
    public static boolean botActive = false;
    public static boolean optimalOrderActive = false;

    public static UI ui = new UI(horizontalGridSize, verticalGridSize, 40);
    public static Board board;
    public static TetrisTimer timer;
    public static Stopwatch stopwatch;

    public static int score;

    /**
     * Main function of the tetris game
     */
    public static void main(String[] args) {
        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().
                addKeyEventPostProcessor(new Controls());

        initTetrisGame();
    }

    /**
     * Initialises the tetris game
     */
    public static void initTetrisGame() {
        // Initialize an empty board
        board = new Board(horizontalGridSize, verticalGridSize);
        board.clearBoard();
        ui.setState(board.getField());
    }

    /**
     * Starts a new round of tetris
     */
    public static void newGame() {
        Object[] options = {"1", "5", "10", "15", "20"};
        //...and passing `frame` instead of `null` as first parameter
        Object selectionObject = null;
        if (!optimalOrderActive && !botActive) {
            selectionObject = JOptionPane.showInputDialog(null, "Start Level", "Level",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        } else {
            selectionObject = 1;
        }

        TetrisGame.ui.setGameOver("");
        String selectionString;

        TetrisGame.ui.setGameOver("");

        if(timer != null)
            timer.stop();
        timer = new TetrisTimer(1000, 0.95);

        //clear field
        board.clearBoard();
        selectionString = selectionObject.toString();
        board.setLevel(Integer.parseInt(selectionString));

        //get new pentomino        
        if(optimalOrderActive) {
            board.newPentominoOptimal();
        } else if (botActive) {
            board.newPentomino();
            board.newPentominoBot();
        } else {
            board.newPentomino();      
        }

        //Update the nl.group37.pentominotetris.gui.UI
        ui.setState(board.getField());

        timer.start();

        if(stopwatch != null)
            stopwatch.stop();
        stopwatch = new Stopwatch();
        stopwatch.start();
    }

    /**
     * Adds a pentomino to the position on the field (overriding current board at that position)
     * @param field a matrix representing the board to be fulfilled with pentominoes
     * @param piece a matrix representing the pentomino to be placed in the board
     * @param pieceID ID of the relevant pentomino
     * @param x x position of the pentomino
     * @param y y position of the pentomino
     */
    public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y)
    {
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // Add the ID of the pentomino to the board if the pentomino occupies this square
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }

    /* can be removed?
    public static void setNextBlock(int pieceID) {
        int[][] nextBlockField = new int[5][3];
        for (int[] ints : nextBlockField) {
            Arrays.fill(ints, -1);
        }
        int[][] pieceToPlace = PentominoDatabase.data[pieceID][0];
        for(int i = 0; i < pieceToPlace.length; i ++) {
            for (int j = 0; j < pieceToPlace[i].length; j ++){
                if(pieceToPlace[i][j] == 1) {
                    nextBlockField[i][j] = pieceID;
                }
            }
        }
        ui.setNextBlock(nextBlockField);
    } */

    public static void pauseGame() {
        if(timer == null)
            return;
        if(timer.isRunning()) {
            timer.stop();
            stopwatch.stop();
        } else {
            timer.start();
            stopwatch.start();
        }
    }

    public static void stopGame() {
        if(timer == null)
            return;
        timer.stop();
    }

    public static boolean isBotActive() {
        return botActive;
    }

    public static void setBotActive( boolean value) {
        botActive = value;
    }

    public static boolean isOptimalOrderActive() { 
        return optimalOrderActive;
    }

    public static void setOptimalOrder (boolean value) {
        optimalOrderActive = value;
    }
}
