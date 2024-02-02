package nl.group37.algorithm;

import javafx.application.Platform;
import nl.group37.algorithm.matrix.Header;
import nl.group37.algorithm.matrix.Node;
import nl.group37.gui.GUI;

import java.util.Stack;

public class Search3D {
    private Stack<Node> solutionStack = new Stack<Node>();
    private final Node root;
    private boolean done;
    private int cargoValue;
    private int maxValue;


    public Search3D(Node root) {
        System.out.println("Search initialized");
        this.root = root;
        this.done = false;
        this.cargoValue = 0;
        this.maxValue = 0;
    }


    public void search() {
        //If R[h] = h, print the current solution (see below) and return.
        if (root.rightNode == root) {
            this.done = true;
            printSolution();
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
            cargoValue += getValue(r);
            //for each j <- R[r], R[R[r], ..., while j != r
            for (Node j = r.rightNode; j != r; j = j.rightNode) {
                // cover column j
                cover(j.header);
            }
            if (!this.done) {
                search();
            }
            //set r ← Ok and c ← C[r];
            if (cargoValue > maxValue) {
                maxValue = cargoValue;
                System.out.println("Value: " + maxValue);

                //print partial solution
                printSolution();
            }
            solutionStack.pop();
            cargoValue -= getValue(r);
            c = r.header;
            //for each j ← L[r], L[L[r]], ..., while j != r
            for (Node j = r.leftNode; j != r; j = j.leftNode) {
                //uncover column j
                uncover(j.header);
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

        for (Node n = root.rightNode; n != root; n = n.rightNode) {
            Header h = n.header;
            if (h.size < s) {
                s = h.size;
                c = h;
            }
        }
        return c;
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

    public void printSolution() {
        int[][][] output = new int[8][5][33];
        int fieldsFilled = 0;
        for (int i = 0; i < solutionStack.size(); i++) {
            Node n = solutionStack.get(i);
            Node start = n;
            do {
                int index = Integer.parseInt(n.header.name);
                int identifier = getIdentifier(n.type);
                int[] coords = getCoordinatesForIndex(index);
                output[coords[0]][coords[1]][coords[2]] = identifier;
                fieldsFilled++;
                n = n.rightNode;
            }
            while (n != start);
        }
        System.out.println("Solution found with " + (1320 - fieldsFilled) + " gaps.");
        Platform.runLater(() -> {
            GUI.update(output, 50);
            GUI.updateMax(maxValue);
        });
    }

    private int getIdentifier(char type) {
        switch (type) {
            case 'A':
                return 1;
            case 'B':
                return 2;
            case 'C':
                return 3;
            case 'T':
                return 1;
            case 'L':
                return 2;
            case 'P':
                return 3;
            default:
                return 0;
        }

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
}
