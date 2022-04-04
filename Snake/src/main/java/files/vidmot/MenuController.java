package files.vidmot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import files.vidmot.GameController;

import java.io.IOException;

public class MenuController {
    @FXML private Button startButton;
    @FXML private Button deathButton;
    // @FXML private Button statButton;
    

    public void switchScenesGame(ActionEvent event) throws IOException{
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        Parent game = FXMLLoader.load(getClass().getResource("game-view.fxml"));
        Scene scene = new Scene(game);
        thisStage.setScene(scene);
    }

    public void switchScenesDeath(ActionEvent event) throws IOException{
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        Parent death = FXMLLoader.load(getClass().getResource("death-view.fxml"));
        Scene scene = new Scene(death);
        thisStage.setScene(scene);
    }
}
