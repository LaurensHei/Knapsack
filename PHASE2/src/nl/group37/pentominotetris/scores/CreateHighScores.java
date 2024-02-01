package scores;

import java.io.File;
import java.io.IOException;

public class CreateHighScores {
    public static void main(String[] args) {
        try {
            File highScores = new File("HighScores.txt");
            if(highScores.createNewFile()){
                System.out.println("File created: " + highScores.getName());
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }



}
