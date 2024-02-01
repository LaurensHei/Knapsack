package scores;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RecordScores {

    public static void recordScore(String playersName, int score) {

        ArrayList<String> highscores = ReadScores.readScores();

        for(int i = 0; i < 3; i ++) {
            int current = Integer.parseInt(highscores.get(i).split(": ")[1]);
            if(score > current) {
                System.out.println("New Highscore");
                int pos = i;
                for(int j = 2; j > i; j --) {
                    highscores.set(j, highscores.get(j-1));
                }
                highscores.set(pos, playersName + ": " + score);
                break;
            }
        }


        try {
            FileWriter recorder = new FileWriter("HighScores.txt", false);
            for(String s : highscores) {
                recorder.write(s+"\n");
            }
            recorder.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }



}
