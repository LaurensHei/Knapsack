package pentomino;

import helpermethods.HelperMethods;
import helpermethods.Overlap;

public class Pentomino {

    char character;
    int pentID;
    Board board;
    int[][] blocks; //pentomino shape
    int x; //x coordinate of pentomino on the field
    int y; //y coordinate of pentomino on the field
    int mutation;
    PentominoRotation rotation;


    public Pentomino (char character, Board board) {
        this.character = character;
        this.pentID = HelperMethods.characterToID(character);
        this.board = board;
        this.x = 0;
        this.y = 0;
        this.mutation = 0;
        this.rotation = new PentominoRotation();
        rotation.setCurrentBlock(pentID);

        this.blocks = PentominoDatabase.data[this.pentID] [this.mutation];
    }

    /**
     * checks if pentomino is out of field or hits another block
     * Important: This method should be called between changing the pentomino's position and
     * to actual updating the nl.group37.pentominotetris.gui.UI. So if the method returns true, the position of the pentomino
     * should either be set to the previous position or should be placed on the field (in case
     * of a collision with another block)
     * @return true if out of field or hits another block
     */
    public boolean checkCollision() {
        if((this.y + blocks[0].length) > board.getField()[0].length || (x+blocks.length) > board.getField().length || this.x < 0 || this.y <0 
            || Overlap.isOverlapping(board.getField(), pentID, mutation, x, y)) {
            return true;
        }
        return false;
    }

    public boolean checkFinalCollision() {
        //case ground
        if((this.y + blocks[0].length) > (board.getField()[0].length) || Overlap.isOverlapping(board.getField(), pentID, mutation, x, y)) {
            board.placePentomino();
            return true;
        }

        return false;
    }

    public boolean checkBlockCollision(int oldX, int oldY) {
        //case other block
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if(board.getField()[x+i][y+j] != -1) {

                    System.out.println("Not empty at x: " + (x+i) + " y: " + (y+j));
                }
            }
        }
        return false;
    }


    /*

    if(oldX+i > board.getField().length || oldY+y > board.getField()[0].length)
                    continue;
                if (board.getField()[oldX+i][oldY+j] != -1)
                    continue;
                System.out.println("c");
                if((board.getField()[x+i][y+j] != -1) && (blocks[i][j] == 1)) {

                    System.out.println("block collision");
                    return true;
                }
     */

    public static boolean isOverlapping(int[][] field, int pentID, int mutation, int x, int y) {
        int[][] pentomino = PentominoDatabase.data[pentID][mutation];

        //if((x+pentomino[0].length) > field.length || (y+pentomino.length) > field[0].length)
        //	return true;
        if((y+pentomino[0].length) > field[0].length || (x+pentomino.length) > field.length)
            return true;

        for (int i = 0; i < pentomino.length; i++) {
            for (int j = 0; j < pentomino[i].length; j++) {
                //if((x+i) > pentomino[i].length || (y+j) > pentomino.length)
                //	return true;

                if(field[x+i][y+j] != -1 && pentomino[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveDown() {
        this.y ++;
        //checkBlockCollision(this.x, this.y-1);
        if(!checkFinalCollision())
            return true;
        this.y --;
        return false;
    }

    public boolean moveLeft() {
        this.x --;
        if(!checkCollision())
            return true;
        this.x ++;
        return false;
    }

    public boolean moveRight() {
        this.x ++;
        if(!checkCollision())
            return true;
        this.x --;
        return false;
    }

    public boolean hardDrop() {
        while(!checkCollision()) {
            this.y ++;
        }
        this.y --;
        return false;
    }

    public boolean rotateLeft() {
        int oldm = mutation;
        rotation.rotateBlockL();
        mutation = rotation.getCurrentstate();
        int[][]newBlocks = PentominoDatabase.data[pentID][mutation];
        if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
            blocks = newBlocks;
            return true;
        }
        int ogX=x;
        int ogY=y;
        x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x = ogX;
    y = ogY;
    mutation = oldm;
    blocks = PentominoDatabase.data[pentID][mutation];
    return false;

    }


    public boolean rotateRight() {
        int oldm = mutation;
        rotation.rotateBlockR();
        mutation = rotation.getCurrentstate();
        int[][]newBlocks = PentominoDatabase.data[pentID][mutation];
        if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
            blocks = newBlocks;
            return true;
        }
        int ogX=x;
        int ogY=y;
        x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x--;
    if (!checkRotationCollision(newBlocks) && !checkRotationOutOfBounds(newBlocks)) {
        blocks = newBlocks;
        return true;
    }
    x = ogX;
    y = ogY;
    mutation = oldm;
    blocks = PentominoDatabase.data[pentID][mutation];
    return false;
    }
    public boolean checkRotationOutOfBounds(int[][]blocks){
        int maxX=x+blocks.length-1;
        int maxY=y+blocks[0].length-1;
        return maxX>=board.getField().length||maxY>=board.getField()[0].length;
    }
    private boolean checkRotationCollision(int[][] newBlocks) {
        for (int i = 0; i < newBlocks.length; i++) {
            for (int j = 0; j < newBlocks[i].length; j++) {
                if (newBlocks[i][j] == 1) {
                    int newX = x + i;
                    int newY = y + j;
                    if (newX < 0 || newY < 0 || newX >= board.getField().length || newY >= board.getField()[0].length
                            || board.getField()[newX][newY] != -1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getMutation() {
        return this.mutation;
    }

    public int[][] getBlocks() {
        return this.blocks;
    }

    public int[][] getBlocks(int mutation) {
        return PentominoDatabase.data[this.pentID] [mutation];
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getPentID() {
        return pentID;
    }

    public void setPentID(int pentID) {
        this.pentID = pentID;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMutation(int mutation) {
        this.mutation = mutation;
        this.blocks = PentominoDatabase.data[this.pentID] [mutation];
    }

    

    
}
