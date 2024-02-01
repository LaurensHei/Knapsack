package pentomino;

import javax.swing.*;

import gui.GameOverScreen;
import helpermethods.Overlap;
import main.TetrisGame;
import scores.RecordScores;

import java.util.Arrays;
import java.util.Random;

public class Board {
    public static final char[] input = { 'W', 'Y', 'I', 'T', 'Z', 'L', 'N', 'P', 'X', 'F', 'V', 'U'};
    public static final char[] optimalInput = {'L', 'Y', 'U', 'Z', 'T', 'F', 'X', 'N', 'I', 'P', 'W', 'V'};
    public static int i = 0;
    public Pentomino currentPentomino;
    public Pentomino nextPentomino;
    int pentX;
    int pentY;
    int mutation;
    int[][] field;
    int points;
    int level;
    boolean gameOver;
    String playersName;
    Bot bot;

    public Board(int width, int height) {
        this.field = new int[width][height];
        this.points = 0;
        this.level = 1;
        this.gameOver = false;
        this.playersName = "";
        TetrisGame.ui.setLevel(this.level+"");
        this.bot = new Bot();
    }

    public void removeFullRows() {
        //Iterates through every row
        for (int i = 0; i < field[0].length; i++){ 
            int count = 0;
            //Iterate through every coordinate of that row
            for (int j = 0; j < field.length; j++){
                if (field[j][i] != -1){
                    count++;
                }
            }
            //If all 5 coordinates of that row are occupied, we change that row to 0
            if (count >= 5){
                for(int j = 0; j < 5; j++){
                    field[j][i] = -1;
                }
                addPoint();
            }
        }

        //Iterates through every row 
        for (int i = field[0].length-1; i > 0; i--){
            //Creates a temporary array to store rows
            int[] tempRow = new int[5];
            //Iterates from row i upwards
            for(int m = i; m > 0; m--){
                int count = 0;
                for (int j = 0; j < field.length; j++){
                    if (field[j][i] == -1){
                        count++;
                    }
                }
                if (count < 5){
                    break;
                }
                //If that row is empty
                else{
                    //Iterates from row i upwards
                    for (int n = i; n > 0; n--){
                        //It copies the row above to tempRow and sets the current row to tempRow
                        for (int j = 0; j < field.length; j++){
                            tempRow[j] = field[j][n-1];
                            field[j][n] = tempRow[j];
                        }
                    }
                    //empties the row on top
                    for (int j = 0; j < field.length; j++){
                        field[j][0] = -1;
                    }
                }
            }
        }
    }

    public void setLevel(int value) {
        this.level = value;
        TetrisGame.ui.setLevel(this.level + "");
        TetrisGame.timer.increaseSpeed(this.level);
    }

    public void increaseLevel() {
        this.level ++;
        TetrisGame.ui.setLevel(this.level+"");
        TetrisGame.timer.increaseSpeed(1);
    }

    public void addPoint() {
        points++;
        TetrisGame.ui.setScore(points+"");

        if(points % 3 == 0) {
            increaseLevel();
        }
    }

    /**
     * Places the current pentomino at its current position to blocks[][]
     */
    public void placePentomino() {
        for (int i = 0; i < currentPentomino.getBlocks().length; i++) {
            for (int j = 0; j < currentPentomino.getBlocks()[0].length; j++) {
                if (currentPentomino.getBlocks()[i][j] != 0) {
                    field[pentX + i][pentY + j] = currentPentomino.pentID;
                }
            }
        }

        removeFullRows();

        //get new pentomino        
        if(TetrisGame.isOptimalOrderActive()) {
            newPentominoOptimal();
        } else if (TetrisGame.isBotActive()) {
            newPentomino();
            newPentominoBot();
        } else {
            newPentomino();      
        }
    }

    /**
     * Adds a new random pantomino to the grid
     */
    public void newPentomino() {
        Random r = new Random();

        //first pentomino of the game
        if(nextPentomino == null) {
            //generates 2 pentominoes, 1 current pentomino and 1 next pentomino
            int firstCharacter = r.nextInt(input.length);
            Pentomino firstPentomino = new Pentomino(input[firstCharacter], this);
            this.setCurrentPentomino(firstPentomino);

            int nextCharacter = r.nextInt(input.length);
            nextPentomino = new Pentomino(input[nextCharacter], this);
        } else  /*next pentomino exists*/{
            //set next pentomino to current and generate a new next
            this.setCurrentPentomino(nextPentomino);
            checkEndGame();

            int nextCharacter = r.nextInt(input.length);
            nextPentomino = new Pentomino(input[nextCharacter], this);
        }

        this.currentPentomino.moveRight();
        this.updateNextPentomino();
    }

    public void newPentominoBot() {
        removePentomino();

        try {
            setCurrentPentomino(bot.getPentomino(field, currentPentomino));
            addPentomino();
        } catch(ArrayIndexOutOfBoundsException e) {
            TetrisGame.setBotActive(false);
            TetrisGame.pauseGame();
            TetrisGame.pauseGame();
        }
    }

