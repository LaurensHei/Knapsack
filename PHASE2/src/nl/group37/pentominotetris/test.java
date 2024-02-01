public class test {
public static void main(String[] args) {
    int[][] field = {
        {-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,1},
        {1,1,1,1,1},};
    System.out.println(getScore(field));
}
    private static double getScore(int[][] field) {
        // Agregate height
        int AHscore = 0;
        int[] AHscores = new int[field[0].length];
        for (int i = 0; i < field[0].length; i++) {
            int maxHeight = field.length;
            for (int j = field.length - 1; j >= 0; j--) {
                if (field[j][i] != -1) {
                    maxHeight = j + 1;
                }
            }
            AHscores[i] = field.length - maxHeight;
        }
        for (int i = 0; i < AHscores.length; i++) {
            AHscore += AHscores[i];
        }
        // Complete lines
        int CLscore = 0;
        for (int i = 0; i < field.length; i++) {
            int count = 0;
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == 1) {
                    count++;
                }
            }
            if (count >= field[0].length) {
                CLscore++;
            }
        }
        // Holes
        int Hscore = 0;
        System.out.println(field.length);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] != -1 && i != 0 && field[i - 1][j] != -1) {
                    if (i != field.length - 1 && field[i + 1][j] != -1) {
                        if (j != 0 && field[i][j - 1] != -1) {
                            if (j != field[0].length - 1 && field[i][j + 1] != -1) {
                                Hscore++;
                            }
                        }
                    }
                }
            }
        }

        // Bumpiness
        int Bscore = 0;
        int[] Bscores = new int[field[0].length];
        int previousScore = 0;
        for (int i = 0; i < field[0].length; i++) {
            int maxHeight = field.length;
            for (int j = field.length - 1; j >= 0; j--) {
                if (field[j][i] != -1) {
                    maxHeight = j + 1;
                }
            }
            maxHeight = field.length - maxHeight;
            if (i == 0) {
                previousScore = maxHeight;
            }
            Bscores[i] = Math.abs(maxHeight - previousScore);
            previousScore = maxHeight;
        }
        for (int i = 0; i < Bscores.length; i++) {
            Bscore += Bscores[i];
        }

        // Sum
        return ((-0.510066 * AHscore) + (100000.760666 * CLscore) + (-0.35663 * Hscore) + (-0.184483 * Bscore));
    }
}