package files.vidmot;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToGameScene(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("game-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDeathScene(Label event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("death-view.fxml"));
        stage = (Stage)(event).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
