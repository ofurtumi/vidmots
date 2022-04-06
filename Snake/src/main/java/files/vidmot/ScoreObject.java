package files.vidmot;

import java.io.IOException;
import java.util.ArrayList;
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

    private ArrayList<String> alphabet = new ArrayList<String>();

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
    public ScoreObject(int score) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("score-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        char[] chars = (String.valueOf(score)).toCharArray();
        setNum(chars);
    }

    public ScoreObject(String name) {
        for (int i = 0; i < alphArr.length; i++) {
            alphabet.add(alphArr[i]);    
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
        for (int i = 0; i < 3; i++) {
            if (chars.length > i) {
                gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t" + chars[chars.length-(1+i)] + ".png"))), 2-i, 0);
            }
            else {
                gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t0.png"))), 2-i, 0);
            }
        }
    }

    private void setName(String name) {
        for (int i = 0; i < 3; i++) {
            if (name.length() != 0 && alphabet.contains(name.substring(i,i+1).toLowerCase())) {
                gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/alph/" + name.substring(i, i+1).toLowerCase() + ".png"))), i, 0);
            }
            else {
                gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/alph/hmm.png"))), i, 0);
            }
        }
    }
}
