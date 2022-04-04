package files.vidmot;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import files.vinnsla.Score;

public class DeathController {
    @FXML
    private TextField nameInput;

    @FXML
    private GridPane playerScoreGP;

    private Score score;
    private final int maxLength = 3;

    public void submit(ActionEvent event) {
        System.out.println("nameInput.getText() --> " + nameInput.getText());
        ScoreObject scoreName = new ScoreObject(nameInput.getText());
        playerScoreGP.add(scoreName, 0, 0);
    }

    public void textCheck() {
        if (nameInput.getText().length() >= maxLength) {
            nameInput.setText(nameInput.getText().substring(0, maxLength));
            nameInput.positionCaret(3);
        }
    }

    public void sendData(Score score) {
        this.score = score;
        ScoreObject scoreScore = new ScoreObject(score);
        playerScoreGP.add(scoreScore, 1, 0);
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
