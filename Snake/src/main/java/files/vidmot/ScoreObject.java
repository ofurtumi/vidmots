package files.vidmot;

import java.io.IOException;
import java.util.HashMap;

import files.vinnsla.Score;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ScoreObject extends AnchorPane {
    @FXML
    private GridPane gp;

    private String[] alphArr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "r",
            "s", "t", "u", "v", "w", "x", "y", "z" };

    private HashMap<String, Integer> alphabet = new HashMap<String, Integer>();

    public ScoreObject(Score score) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("score-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        char[] chars = (String.valueOf(score.getScore())).toCharArray();
        setNum(chars);
    }

    public ScoreObject(String name) {
        for (int i = 0; i < alphArr.length; i++) {
            alphabet.put(alphArr[i], i);    
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("score-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setName(name);
    }

    private void setNum(char[] chars) {
        gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t" + chars[0] + ".png"))), 2, 0);
        if (chars.length > 1)
            gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t" + chars[1] + ".png"))), 1,
                    0);
        else
            gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t0.png"))), 1, 0);
        if (chars.length > 2)
            gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t" + chars[2] + ".png"))), 0,
                    0);
        else
            gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t0.png"))), 0, 0);
    }

    private void setName(String name) {
        for (int i = 0; i < 3; i++) {
            if (alphabet.containsKey(name.substring(i,i+1))) {
                gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/alph/" + name.substring(i, i+1) + ".png"))), i, 0);
            }
            else {
                gp.add(new Label(name.substring(i, i+1)), i, 0);
            }
        }
    }
}
