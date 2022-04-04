package files.vidmot;


import java.io.IOException;

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
        ScoreObject S1 = new ScoreObject();
        ScoreObject S2 = new ScoreObject();
        S1.setNum(5, 9, 3);
        S2.setNum(0,1,5);
        playerScoreGP.add(S1, 0, 0);
        playerScoreGP.add(S2, 1, 0);
    }

    public void textCheck() {
        if (nameInput.getText().length() >= maxLength) {
            nameInput.setText(nameInput.getText().substring(0,maxLength));
            nameInput.positionCaret(3);
        }
    }

    public void sceneSwitchGame(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        Parent game = FXMLLoader.load(getClass().getResource("game-view.fxml"));
        Scene scene = new Scene(game);
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
