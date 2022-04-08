package files.vidmot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import files.vinnsla.HighScore;
import files.vinnsla.Stats;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StatsView {
    private HighScore HS;
    private Stats S;

    private ArrayList<String> topNames = new ArrayList<String>();
    private ArrayList<Integer> topScores = new ArrayList<Integer>();

    @FXML
    private GridPane topGP;
    
    @FXML
    private Label statsLabel;

    public void setsStats() {
        try {
            HS = new HighScore();
            S = new Stats();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        statsLabel.setText(S.toString());

        topNames = HS.getNames();
        topScores = HS.getScores();

        for (int i = 0; i < topNames.size(); i++) {
            ScoreObject tempName = new ScoreObject(topNames.get(i).substring(0, 3));
            ScoreObject tempScore = new ScoreObject(topScores.get(i));

            topGP.add(tempName, 0, i);
            topGP.add(tempScore, 1, i);
            if (i == 4) break;
        }
    }

    public void sceneSwitchMenu(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        Parent menu = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
        Scene scene = new Scene(menu);
        thisStage.setScene(scene);
    }
}
