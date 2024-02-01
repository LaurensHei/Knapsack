package nl.group37.algorithm.pent3d;

public class Parcel {
    private final char type;
    private int[][][] matrix;

    public Parcel(char type, int height, int width, int depth) {
        this.type = type;
        this.matrix = new int[height][width][depth];
    }

    public char getType() {
        return type;
    }

    public int[][][] getMatrix() {
        return matrix;
    }
}
