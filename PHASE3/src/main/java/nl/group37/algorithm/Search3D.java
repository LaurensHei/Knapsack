package nl.group37.algorithm;

import javafx.application.Platform;
import nl.group37.algorithm.matrix.Header;
import nl.group37.algorithm.matrix.Node;
import nl.group37.gui.GUI;

import java.util.Stack;

public class Search3D {
    private Stack<Node> solutionStack = new Stack<Node>();

    private Node root;
    private boolean done;
    //private int count;

    private int cargo_value;
    private int maxValue;
    //private int c;

    public static int[][][] output1;

    public static int[][][] solutionWithoutGaps;

    public Search3D(Node root) {
        System.out.println("Search initialized");
        this.root = root;
        this.done = false;
        this.cargo_value = 0;
        this.maxValue = 0;
        //this.c = 0;
    }

    public int getValue(Node n) {
        switch (n.type) {
            case 'A':
                return 3;
            case 'B':
                return 4;
            case 'C':
                return 5;
            case 'L':
                return 3;
            case 'P':
                return 4;
            case 'T':
                return 5;
            default:
                return 0;
        }
    }


    public void search() {
        //If R[h] = h, print the current solution (see below) and return.
        if (root.getRightNode() == root) {

            this.done = true;
            printSolution();
            //countSolution();
            return;
        }

        //Otherwise choose a column object c (see below).
        Node c = chooseSmallestColumn();

        //cover column c
        cover(c);

        //For each r ← D[c], D[D[c]], ..., while r != c, set O[k] ← r
        for (Node r = c.downNode; r != c; r = r.downNode) {
            //set Ok ← r;
            solutionStack.push(r);
            cargo_value += getValue(r);
            //for each j <- R[r], R[R[r], ..., while j != r
            for (Node j = r.rightNode; j != r; j = j.rightNode) {
                // cover column j
                cover(j.getHeader());
            }
            if (!this.done) {
                search();
            }
            //set r ← Ok and c ← C[r];
            if(cargo_value > maxValue) {
                maxValue = cargo_value;
                System.out.println("Max Value Found " + maxValue);

                //print partial solution
                printPartialSolution();
            }
            solutionStack.pop();
            cargo_value -= getValue(r);
            c = r.header;
            //for each j ← L[r], L[L[r]], ..., while j != r
            for (Node j = r.leftNode; j != r; j = j.leftNode) {
                //uncover column j
                uncover(j.getHeader());
            }
        }
        //uncover column c
        uncover(c);
    }


    private void cover(final Node c) {
        // Set L[R[c]] ← L[c]
        c.rightNode.leftNode = c.leftNode;
        //and R[L[c]] ← R[c].
        c.leftNode.rightNode = c.rightNode;
        //For each i ← D[c], D[D[c]], ..., while i != c
        for (Node i = c.downNode; i != c; i = i.downNode) {
            //for each j ← R[i], R[R[i]], ..., while j != i
            for (Node j = i.rightNode; j != i; j = j.rightNode) {
                //set U[D[j]] ← U[j]
                j.downNode.upNode = j.upNode;
                //and D[U[j]] ← D[j]
                j.upNode.downNode = j.downNode;
                //and set S[C[j]] ← S[C[j]] − 1
                j.header.size--;

            }
        }
    }

    private void uncover(final Node c) {

        //For each i = U[c], U[U[c]], ..., while i  != c
        for (Node i = c.upNode; i != c; i = i.upNode) {

            //for each j ← L[i], L[L[i]], ..., while j != i
            for (Node j = i.leftNode; j != i; j = j.leftNode) {
                //set S[C[j]] ← S[[j]] + 1
                j.header.size++;

                //and set U[D[j]] ← j
                j.upNode.downNode = j;
                //and D[U[j]] ← j
                j.downNode.upNode = j;

            }
        }

        //Set R[L[c]] ← c
        c.rightNode.leftNode = c;
        //And L[R[c]] ← c
        c.leftNode.rightNode = c;
    }

    private Node chooseSmallestColumn() {
        int s = 99999999;
        Node c = null;

        for (Node n = root.getRightNode(); n != root; n = n.getRightNode()) {
            Header h = n.getHeader();
            if (h.getSize() < s) {
                s = h.getSize();
                c = h;
            }
        }
        return c;
    }

    public void printPartialSolution() {
        int[][][] output = new int[8][5][33];
        int fieldsFilled = 0;
        for(int i = 0; i < solutionStack.size(); i++) {
            Node n = solutionStack.get(i);
            Node start = n;
            do {
                int index = Integer.parseInt(n.getHeader().getName());
                int identifier = getIdentifier(n.type);
                int[] coords = getCoordinatesForIndex(index);
                output[coords[0]][coords[1]][coords[2]] = identifier;
                fieldsFilled++;
                n = n.getRightNode();
            }
            while(n != start);
        }
        System.out.println("Solution found with " + (1320-fieldsFilled) + " gaps.");
        Platform.runLater(() -> {
            GUI.update(output,50);
            GUI.updateMax(maxValue);
            output1=output;
        });
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    // Get coordinates (i, j, k) for a given index
    private int[] getCoordinatesForIndex(int index) {
        int[] coordinates = new int[3];

        int remainingIndex = index - 1;

        //int size1 = 8;
        int size2 = 5;
        int size3 = 33;

        coordinates[0] = remainingIndex / (size2 * size3); // i
        remainingIndex %= (size2 * size3);

        coordinates[1] = remainingIndex / size3; // j
        coordinates[2] = remainingIndex % size3; // k

        return coordinates;
    }

    private int getIdentifier(char type) {
        switch (type) {
            case 'A':
                return 1;
            case 'B':
                return 2;
            case'C':
                return 3;
            case 'T':
                return 1;
            case 'L':
                return 2;
            case'P':
                return 3;
            default:
                return 0;
        }

    }

    private void printSolution() {
        solutionWithoutGaps = new int[8][5][33];
        for(int i = 0; i < solutionStack.size(); i++) {
            Node n = solutionStack.get(i);
            Node start = n;
            do {
                int index = Integer.parseInt(n.getHeader().getName());
                int identifier = getIdentifier(n.type);
                int[] coords = getCoordinatesForIndex(index);
                solutionWithoutGaps[coords[0]][coords[1]][coords[2]] = identifier;
                n = n.getRightNode();
            }
            while(n != start);
        }
        System.out.println("Found solution with 0 gaps! Value: " + cargo_value);

    }

    public int getMaxValue() {
        return maxValue;
    }
}