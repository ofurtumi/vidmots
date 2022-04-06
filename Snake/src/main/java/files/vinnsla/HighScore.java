package files.vinnsla;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

public class HighScore {
    private File file;
    private HashMap<String, Integer> scoreValues = new HashMap<String, Integer>();
    private ArrayList<String> topNames = new ArrayList<String>();
    private ArrayList<Integer> topScores = new ArrayList<Integer>();

    public HighScore() throws FileNotFoundException {
        URL url = getClass().getResource("scores.txt");
        this.file = new File(url.getPath());
        Scanner scanner = new Scanner(file);
        String readString = "";

        while (scanner.hasNext()) {
            readString += scanner.next();
        }
        scanner.close();

        String[] fileValues = readString.split(",");
        for (int i = 0; i < fileValues.length; i++) {
            String[] temp = fileValues[i].split(":");
            if (temp.length < 2)
                scoreValues.put(temp[0], 0);
            else if (scoreValues.containsKey(temp[0])) {
                int u = 1;
                while (scoreValues.containsKey(temp[0] + u))
                    u++;
                scoreValues.put(temp[0] + u, Integer.parseInt(temp[1]));
            } else {
                scoreValues.put(temp[0], Integer.parseInt(temp[1]));
            }
        }

        scoreValues = sortByValue(scoreValues);

        scoreValues.forEach((name, score) -> {
            topNames.add(name);
            topScores.add(score);
        });
    }

    private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                    Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void saveScore(String name, int score) {
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write("," + name + ":" + score);
            fw.close();
        } catch (Exception e) {
            System.out.println("saveScore HighScore.java --> " + e);
        }
    }

    public ArrayList<String> getNames() {
        return this.topNames;
    }

    public ArrayList<Integer> getScores() {
        return this.topScores;
    }
}
