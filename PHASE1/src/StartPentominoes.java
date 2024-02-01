import java.util.Scanner;

public class StartPentominoes {
    /** 
     */
    public static void main(String[] args) {
        boolean correct = true;
        String correctInput = "XYWLVTPNFUZI";
        Scanner scanner = new Scanner(System.in);
        System.out.println("How long do you want the first axis size to be?");
        int horizontalGridSize = scanner.nextInt();
        System.out.println("How long do you want the second axis size to be?");
        int verticalGridSize =  scanner.nextInt();
        System.out.println("What pentomino pieces do you want to use?");
        String pieces = scanner.next();
        scanner.nextLine();
        char[] pentominoes = pieces.toCharArray();

        if (pentominoes.length == 12) {
            char[] in = {'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F'};
            pentominoes = in;
        }

        //Our algorithm is faster when the horizontal side is the long side, so we switch them around if that is not the case
        if (verticalGridSize > horizontalGridSize) {
            int temp = verticalGridSize;
            verticalGridSize = horizontalGridSize;
            horizontalGridSize = temp;
        }

        for (int i = 0; i < pentominoes.length; i++) {
            if (correctInput.contains(pentominoes[i] + "")){
                //nothing happens
            }
            else {
                System.out.println("Wrong Pentominoes the available are : X Y W L V T P N F U Z I");
                correct = false;
                break;
            }
        }
        if (verticalGridSize <= 0 || horizontalGridSize <= 0  || verticalGridSize*horizontalGridSize%5 != 0 ) {
            correct = false; 
            System.out.println("These grid sizes cannot be used");
        }
   
        if (correct) {     
            UI ui = new UI(horizontalGridSize, verticalGridSize, 50);
            System.out.println("Which algorithm do you want to use: Branching (1) or BruteForce (2)?");
            int choice = scanner.nextInt();
            if (choice == 1) {
                // Initialize an empty board
                int[][] field = new int[horizontalGridSize][verticalGridSize];
                //int[][] field = new int[verticalGridSize][horizontalGridSize];

                for(int i = 0; i < field.length; i++)
                {
                    for(int j = 0; j < field[i].length; j++)
                    {
                        // -1 in the state matrix corresponds to empty square
                        // Any positive number identifies the ID of the pentomino
                        field[i][j] = -1;
                    }
                }
                BranchSearching.input = pentominoes;
                BranchSearching.ui = ui;
                BranchSearching.branchSearch(field, new boolean[pentominoes.length]);
            }
            else if (choice == 2) {
                if(verticalGridSize * horizontalGridSize/ 5 == pentominoes.length){
                    int[][] field = new int[horizontalGridSize][verticalGridSize];
                    for (int i = 0; i < field.length; i++) {
                        for (int j = 0; j < field[i].length; j++) {
                            field[i][j] = -1;
                        }
                    }
                    BruteForce.input = pentominoes;
                    BruteForce.ui = ui ;
                    BruteForce.bruteforce(field);
                    System.out.println("Done running"); 
                }
                else {
                    System.out.println("Wrong input ");
                }
            }
            else {
                System.out.println("Wrong Input of algorithm");
            }
        }
        else {
            System.out.println("Goodbye");
        }
        scanner.close();
    }
}