    public void newPentominoOptimal() {
        try {
            Pentomino testPentomino = new Pentomino(optimalInput[i], this);
            setCurrentPentomino(testPentomino);

            i++;
            removePentomino();
            setCurrentPentomino(bot.getPentomino(field, currentPentomino));
            addPentomino();

        } catch (Exception e) {
            TetrisGame.ui.setGameOver("OPTIMAL ORDER");
            endOptimalGame();
        }
    }


    public void setCurrentPentomino(Pentomino currentPentomino) {
        this.currentPentomino = currentPentomino;
        this.pentX = currentPentomino.getX();
        this.pentY = currentPentomino.getY();
        if (Overlap.isOverlapping(field, currentPentomino.pentID, currentPentomino.getMutation(), currentPentomino.getX(), currentPentomino.getY())){
            TetrisGame.ui.setGameOver("GAME OVER");
            TetrisGame.pauseGame();
        }
        this.mutation = currentPentomino.getMutation();
    }

    public void checkEndGame() {
        //System.out.println("Check end game");
        //boolean block = blockInFirstRow();
        //if(block) {
        //    endGame();
        //}
        if (Overlap.isOverlapping(field, currentPentomino.pentID, currentPentomino.getMutation(), currentPentomino.getX(), currentPentomino.getY())){
            endGame();
        }
    }

    public boolean blockInFirstRow () {
        for (int i = 0; i < field.length; i++) {
            if(field[0][i] != -1) {
                return true;
            }

        }
        return false;
    }

    public void endGame() {
        this.gameOver = true;
        TetrisGame.stopGame();
        TetrisGame.stopwatch.stop();

        if(playersName == null || playersName.equals("")) {
            setPlayersName(JOptionPane.showInputDialog("Type in a name"));
        }

        RecordScores.recordScore(this.playersName, points);
        TetrisGame.ui.updateScoreboard();

        new GameOverScreen();
    }

    public void endOptimalGame() {
        this.gameOver = true;
        TetrisGame.stopGame();
        TetrisGame.stopwatch.stop();
    }

    //remove old pentomino position on the field and adding the new one
    public void updatePentomino() {
        //remove old pentomino
        for(int i = 0; i < currentPentomino.getBlocks(this.mutation).length; i ++) {
            for(int j = 0; j < currentPentomino.getBlocks(this.mutation)[i].length; j ++) {
                if(currentPentomino.getBlocks(this.mutation)[i][j] == 1) {
                    this.field[this.pentX + i][this.pentY + j] = -1;
                    System.out.println("Update " + (this.pentX + i) + " " + (this.pentY + j));
                }
            }
        }

        //add new pentomino
        this.pentX = currentPentomino.getX();
        this.pentY = currentPentomino.getY();
        for(int i = 0; i < currentPentomino.getBlocks().length; i ++) {
            for(int j = 0; j < currentPentomino.getBlocks()[i].length; j ++) {
                if(currentPentomino.getBlocks()[i][j] == 1) {
                    this.field[this.pentX + i][this.pentY + j] = currentPentomino.pentID;
                }
            }
        }
    }

    public void updateNextPentomino() {
        int[][] blocks = new int[5][3];
        for (int[] row: blocks)
            Arrays.fill(row, -1);

        int[][] pentominoBlocks = PentominoDatabase.data[nextPentomino.pentID][0];
        for(int i = 0; i < pentominoBlocks.length; i ++) {
            for (int j = 0; j < pentominoBlocks[i].length; j ++) {
                if(pentominoBlocks[i][j] == 1) {
                    blocks[i][j] = nextPentomino.pentID;
                }
            }
        }

        TetrisGame.ui.setNextBlock(blocks);
    }

    public int[][] getField() {
        return this.field;
    }

    public  void clearBoard() {
        for(int i = 0; i < this.field.length; i++)
        {
            for(int j = 0; j < this.field[i].length; j++)
            {
                // -1 in the state matrix corresponds to empty square
                // Any positive number identifies the ID of the pentomino
                this.field[i][j] = -1;
            }
        }
        points = 0;
        TetrisGame.ui.setScore(points+"");
        TetrisGame.ui.updateScoreboard();
    }

    public void removePentomino() {
        for(int i = 0; i < currentPentomino.getBlocks().length; i ++) {
            for(int j = 0; j < currentPentomino.getBlocks()[i].length; j ++) {
                if(currentPentomino.getBlocks()[i][j] == 1) {
                    this.field[this.pentX + i][this.pentY + j] = -1;
                    //System.out.println("Remove " + (this.pentX + i)+" "+(this.pentY + j));
                }
            }
        }
    }

    public void addPentomino() {
        this.pentX = currentPentomino.getX();
        this.pentY = currentPentomino.getY();
        this.mutation = currentPentomino.getMutation();
        for(int i = 0; i < currentPentomino.getBlocks().length; i ++) {
            for(int j = 0; j < currentPentomino.getBlocks()[i].length; j ++) {
                if(currentPentomino.getBlocks()[i][j] == 1) {
                    this.field[this.pentX + i][this.pentY + j] = currentPentomino.pentID;
                }
            }
        }

    }


    public void setPlayersName(String value) {
        this.playersName = value;
        TetrisGame.ui.setPlayersName(this.playersName);
    }

    public Pentomino getCurrentPentomino() {
        return currentPentomino;
    }
}
