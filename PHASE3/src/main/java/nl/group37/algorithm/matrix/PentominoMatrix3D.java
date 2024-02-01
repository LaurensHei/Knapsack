package nl.group37.algorithm.matrix;

import nl.group37.algorithm.ParcelList;
import nl.group37.algorithm.pent3d.Pentomino;

import java.util.ArrayList;
import java.util.List;

public class PentominoMatrix3D {
    private int[][][] field;
    List<Pentomino> pentominoes;
    private Node root;
    private Node current;

    public PentominoMatrix3D(int height, int width, int depth) {
        //create root node, connect left and right to itself
        this.root = new Node();
        root.setRightNode(root);
        root.setLeftNode(root);
        //set current node to root
        this.current = root;

        //create the cargo container. index every point in the matrix
        this.field = new int[height][width][depth];
        fillField();
    }

    public void buildMatrix() {
        addHeaders();
        initPentominoes();
        addNodes();
    }
    private void addNodes() {
        //set our current pointer to the first column (starting from the left)
        current = root.getRightNode();

        //count rows added
        int c = 0;
        //loop through every parcel
        for (Pentomino pentomino : pentominoes) {
            int[][][] matrix = pentomino.getMatrix();

            int iMax = field.length - matrix.length;
            int jMax = field[0].length - matrix[0].length;
            int kMax = field[0][0].length - matrix[0][0].length;

            for (int i = 0; i <= iMax; i++) {
                for (int j = 0; j <= jMax; j++) {
                    for (int k = 0; k <= kMax; k++) {
                        c++;
                        int[] row = blockToCoordinates(matrix, i, j, k);
                        addRow(pentomino.getType(), row);
                    }
                }
            }
        }
        System.out.println(c + " rows added");
    }

    private void addRow(char identifier, int[] columns) {
        //int columnIndex = -1;
        Node next = root;

        Node first = new Node();
        Node c = new Node();
        while (next.getRightNode() != root) {
            next = next.getRightNode();
            //columnIndex++;

            //loop columns and check if next is equal to column
            for (int column : columns) {
                if (next.getHeader().getName().equals(Integer.toString(column))) {

                    //next is equal to a column in our list columns[]
                    Node n = new Node();

                    //check if first exist, if not, set first and current to n
                    if (first.rightNode == null) {
                        first = n;
                        c = n;
                    }

                    n.setHeader(next.getHeader());
                    n.header.size++;

                    n.type = identifier;

                    n.setRightNode(first);
                    n.setLeftNode(c);

                    c.setRightNode(n);
                    c = n;

                    //connect the last vertical element in column with new node
                    Node verticalBottom = next.getHeader().getUpNode();
                    verticalBottom.setDownNode(n);

                    n.setUpNode(verticalBottom);
                    n.setDownNode(next.getHeader());
                    next.getHeader().setUpNode(n);

                }
            }
        }
        first.setLeftNode(c);
    }

    private void addHeaders() {
        int amount = field.length * field[0].length * field[0][0].length;

        //loop through every column of our matrix
        for (int i = 1; i <= amount; i++) {
            Header columnHeader = new Header(String.valueOf(i));
            current.setRightNode(columnHeader);
            columnHeader.setLeftNode(current);
            columnHeader.setHeader(columnHeader);

            columnHeader.setUpNode(columnHeader);
            columnHeader.setDownNode(columnHeader);

            current = columnHeader;
        }
        current.setRightNode(root);
        root.setLeftNode(current);

        current = root;
    }

    private void initPentominoes() {
        this.pentominoes = new ArrayList<>();





        for (int i = 0; i < ParcelList.tPentominoes.length; i++) {
            this.pentominoes.add(new nl.group37.algorithm.pent3d.Pentomino('T',ParcelList.tPentominoes[i]));
        }
        for (int i = 0; i < ParcelList.pPentominoes.length; i++) {
            this.pentominoes.add(new Pentomino('P',ParcelList.pPentominoes[i]));
        }
        for (int i = 0; i < ParcelList.lPentominoes.length; i++) {
            this.pentominoes.add(new Pentomino('L',ParcelList.lPentominoes[i]));
        }


    }

    private int fillField() {
        //fill field
        int index = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    index++;
                    field[i][j][k] = index;
                }
            }
        }
        return index;
    }

    public int[] blockToCoordinates(int[][][] block, int offsetHeight, int offsetWidth, int offsetDepth) {
        int[] result = new int[block.length * block[0].length * block[0][0].length];

        int index = 0;
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                for (int k = 0; k < block[i][j].length; k++) {
                    if(block[i][j][k] != 1)
                        continue;
                    result[index] = field[offsetHeight + i][offsetWidth + j][offsetDepth + k];
                    //System.out.println(result[index]);
                    index++;
                }
            }
        }
        return result;
    }

    public Node getRoot() {
        return root;
    }


}
