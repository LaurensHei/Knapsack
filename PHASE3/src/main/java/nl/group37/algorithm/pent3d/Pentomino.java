package nl.group37.algorithm.pent3d;

public class Pentomino {

    private final char type;
    private int[][][] matrix;

    public Pentomino(char type, int[][][] matrix) {
        this.type = type;
        this.matrix = matrix.clone();
    }

    public char getType() {
        return type;
    }

    public int[][][] getMatrix() {
        return matrix;
    }
}
