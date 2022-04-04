package files.vidmot;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ScoreObject extends AnchorPane{
    @FXML
    private GridPane gp;

    public ScoreObject() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("score-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNum(int s1, int s2, int s3) {        
        gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t" + s1 + ".png"))), 0, 0);
        gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t" + s2 + ".png"))), 1, 0);
        gp.add(new ImageView(new Image(ScoreObject.class.getResourceAsStream("imgs/num/t" + s3 + ".png"))), 2, 0);
    }
}
