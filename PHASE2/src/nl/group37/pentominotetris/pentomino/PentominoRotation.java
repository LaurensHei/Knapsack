package pentomino;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PentominoRotation {
    public static int[][][][] blockdata =loadData("pentominos.csv"); 
    private int currentstate;
    private int currentBlock;
    
    public PentominoRotation(){
        this.currentstate=0;
        this.currentBlock=11;
    }
    public void rotateBlockR() {
        if (currentstate < blockdata[currentBlock].length - 1) {
            currentstate++;
        } else {
            currentstate = 0;
        }
        //printBlock(blockdata[currentBlock][currentstate]);
    }
    public void rotateBlockL() {
        if (currentstate > 0) {
            currentstate--;
        } else {
            currentstate = blockdata[currentBlock].length-1;
        }
        //printBlock(blockdata[currentBlock][currentstate]);
    }
    public void printBlock(int[][] block) {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                System.out.print(block[i][j] + ",");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        PentominoRotation lBlock = new PentominoRotation();
        lBlock.rotateBlockR();
        System.out.println();
        lBlock.rotateBlockL();
        System.out.println();
        lBlock.rotateBlockR();
        System.out.println();
        lBlock.rotateBlockR();
        System.out.println();
        
    }
    private static int[][][][] loadData(String fileName)
    {
        //Create a temporary dynamic object to store the data, later to be converted to a static 4D array
        ArrayList<ArrayList<int[][]>> dynamicList =  new ArrayList<>();

        //Open the CSV file
        File file = new File(fileName);

        try
        {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) // For each line in the CSV file
            {
                // Read the line, and convert the string to a list of numbers
                String[] values = scanner.nextLine().split(",");

                // If this piece has a new ID, increase the list
                if(Integer.valueOf(values[0]) > dynamicList.size() - 1)
                {
                    dynamicList.add(new ArrayList<>());
                }

                int xSize = Integer.valueOf(values[2]);
                int ySize = Integer.valueOf(values[3]);
                int[][] piece = new int[xSize][ySize];

                // Convert 1D list to 2D list
                for(int i = 0; i < xSize * ySize; i++)
                {
                    piece[i / ySize][i % ySize] = Integer.valueOf(values[4 + i]);
                }

                // Add piece to the dynamic list
                dynamicList.get(dynamicList.size() - 1).add(piece);
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(0);
        }

        //Arrays index easier than ArrayLists, so convert dynamic list to static list
        int[][][][] staticList = new int[dynamicList.size()][][][];
        for(int i = 0; i < dynamicList.size(); i++)
        {
            staticList[i] = dynamicList.get(i).toArray(new int[0][0][0]);
        }
        return staticList;
    }

    public int getCurrentstate() {
        return currentstate;
    }
    public void setCurrentBlock(int currentblock) {
        this.currentBlock = currentblock;
    }
}
