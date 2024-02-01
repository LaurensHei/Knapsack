package pentomino;
import java.util.Arrays;

import helpermethods.Overlap;

public class Bot {
    //For debugging, can be removed later
    public static void main(String[] args) {
        //normal size field (transpose to get actual field)
        int[][] field = {   {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,1,1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};

        //Pentomino p = new Pentomino('Y', null);

        System.out.println(getHscore(field));
        printTranspose(field);

    }

    /**
     * Returns the optimal pentomino mutation and place based on the field and the pentomino given
     * @param field the field of the board
     * @param currentPentomino the pentomino to be placed
     * @return the pentomino with the best mutation and place
     */
    public Pentomino getPentomino(int[][] field, Pentomino currentPentomino) {
        //ask heuristicBot for the pentomino
        Pentomino c = heuristicBot(field, currentPentomino);
        return c;
    }

    /**
     * Calculates the score of every possible pentomino placement and returns the best pentomino
     * @param field the field of the board
     * @param currentPentomino the pentomino to be placed
     * @return the pentomino with the best mutation and place
     */
    private static Pentomino heuristicBot(int[][] field, Pentomino currentPentomino) {
        //starting score is -infinity to get the best even if it is below 0
        double maxScore = Double.NEGATIVE_INFINITY;
        int[] placeSet = new int[2];

        //Go over every possible mutation of the pentomino
        for (int rotation = 0; rotation < getRotations(currentPentomino.getCharacter()); rotation++) {
            int[][] blocks = currentPentomino.getBlocks(rotation);
            //Go over every possible x coord of the pentomino (based on the length of its blocks)
            for (int x = 0; x < 6-blocks.length; x++) {

                //adds the piece to the field and returns the number of blocks it has fallen
                int fall = addPiece(field, currentPentomino, x, rotation);

                //If a block cannot be placed because it goes over the top border, the block is skipped
                if (fall == -1) {
                    continue;
                }

                //gets the score of this piece placement
                double score = 0;
                
                if (currentPentomino.getCharacter() == 'L') {
                    score = getScore(field, true);
                }
                else {
                    score = getScore(field, false);
                }

                //if the score is higher than the current maximum score the maximum score will be replaced and the best x and rotation values updated
                if (score > maxScore) {
                    maxScore = score;
                    placeSet[0] = rotation;
                    placeSet[1] = x;

                }

                //removes the piece from the field (using fall) to clean it up for the next pentomino placement
                removePiece(field, currentPentomino, x, rotation, fall);
            }
        }
        //changes the mutation and x of the current pentomino
        currentPentomino.setMutation(placeSet[0]);
        currentPentomino.setX(placeSet[1]);
        
        return currentPentomino;
    }

    /**
     * calculates the score the current field
     * @param field the field of the board
     * @return the score of this field
     */
    private static double getScore(int[][] field, boolean bool) {
        //Total height of the field
        int THscore = getTHscores(field);
        //System.out.println("height: " + THscore);

        //Completed lines of the field
        int CLscore = getCLscore(field);
        //System.out.println("cleared lines: " + CLscore);

        //Holes in the field
        int Hscore = getHscore(field);
        //System.out.println("holes: " + Hscore);

        //Bumpiness of the field
        int Bscore = getBscore(field);
        //System.out.println("bumpiness: " + Bscore);

        //Sum
        //return ((-0.510066*AHscore) + (0.760666*CLscore) + (-0.35663*Hscore) + (-0.184483*Bscore));
  
        if (bool) {
            return (-0.5*THscore + 0.8*CLscore - 0.4*Hscore - 0.0*Bscore);
        } else {
            return (-0.5*THscore + 0.8*CLscore - 0.4*Hscore - 0.2*Bscore);
        }
        
    }

    /**
     * calculates the total height of the field
     * @param field the field of the board
     * @return total height
     */
    private static int getTHscores(int[][] field) {
        int THscore = 0;
        int[] THscores = new int[field.length];
        
        //goes over every column
        for (int i = 0; i < field.length; i++){
            int maxHeight = field[0].length;
            
            //goes from bottom to top for every column and finds the highest point that isn't empty
            for (int j = field[0].length -1; j >= 0; j--){
                if (field[i][j] != -1) {
                    maxHeight = j;
                }
            }
            THscores[i] = field[0].length - maxHeight;
        }

        //sums up all heights to get the total height score
        for (int i = 0; i < THscores.length; i++){
            THscore += THscores[i];
        }
        return THscore;
    }

    /**
     * calculated to total amount of full lines in the field
     * @param field the field of the board
     * @return total amount of completed lines
     */
    private static int getCLscore(int[][] field) {
        int CLscore = 0; 
        
        //goes over all the rows of the field
        for(int i = 0; i < field[0].length; i++){
            int count = 0;

            //checks for every block in the row if it is non-empty
            for(int j = 0; j < field.length; j++){
                if (field[j][i] != -1){
                    count ++;
                }
            }

            //if all blocks are non-empty the line is complete and the score increases with 1
            if (count >= field.length){
                CLscore ++;
            }
        }
        return CLscore;
    }

    /**
     * calculates the amount of holes in the field
     * @param field the field of the board
     * @return total amount of holes
     */
    private static int getHscore(int[][] field) {
        int Hscore = 0;
        //get every x coordinate
        for (int i = 0; i < field.length; i++){
            //get every y coordinate
            for (int j = 1; j < field[0].length; j++){
                //check if this block is empty and has a non-empty block above it
                if(field[i][j] == -1 && (j==0 || field[i][j-1] != -1)) {
                    //if this is the case the holes score increases with one
                    Hscore++;
                }
                /* code in case our bot can move a pentomino after the first movement
                //check if this block is empty and has a non-empty block left of it
                if (field[i][j] == -1 && (i == 0 || field[i-1][j] != -1)) {
                    //and right it
                    if (i == field.length-1 || field[i+1][j] != -1) {
                        //and above it
                        if (j == 0 || field[i][j-1] != -1) {
                            //and below it
                            if (j == field[0].length-1 || field[i][j+1] != -1) {
                                //when that is the case, this space is a hole
                                Hscore ++;
                            }
                        }
                    }
                }
                */
            }
        }
        return Hscore;
    }

    /**
     * calculates the bumpiness of the field (the height difference between columns)
     * @param field the field of the board
     * @return the bumpiness aka total height difference
     */
    private static int getBscore(int[][] field) {
        int Bscore = 0;
        int[] Bscores = new int[field.length];
        int previousScore = 0;

        //goes over every column (same code as total height)
        for (int i = 0; i < field.length; i++){
            int maxHeight = field[0].length;

            //goes from bottom to top for every column and finds the highest point that isn't empty (same code as total height)
            for (int j = field[0].length -1; j >= 0; j--){
                if (field[i][j] != -1){
                    maxHeight = j;
                }
            }
            maxHeight = field[0].length - maxHeight;
            
            //sets the previous score of the first column to the same height as the column, since its against a wall and thus no bump
            if (i == 0) {
                previousScore = maxHeight;
            }

            //calculates bumpiness as the absolute difference between the current height (maxHeight) and the previous height (previousScore)
            Bscores[i] = Math.abs(maxHeight - previousScore);
            
            //sets the previousscore to the current height, to be the previous height of the next column
            previousScore = maxHeight;
        }

        //sums up all bumpiness scores
        for (int i = 0; i < Bscores.length; i++){
            Bscore += Bscores[i];
        }
        return Bscore;
    }

    /**
     * Returns the number of rotations that every pentomino piece has
     * @param character to the the number of rotations from
     * @return total number of rotations 
     */
    private static int getRotations(char character) {
		int rotation = 0;
		if (character == 'X') {
			rotation = 1;
		} else if (character == 'I') {
			rotation = 2;
		} else if (character == 'Z') {
			rotation = 2;
		} else if (character == 'T') {
			rotation = 4;
		} else if (character == 'U') {
			rotation = 4;
		} else if (character == 'V') {
			rotation = 4;
		} else if (character == 'W') {
			rotation = 4;
		} else if (character == 'Y') {
			rotation = 4;
		} else if (character == 'L') {
			rotation = 4;
		} else if (character == 'P') {
			rotation = 4;
		} else if (character == 'N') {
			rotation = 4;
		} else if (character == 'F') {
			rotation = 4;
		}
		return rotation;
	}

    /**
     * removes a given piece from a given field
     * @param field the field of the board
     * @param pentomino to be removed
     * @param x coordinate of the pentomino
     * @param mutation of the pentomino
     * @param fall amount of blocks the pentomino fell after adding it
     */
    private static void removePiece(int[][] field, Pentomino pentomino, int x, int mutation, int fall) {
        int pieceID = pentomino.getPentID();
        int[][] piece = PentominoDatabase.data[pieceID] [mutation];
        int y = pentomino.getY()+fall;

		for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
		{
			for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
			{
				if (piece[i][j] == 1)
				{
					// Add the ID of the pentomino to the board if the pentomino occupies this square
					field[x + i][y + j] = -1;
				}
			}
		}
	}

    /**
     * adds a given piece to a given field and drops it down 
     * @param field the field of the board
     * @param pentomino to be placed
     * @param x coordinate of the pentomino
     * @param mutation of the pentomino
     * @return the amount of blocks the added pentomino has fallen
     */
    private static int addPiece(int[][] field, Pentomino pentomino, int x, int mutation) {
        pentomino.setMutation(mutation);
        pentomino.setX(x);
        int fall = 0;
        
        //as long as the piece doesn't collide with anything it keeps falling
        while(!checkCollision(field, pentomino, fall)) {
            fall++;
        }
        fall--;

        //If a block cannot be placed because it goes over the top border, the block is skipped
        if (fall == -1) {
            return fall;
        }
        
        int pieceID = pentomino.getPentID();
        int[][] piece = PentominoDatabase.data[pieceID] [mutation];
        int y = pentomino.getY() + fall;

        //loops over x position of the pentomino
        for(int i = 0; i < piece.length; i++) {
            
            //loops over y position of pentomino
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    //adds the ID of the pentomino to the board if the pentomino occupies this square
                    field[x + i][y + j] = pieceID;
                }
            }
        }
        return fall;  
    }

    /**
     * checks if the possibly added block collides with anything
     * @param field the field of the board
     * @param p to be placed
     * @param fall amount of blocks the pentomino fell so far
     * @return whether the pentomino collides with anything or not
     */
    private static boolean checkCollision(int[][] field, Pentomino p, int fall) {
        //returns true when the pentomino goes over the bottom border
        if( (p.getY() + fall + p.getBlocks()[0].length) > field[0].length || 
            //the right side border
            (p.getX()+p.getBlocks().length) > field.length || 
            //the left side border
             p.getX() < 0 || 
             //the top border
             p.getY() <0 || 
             //or when the pentomino is overlapping with an already placed pentomino
             Overlap.isOverlapping(field, p.getPentID(), p.getMutation(), p.getX(), p.getY()+fall)) {
                return true;
        }
        //else the pentomino can safely be placed
        return false;
    }

    //For debugging, can be removed later
    private static void printTranspose(int[][] field) {
        int m = field.length;
        int n = field[0].length;

        int[][] transpose = new int[n][m];

        for(int x = 0; x < n; x++) {
            for(int y = 0; y < m; y++) {
                transpose[x][y] = field[y][x];
            }
        }

        for (int i = 0; i < transpose.length; i++) {
            System.out.println(Arrays.toString(transpose[i]));
        }
        System.out.println();
    }
}
