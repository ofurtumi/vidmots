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

    public int getScore() {
        return this.scoreNow;
    }

    public String getScoreString() {
        return Integer.toString(this.scoreNow);
    }

    public void saveScore(String name) {
        // todo: save score to file {score:this.scorenow, name:name}
    }
}
