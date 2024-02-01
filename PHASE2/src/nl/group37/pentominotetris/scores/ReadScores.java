package scores;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class ReadScores {


    public static ArrayList<String> readScores() {
        ArrayList<String> list = new ArrayList<>();
        try {
            File score = new File("PHASE2\\HighScores.txt");
            Scanner myReader = new Scanner(score);
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                list.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return list;
    }

    public static void getBestScores(int amount) {

        try {
            File score = new File("PHASE2\\HighScores.txt");
            Scanner myReader = new Scanner(score);
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                //String playersName = data.split(":")[0];
                //int playersScore = Integer.parseInt(data.split(": ")[1]);
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
       getBestScores(10);
    }
}
