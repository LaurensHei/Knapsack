package nl.group37.algorithm;

public class Blocks {

    public static int[][][] grid = new int[33][5][8];

    public static int[][][] block = new int[33][5][8];

    public static void addBlock(int xCoord, int yCoord, int zCoord, int[][][] block) {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                for (int k = 0; k < block[i][j].length; k++) {
                    if (block[i][j][k] == 0) {
                    } else
                        grid[xCoord + i][yCoord + j][zCoord + k] = block[i][j][k];
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
