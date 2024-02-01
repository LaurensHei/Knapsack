/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

/**
 * This class includes the methods to support the search of a solution.
 */
public class Search
{
    public static final int horizontalGridSize =12;
    public static final int verticalGridSize = 5;

	//TODO pruning

    //public static final char[] input = {'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F'};
	//XIZTUVWYLPNF
	public static char[] input = {};

	public static boolean solved = false;

    //Static UI class to display the board
    public static UI ui;

	/**
	 * Helper function which starts a basic search algorithm
	 */
    public static void search()
    {
		ui = new UI(horizontalGridSize, verticalGridSize, 50);
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
		solved = false;
		//long time = System.currentTimeMillis();
		branchSearch(field, new boolean[input.length]);
		//System.out.println(System.currentTimeMillis() - time + "ms time efford.");
		//branchTest(field);

	}
	
	/**
	 * Get as input the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
    private static int characterToID(char character) {
    	int pentID = -1; 
    	if (character == 'X') {
    		pentID = 0;
    	} else if (character == 'I') {
    		pentID = 1;
    	} else if (character == 'Z') {
    		pentID = 2;
    	} else if (character == 'T') {
    		pentID = 3;
    	} else if (character == 'U') {
    		pentID = 4;
     	} else if (character == 'V') {
     		pentID = 5;
     	} else if (character == 'W') {
     		pentID = 6;
     	} else if (character == 'Y') {
     		pentID = 7;
    	} else if (character == 'L') {
    		pentID = 8;
    	} else if (character == 'P') {
    		pentID = 9;
    	} else if (character == 'N') {
    		pentID = 10;
    	} else if (character == 'F') {
    		pentID = 11;
    	} 
    	return pentID;
    }


	//returns an integer of the number of possible mutations for an element
	private static int getMutations(char character) {
		int mutation = 0;
		if (character == 'X') {
			mutation = 1;
		} else if (character == 'I') {
			mutation = 2;
		} else if (character == 'Z') {
			mutation = 4;
		} else if (character == 'T') {
			mutation = 4;
		} else if (character == 'U') {
			mutation = 4;
		} else if (character == 'V') {
			mutation = 4;
		} else if (character == 'W') {
			mutation = 4;
		} else if (character == 'Y') {
			mutation = 8;
		} else if (character == 'L') {
			mutation = 8;
		} else if (character == 'P') {
			mutation = 8;
		} else if (character == 'N') {
			mutation = 8;
		} else if (character == 'F') {
			mutation = 8;
		}
		return mutation;
	}

	//branchSearch(field, 0, 0, 0, 0);

	//We need a function which consumes field[][], pentomino[][], x, y, rotation

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



	public static void branchTest(int[][] field) {
		int pentId = 1;
		int[][] pieceToPlace = PentominoDatabase.data[pentId][0];
		addPiece(field, pieceToPlace, pentId, 0, 3);
		pentId = 0;
		pieceToPlace = PentominoDatabase.data[pentId][0];
		addPiece(field, pieceToPlace, pentId, 1, 0);

		ui.setState(field);
		//System.out.println(isUseless(field));
	}


	public static void branchSearch(int[][] field, boolean[] used){
		//System.out.println(Arrays.toString(used));

		//loop through every block possible
		for(int blockId = 0; blockId < input.length; blockId++) {
			//skip used blocks
			if(used[blockId])
				continue;

			//get pentId of blockId
			int pentID = characterToID(input[blockId]);

			//loop through every mutation possible for blockId
			for(int mutation = 0; mutation < getMutations(input[blockId]); mutation++) {

				//declare it here because of performance?
				int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];


				//loop every y
				for(int y = 0; y <= field[0].length - pieceToPlace[0].length; y++) {

					//loop every x
					for(int x = 0; x <= field.length - pieceToPlace.length; x++) {

						//check if block can placed here
						if(isOverlapping(field, pentID, mutation, x, y))
							continue;

						//place block
						addPiece(field, pieceToPlace, pentID, x, y);

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
							printMatrix(field);
							return;
						}
						if(!isUseless(field, used)) {
							branchSearch(field, copyArr(used));
						}
						used[blockId] = false;
						removePiece(field, pieceToPlace, pentID, x, y);
					}
				}
			}
		}
	}

	public static int[][] copyArr(int[][] arr){
		int[][] tempArr=new int[arr.length][arr[0].length];
		for(int m=0;m<tempArr.length;m++){
			for(int n=0;n<tempArr[m].length;n++){
				tempArr[m][n]=arr[m][n];
			}
		}
		return tempArr;
	}

	public static boolean[][] copyArr(boolean[][] arr){
		boolean[][] tempArr=new boolean[arr.length][arr[0].length];
		for(int m=0;m<tempArr.length;m++){
			for(int n=0;n<tempArr[m].length;n++){
				tempArr[m][n]=arr[m][n];
			}
		}
		return tempArr;
	}

	public static boolean[] copyArr(boolean[] arr){
		boolean[] tempArr=new boolean[arr.length];
		for(int m=0;m<tempArr.length;m++){
			tempArr[m]=arr[m];
		}
		return tempArr;
	}

	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " "); // Print the element followed by a space
			}
			System.out.println(); // Move to the next line after printing a row
		}
	}

	public static boolean isUseless(int [][] field, boolean[] used) {

		boolean[][] freeSpace = new boolean[field.length][field[0].length];
		boolean[] usedPieces = copyArr(used);

		for(int i = 0; i < field.length; i ++) {
			for(int j = 0; j < field[0].length; j++) {
				if(field[i][j] == -1) {

					//TODO delete
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

								freeSpace = copyArr(saveField);
							}
						}
					}

				}
			}
		}
        return  false;
    }


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

	private static boolean tryToFit(int[][] testField, int blockId) {
		int pentID = characterToID(input[blockId]);
		//loop through every mutation possible for blockId
		for(int mutation = 0; mutation < getMutations(input[blockId]); mutation++) {
			int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
			//loop every y
			for(int y = 0; y <= testField[0].length - pieceToPlace[0].length; y++) {
				//loop every x
				for(int x = 0; x <= testField.length - pieceToPlace.length; x++) {
					//check if block can placed here
					if (!isOverlapping(testField, pentID, mutation, x, y)) {
						return true;
					}
				}
			}
		}
		return false;
	}

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


	//returns true when two boolean[][] are the same and false if there are different
	public static boolean compareMatrix(boolean[][] matrix1, boolean[][] matrix2) {
		for(int i = 0; i < matrix1.length; i ++) {
			for (int j = 0; j < matrix1[0].length; j ++) {
				if(matrix1[i][j] != matrix2[i][j])
					return false;
			}
		}

		return true;
	}

	public static boolean hasAtLeastFiveNegativeOnes(int[][] matrix, int x, int y) {
		return countConnectedNegatives(matrix, x, y, new boolean[matrix.length][matrix[0].length], 0) % 5 == 0;
	}

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

	/*
	private static boolean[][] saveConnectedNegatives(int[][] matrix, int x, int y, boolean[][] visited, int count) {
		if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length || visited[x][y] || matrix[x][y] != -1 || count > 5) {
			return visited;
		}

		visited[x][y] = true;

		// Count connected negative ones in all four directions
		count++;

		saveConnectedNegatives(matrix, x + 1, y, visited, count); // Right
		saveConnectedNegatives(matrix, x - 1, y, visited, count); // Left
		saveConnectedNegatives(matrix, x, y + 1, visited, count); // Down
		saveConnectedNegatives(matrix, x, y - 1, visited, count); // Up

		return visited;
	} */



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
		//System.out.println("piece added");
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

	public static void removePiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
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
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
    public static void main(String[] args)
    {
        search();
    }
}