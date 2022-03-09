package files.vinnsla;

import java.io.Console;

public class Score {
    private int[] score = new int[5];
    private int scoreNow;

    public Score() {
        this.scoreNow = 0;
    }

    public void plus() {
        this.scoreNow++;
    }

    public String saveScore() {
        String outString = "Topp 5 stig:";
        for (int i = 0;i < score.length; i++) {
            if (score[i] < this.scoreNow) {
                System.out.println(i);
                for (int j = score.length-1; j > i; j--) {
                    score[j] = score[j-1];
                }
                score[i] = this.scoreNow;
                break;
            }
        }
        for (int i : score) {
            outString += "\n" + i;
        }

        outString += "\n\n þín stig núna " + this.scoreNow;
        this.scoreNow = 0;
        return outString;
    }

    public String getScore() {
        return Integer.toString(this.scoreNow);
    }
}
