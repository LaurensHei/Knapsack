/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

/**
 * This class includes the methods to support the search of a solution.
 */
public class BranchSearching
{
    public static final int horizontalGridSize = 12;
    public static final int verticalGridSize = 5;
    
	public static char[] input = {};

	public static boolean solved = false;

    //Static UI class to display the board
    public static UI ui;
	public static Long startTime = 0L;
	

	/**
	 * Helper function which starts a basic search algorithm
	 */
    public static void search()
    {
        // Initialize an empty board
        int[][] field = new int[horizontalGridSize][verticalGridSize];
		//int[][] field = new int[verticalGridSize][horizontalGridSize];

        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
                // -1 in the state matrix corresponds to empty square
                // Any positive number identifies the ID of the pentomino
            	field[i][j] = -1;
            }
        }
		startTime = System.currentTimeMillis();
		branchSearch(field, new boolean[input.length]);
	}


	/**
	 * Branch search algorithms creates a branch of every possible combination of pentominoes on the field. Then it
	 * goes through all the possible combinations. Meanwhile, it prunes useless paths.
	 * @param field pentomino field
	 * @param used list of pentominoes which are already used
	 */
	public static void branchSearch(int[][] field, boolean[] used){
		//System.out.println(Arrays.toString(used));

		//loop through every block possible
		for(int blockId = 0; blockId < input.length; blockId++) {
			//skip used blocks
			if(used[blockId])
				continue;

			//get pentId of blockId
			int pentID = HelperMethods.characterToID(input[blockId]);

			//loop through every mutation possible for blockId
			for(int mutation = 0; mutation < HelperMethods.getMutations(input[blockId]); mutation++) {

				//declare it here because of performance?
				int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];


				//loop every y
				for(int y = 0; y <= field[0].length - pieceToPlace[0].length; y++) {

					//loop every x
					for(int x = 0; x <= field.length - pieceToPlace.length; x++) {

						//check if block can placed here
						if(Overlap.isOverlapping(field, pentID, mutation, x, y))
							continue;

						//place block
						HelperMethods.addPiece(field, pieceToPlace, pentID, x, y);

						//mark block as used
						used[blockId] = true;
						//update the ui
						//if(!solved)
						//	ui.setState(field);


						//check if there are unused blocks left
						boolean unusedBlocks = false;
						for(int i = used.length-1; i > 0; i--) {
							if(!used[i]) {
								unusedBlocks = true;
								break;
							}
						}


						if(!unusedBlocks) {
							System.out.println("solved!");
							solved = true;
							ui.setState(field);
							HelperMethods.printMatrix(field);
							return;
						}
						if(!isUseless(field, used)) {
							branchSearch(field, HelperMethods.copyArr(used));
						}
						used[blockId] = false;
						HelperMethods.removePiece(field, pieceToPlace, pentID, x, y);
					}
				}
			}
		}
	}

	/**
	 * Prunes the branchSearch. The pentomino field is useless when there are bordered free spaces for which
	 * the size % 5 != 0, a bordered free space has a shape of a pentomino which is already used or if two free spaces
	 * with the size of 5 have the same shape
	 * @param field
	 * @param used
	 * @return
	 */
	public static boolean isUseless(int [][] field, boolean[] used) {

		boolean[][] freeSpace = new boolean[field.length][field[0].length];
		boolean[] usedPieces = HelperMethods.copyArr(used);

		for(int i = 0; i < field.length; i ++) {
			for(int j = 0; j < field[0].length; j++) {
				if(field[i][j] == -1) {

					//free space
					//here we need to check if there are 5 more spaces around
					if(!hasAtLeastFiveNegativeOnes(field, i, j))
						return true;
					//--new solution

					boolean[][] saveField = new boolean[field.length][field[0].length];
					int negatives = countConnectedNegatives(field, i, j, saveField, 0);

					//if free space is not dividable by 5 or smaller than 5, it can't be filled with pentominoes
					if(negatives % 5 != 0 || negatives < 5)
						return true;

					if(negatives == 5) {
						if(!compareMatrix(saveField, freeSpace)) {
							//check which piece can fit
							int piece = whichPieceCanFit(saveField);
							if(piece != -1) {
								//check if piece is already used
								if(usedPieces[piece]) {
									//if so, return true
									return true;
								}
								usedPieces[piece] = true;

								freeSpace = HelperMethods.copyArr(saveField);
							}
						}
					}
				}
			}
		}
        return  false;
    }

	/**
	 * This method tries to fit a pentomino in a restricted area of 5 blocks. The purpose is to check, if a free space
	 * of 5 blocks isn't in the shape of a used block.
	 * @param field pentomino field
	 * @return returns the blockId of the block which can fit in the restricted area.
	 */
	public static int whichPieceCanFit(boolean[][] field) {
		//first, convert boolean[][] field to int[][] field, with true values (free spaces) = -1 and everything else 100
		int[][] testField = new int[field.length][field[0].length];
		testField = convertBooleanMatrixToIntMatrix(field, testField, -1, 100);
		for(int blockId = 0; blockId < input.length; blockId ++) {
			if (tryToFit(testField, blockId))
				return blockId;
		}

		return -1;
	}

	/**
	 * This method is a helper method of the whichPieceCanFit() method. It's similar to the branchSearch() method.
	 * It tries to fit in a single pentomino in one free space with a size of 5 blocks.
	 * @param testField modified version of the pentomino field where all spaces are used except for a free space of 5
	 * blocks
	 * @param blockId blockId of the piece which we try to fit in the space
	 * @return returns true if the piece can fit in the space.
	 */
	private static boolean tryToFit(int[][] testField, int blockId) {
		int pentID = HelperMethods.characterToID(input[blockId]);
		//loop through every mutation possible for blockId
		for(int mutation = 0; mutation < HelperMethods.getMutations(input[blockId]); mutation++) {
			int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
			//loop every y
			for(int y = 0; y <= testField[0].length - pieceToPlace[0].length; y++) {
				//loop every x
				for(int x = 0; x <= testField.length - pieceToPlace.length; x++) {
					//check if block can placed here
					if (!Overlap.isOverlapping(testField, pentID, mutation, x, y)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * This is a helper method for the whichPieceCanFit() method. It converts a boolean[][] to an int[][]
	 * @param bMatrix reference to a boolean[][] we want to convert
	 * @param iMatrix reference to an int[][] we want to overwrite
	 * @param trueValue value in iMatrix at every position where boolean[][] is true
	 * @param falseValue value in iMatrix at every position where boolean[][] is false
	 * @return returns the iMatrix
	 */
	public static int[][] convertBooleanMatrixToIntMatrix(boolean[][] bMatrix, int[][] iMatrix, int trueValue, int falseValue) {
		for(int i = 0; i < bMatrix.length; i++ ) {
			for(int j = 0; j < bMatrix[0].length; j++ ) {
				if(bMatrix[i][j])
					iMatrix[i][j] = trueValue;
				else
					iMatrix[i][j] = falseValue;
			}
		}
		return iMatrix;
	}


	/**
	 * checks if two matrixes have the same values at every position
 	 * @param matrix1 first matrix
	 * @param matrix2 second matrix
	 * @return returns true when matrix1 and matrix2 have identical values
	 */
	public static boolean compareMatrix(boolean[][] matrix1, boolean[][] matrix2) {
		for(int i = 0; i < matrix1.length; i ++) {
			for (int j = 0; j < matrix1[0].length; j ++) {
				if(matrix1[i][j] != matrix2[i][j])
					return false;
			}
		}

		return true;
	}


	/**
	 * Checks if free space in which a field is bordered is dividable by 5 (that means, an even number of pentominoes
	 * can fit in there
	 * @param matrix pentomino field
	 * @param x x coordinate of field we want to check
	 * @param y y coordinate of field we want to check
	 * @return returns true when free space arount field x % 5 = 0 (5/10/15/20...)
	 */
	public static boolean hasAtLeastFiveNegativeOnes(int[][] matrix, int x, int y) {
		return countConnectedNegatives(matrix, x, y, new boolean[matrix.length][matrix[0].length], 0) % 5 == 0;
	}


	/**
	 * recursive method to count how many free fields exists around a field
	 * @param matrix pentomino field
	 * @param x x coordinate of block we want to check
	 * @param y y coordinate of block we want to check
	 * @param visited boolean array to not count two fields twice
	 * @param count counts free fields
	 * @return returns the number of free fields around a field
	 */
	private static int countConnectedNegatives(int[][] matrix, int x, int y, boolean[][] visited, int count) {
		if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length || visited[x][y] || matrix[x][y] != -1 ) {
			return count;
		}

		visited[x][y] = true;

		// Count connected negative ones in all four directions
		count++;

		count = countConnectedNegatives(matrix, x + 1, y, visited, count); // Right
		count = countConnectedNegatives(matrix, x - 1, y, visited, count); // Left
		count = countConnectedNegatives(matrix, x, y + 1, visited, count); // Down
		count = countConnectedNegatives(matrix, x, y - 1, visited, count); // Up

		return count;
	}

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
    public static void main(String[] args)
    {
        search();
    }
}