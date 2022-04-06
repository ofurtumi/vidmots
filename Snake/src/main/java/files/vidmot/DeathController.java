package files.vidmot;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import files.vinnsla.HighScore;
import files.vinnsla.Score;

public class DeathController {
    @FXML
    private AnchorPane deathRoot;
    @FXML
    private TextField nameInput;
    @FXML
    private Button submitButton;
    @FXML
    private GridPane playerScoreGP;
    @FXML
    private GridPane topGP;

    private Score newScore;
    private final int maxLength = 3;

    private HighScore HS;

    private ArrayList<String> topNames = new ArrayList<String>();
    private ArrayList<Integer> topScores = new ArrayList<Integer>();

    public void submit(ActionEvent event) {
        ScoreObject scoreName = new ScoreObject(nameInput.getText());
        playerScoreGP.add(scoreName, 0, 0);
        deathRoot.getChildren().removeAll(nameInput, submitButton);
        HS.saveScore(nameInput.getText(), newScore.getScore());
    }

    public void textCheck() {
        if (nameInput.getText().length() >= maxLength) {
            nameInput.setText(nameInput.getText().substring(0, maxLength));
            nameInput.positionCaret(3);
        }
    }

    public void sendData(Score newScore) {
        try {
            HS = new HighScore();
        } catch (Exception e) {
            System.out.println("new HighScore DeathController - 81 --> " + e);
        }

        topNames = HS.getNames();
        topScores = HS.getScores();

        this.newScore = newScore;
        ScoreObject scoreScore = new ScoreObject(newScore);
        playerScoreGP.add(scoreScore, 1, 0);
        ScoreObject scoreName = new ScoreObject("");
        playerScoreGP.add(scoreName, 0, 0);


        for (int i = 0; i < 5; i++) {
            ScoreObject tempName = new ScoreObject("");
            ScoreObject tempScore = new ScoreObject(0);
            topGP.add(tempName, 0, i);
            topGP.add(tempScore, 1, i);
        }

        for (int i = 0; i < topNames.size(); i++) {
            ScoreObject tempName = new ScoreObject(topNames.get(i).substring(0,3));
            ScoreObject tempScore = new ScoreObject(topScores.get(i));

            topGP.add(tempName, 0, i);
            topGP.add(tempScore, 1, i);
            if (i == 4)
                break;
        }
    }

    public void sceneSwitchGame(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        FXMLLoader game = new FXMLLoader(getClass().getResource("game-view.fxml"));
        Scene scene = new Scene(game.load());
        thisStage.setScene(scene);
    }

    public void sceneSwitchMenu(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        Parent menu = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
        Scene scene = new Scene(menu);
        thisStage.setScene(scene);
    }
}
