import java.util.ArrayList;

public class BruteForce {
    public static final int horizontalGridSize = 5;
    public static final int verticalGridSize = 6;
    
    public static  char[] input = {};
    
    //Static UI class to display the board
    public static UI ui;
    public static Long startTime = 0L;
    public static void main(String[] args) {
        search();
    }

    public static void search() {
        int[][] field = new int[horizontalGridSize][verticalGridSize];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = -1;
            }
        }
        startTime = System.currentTimeMillis();
        bruteforce(field);
        System.out.println("Done running");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
    }

    /**
     * The basic idea is that it get the possible placings for all the pieces and their mutations and then tries them out one by one. The possible
     * placing is saved as an array where the first int is the pentID of the piece, the 2nd is the mutation and the 3th and 4th are the x and y coords
     * where the block will be placed. It still can be pruned a lot since a lot of blocks cannot be placed in a lot of spots in the first place, so
     * they can be pruned from the main ArrayList that holds all the possible placings.
     * @param field
     */
    public static void bruteforce(int[][] field) {
        //AllPieces holds an arraylist for every piece in input
        ArrayList<ArrayList<int[]>> allPieces = new ArrayList<>();
        //i = pentID piece
        for (int i = 0; i < input.length; i++) {
            ArrayList<int[]> piece = new ArrayList<>();
            char c = input[i];
            //j = mutation number
            for (int j = 0; j < HelperMethods.getMutations(c); j++) {
                //j2 = x coord space to place block
                for (int j2 = 0; j2 < field.length; j2++) {
                    //k = y coord space to place block
                    for (int k = 0; k < field[0].length; k++) {
                        //Here i gets translated to the corresponding pentID
                        int pentID = HelperMethods.characterToID(c);
                        //Here it creates the possible placing of piece pentID with mutation j and at coords (j2, k)
                        int[] a = {pentID, j, j2, k};
                        //then it gets added to piece, which holds all the possible placings of a single piece
                        piece.add(a);
                    }
                }
            }
            //when piece is completely filled with all possible placings of a piece, it is added to allPieces
            allPieces.add(piece);
        }

        //allPieces can be pruned here later by checking which placings already are out of bounds and removing those from the list

        //Here we want to go over all the possible combinations of pieces, versions saves the specific placing we are at for each piece
        int[] versions = new int[input.length];
        boolean solved = false;
        //when all the blocks fit the puzzle is solved and the algorithm will stop
        while (!solved) {
            //the field will be emptied again
            for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					field[i][j] = -1;
				}
			}

            //For every piece the current version will be added to the field if possible
            for (int i = 0; i < input.length; i++) {
                int[] single = allPieces.get(i).get(versions[i]);
                addToField(field, single[0], single[1], single[2], single[3]);
            }

            //it there are no empty spaces in the field, it means it is full and the puzzle is complete
            if (HelperMethods.emptySpaces(field) == 0) {
                solved = true;
                //prints the solution to the UI
                ui.setState(field); 
            }
            //If the tried positions are not correct we move on the the next ones
            else {
                //the version of the last piece in input gets updated intil it has no more version left
                if (versions[versions.length-1] < allPieces.get(versions.length-1).size()) {
                    versions[versions.length-1]++;
                }
                //Every time all versions of a piece are tried, it will be reset to 0 and the version of the next piece will be increased by 1
                for (int i = 0; i < versions.length-1; i++) {
                    if (versions[i+1] >= allPieces.get(i+1).size()-1) {
                        versions[i+1] = 0;
                        versions[i]++;
                    }
                }
            }
        }
    }

    /**
     * Here it checks if a piece can be added to the field by checking if there are any overlappings going on
     * @param field
     * @param pentID
     * @param mutation
     * @param x
     * @param y
     */
    private static void addToField(int[][] field, int pentID, int mutation, int x, int y) {
        if(!Overlap.isOverlapping(field, pentID, mutation, x, y)) {
            int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
            HelperMethods.addPiece(field, pieceToPlace, pentID, x, y);
        }
    }
}
