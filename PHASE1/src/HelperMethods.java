public class HelperMethods {
    /**
	 * Get as input the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
    public static int characterToID(char character) {
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

    public static int[][] copyArr(int[][] arr){
		int[][] tempArr=new int[arr.length][arr[0].length];
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

	public static boolean[][] copyArr(boolean[][] arr){
		boolean[][] tempArr=new boolean[arr.length][arr[0].length];
		for(int m=0;m<tempArr.length;m++){
			for(int n=0;n<tempArr[m].length;n++){
				tempArr[m][n]=arr[m][n];
			}
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

    /**
     * Counts all the empty spaces in a field
     * @param field
     * @return
     */
    public static int emptySpaces(int[][] field) {
        int empties = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == -1) {
                    empties++;
                }
            }
        }
        return empties;
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

	//returns an integer of the number of possible mutations for an element
	public static int getMutations(char character) {
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
}
