package nl.group37.algorithm.matrix;

import java.util.Arrays;

public class Matrix {
    private Node root;
    private Node current;
    private String[] names;

    public Matrix(String[] names) {
        this.root = new Node();
        root.setLeftNode(root);
        root.setRightNode(root);
        this.current = root;
        this.names = names;
    }

    public void buildMatrix(int[][] matrix) {
        addHeaders(matrix);

        addNodes(matrix);
    }

    private void addNodes(int[][] matrix) {
        current = root.getRightNode();

        //creating a vertical current node for each column
        Node[] vCurrentNodes = new Node[matrix[0].length];
        //fill the current nodes with the headers respectively as starting point
        for (int i = 0; i < vCurrentNodes.length; i++) {
            vCurrentNodes[i] = current;
            current = current.getRightNode();
        }

        //set our current pointer to the first column (starting from the left)
        current = root.getRightNode();

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
                    n.setHeader(current.getHeader());

                    //in the header element, increase the number of nodes in that column. we need that to find
                    //columns with the least amount of nodes for the algorithm
                    current.getHeader().increase();

                    //if the hFirstNode is null, that means, it is the first node in this row. we set first node and c node
                    //to our node n.
                    if (hFirstNode == null) {
                        hFirstNode = n;
                        hCurrentNode = n;
                    }

                    //connect top and bottom
                    n.setUpNode(vCurrentNodes[j]);
                    vCurrentNodes[j].setDownNode(n);
                    vCurrentNodes[j] = n;

                    // connect left and right
                    n.setLeftNode(hCurrentNode);
                    hCurrentNode.setRightNode(n);
                    hCurrentNode = n;


                }
                //move our column pointer to the right to move on with the next column
                current = current.getRightNode();
            }
            //connect first and current horizontal nodes to create the circular linked list
            hCurrentNode.setRightNode(hFirstNode);
            hFirstNode.setLeftNode(hCurrentNode);

            //since we now go to the next row, we have to reset our column pointer to the beginning
            current = root.getRightNode();
        }


        //after looping through all rows, we have to connect the nodes on the bottom of every column to
        //the header nodes and all the way around to create the circular linked list also vertically
        for (int i = 0; i < vCurrentNodes.length; i++) {
            vCurrentNodes[i].setDownNode(vCurrentNodes[i].getHeader());
            vCurrentNodes[i].getHeader().setUpNode(vCurrentNodes[i]);
        }
    }

    private void addHeaders(int[][] matrix) {
        //loop through every column of our matrix
        for (int i = 0; i < matrix[0].length; i++) {
            //create a header for every column and name it
            Header columnHeader = new Header(names[i]);
            current.setRightNode(columnHeader);
            columnHeader.setLeftNode(current);
            columnHeader.setHeader(columnHeader);
            current = columnHeader;
        }

        current.setRightNode(root);
        root.setLeftNode(current);

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
