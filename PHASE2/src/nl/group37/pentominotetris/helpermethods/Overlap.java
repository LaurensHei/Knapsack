package helpermethods;

import pentomino.PentominoDatabase;

public class Overlap {


	/**
	 * This method detects, if two or more pentominoes overlap each other or if a pentomino is (partly) out of the field
	 * @param field pentomino field
	 * @param pentID pentomino id
	 * @param mutation mutation of pentomino (rotation / flip)
	 * @param x x coordinate of pentomino
	 * @param y y coordinate of pentomino
	 * @return true if pentomino is overlapping with other pentominoes or if pentomino is (partly) out of the field
	 */
    public static boolean isOverlapping(int[][] field, int pentID, int mutation, int x, int y) {
		//get pentomino blocks from database
		int[][] pentomino = PentominoDatabase.data[pentID][mutation];

		//checks if pentomino is not in the field
		if((y+pentomino[0].length) > field[0].length || (x+pentomino.length) > field.length)
			return true;

		//iterates through all parts of the pentomino and checks if at least one block of the pentomino is on a used space
		for (int i = 0; i < pentomino.length; i++) {
			for (int j = 0; j < pentomino[i].length; j++) {

				if(field[x+i][y+j] != -1 && pentomino[i][j] == 1) {
					return true;
				}
			}
		}
		return false;
	}
}
