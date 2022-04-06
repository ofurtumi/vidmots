package files.vinnsla;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Stats {
    private int apples;
    private int specials;
    private int enemies;
    private int games;
    private int avarage; // ? = (int)((apples + (specials*5)) / games);
    private boolean single;

    public Stats() throws FileNotFoundException {
        URL url = getClass().getResource("stats.txt");
        File file = new File(url.getPath());
        Scanner scanner = new Scanner(file);

        this.apples = scanner.nextInt();
        this.specials = scanner.nextInt();
        this.enemies = scanner.nextInt();
        this.games = scanner.nextInt();
        this.avarage = scanner.nextInt();

        scanner.close();
    }

    public void saveStats() {
        // todo bæta við skránna
        // ? örugglega betra að yfirskrifa til að lenda ekki í ehv overflow
        setAvarage();
        try {
            URL url = getClass().getResource("stats.txt");
            File file = new File(url.getPath());
            FileWriter fw = new FileWriter(file); // the true will append the new data
            fw.write(this.apples + "\n" + this.specials + "\n" + this.enemies + "\n" + this.games + "\n" + this.avarage);
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public Stats(boolean single) {
        this.single = true;
        this.apples = 0;
        this.specials = 0;
        this.enemies = 0;
    }

    public void setApples(int n) {
        this.apples += n;
    }

    public void setSpecials(int n) {
        this.specials += n;
    }

    public void setEnemies(int n) {
        this.enemies += n;
    }

    public void setGames(int n) {
        this.games += n;
    }

    public void setAvarage() {
        this.avarage = (int) ((apples + (specials * 5)) / games);
    }

    public String toString() {
        if (this.single) {
            return "epli:" + this.apples + "\nofur-epli:" + this.specials + "\nnöðrur:" + this.enemies;
        }
        return "apples:" + this.apples + "\nspecials:" + this.specials + "\nenemies:" + this.enemies + "\ngames:"
                + this.games + "\navg score:" + this.avarage;
    }

    public int getApples() {
        return apples;
    }

    public int getSpecials() {
        return specials;
    }

    public int getEnemies() {
        return enemies;
    }

    public int getGames() {
        return games;
    }

    public int getAvarage() {
        return avarage;
    }
}
