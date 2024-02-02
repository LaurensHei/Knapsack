/**
 * This class had the purpose to build the matrix for the 2d pentomino problem.
 * The class is not used by the final program. It was created to fist try the
 * algorithm on the 2d pentomino problem.
 */


package nl.group37.algorithm.matrix;

import java.util.Arrays;

public class Matrix {
    private Node root;
    private Node current;
    private String[] names;

    public Matrix(String[] names) {
        this.root = new Node();
        root.leftNode = root;
        root.rightNode = root;
        this.current = root;
        this.names = names;
    }

    public void buildMatrix(int[][] matrix) {
        addHeaders(matrix);

        addNodes(matrix);
    }

    private void addNodes(int[][] matrix) {
        current = root.rightNode;

        //creating a vertical current node for each column
        Node[] vCurrentNodes = new Node[matrix[0].length];
        //fill the current nodes with the headers respectively as starting point
        for (int i = 0; i < vCurrentNodes.length; i++) {
            vCurrentNodes[i] = current;
            current = current.rightNode;
        }

        //set our current pointer to the first column (starting from the left)
        current = root.rightNode;

        //loop over matrix rows
        for (int i = 0; i < matrix.length; i++) {

            //we need a horizontal first node and a current node for each row.
            //every time we add a node to a row, we update the current node to that node. in the end, we can connect
            //the last node in a row to the first one to create the >circular< linked list for that row
            Node hCurrentNode = null;
            Node hFirstNode = null;

            //loop over matrix elements in a row
            for (int j = 0; j < matrix[i].length; j++) {

                //since we don't save the 0s, we only care about the 1s
                if (matrix[i][j] == 1) {

                    //create a new node and first connect the header pointer to the current columns header
                    Node n = new Node();
                    n.header = current.header;

                    //in the header element, increase the number of nodes in that column. we need that to find
                    //columns with the least amount of nodes for the algorithm
                    current.header.size++;

                    //if the hFirstNode is null, that means, it is the first node in this row. we set first node and c node
                    //to our node n.
                    if (hFirstNode == null) {
                        hFirstNode = n;
                        hCurrentNode = n;
                    }

                    //connect top and bottom
                    n.upNode = vCurrentNodes[j];
                    vCurrentNodes[j].downNode = n;
                    vCurrentNodes[j] = n;

                    // connect left and right
                    n.leftNode = hCurrentNode;
                    hCurrentNode.rightNode = n;
                    hCurrentNode = n;


                }
                //move our column pointer to the right to move on with the next column
                current = current.rightNode;
            }
            //connect first and current horizontal nodes to create the circular linked list
            hCurrentNode.rightNode = hFirstNode;
            hFirstNode.leftNode = hCurrentNode;

            //since we now go to the next row, we have to reset our column pointer to the beginning
            current = root.rightNode;
        }


        //after looping through all rows, we have to connect the nodes on the bottom of every column to
        //the header nodes and all the way around to create the circular linked list also vertically
        for (int i = 0; i < vCurrentNodes.length; i++) {
            vCurrentNodes[i].downNode = vCurrentNodes[i].header;
            vCurrentNodes[i].header.upNode = vCurrentNodes[i];
        }
    }

    private void addHeaders(int[][] matrix) {
        //loop through every column of our matrix
        for (int i = 0; i < matrix[0].length; i++) {
            //create a header for every column and name it
            Header columnHeader = new Header(names[i]);
            current.rightNode = columnHeader;
            columnHeader.leftNode = current;
            columnHeader.header = columnHeader;
            current = columnHeader;
        }

        current.rightNode = root;
        root.leftNode = current;

        current = root;
    }

    public Node getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "Matrix [root=" + root + ", current=" + current + ", names=" + Arrays.toString(names) + "]";
    }
}
