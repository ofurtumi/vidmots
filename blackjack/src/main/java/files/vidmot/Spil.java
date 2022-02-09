package files.vidmot;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Spil extends AnchorPane{
    @FXML private ImageView sortTop;
    @FXML private ImageView sortBottom;
    @FXML private Label tegTop;
    @FXML private Label tegBottom;

    public Spil () {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spil-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSpil(Image sort, int tegund) {
        if (tegund > 10) {
            switch (tegund) {
                case 11:
                    tegTop.setText("J");
                    tegBottom.setText("J");
                    break;
                case 12:
                    tegTop.setText("Q");
                    tegBottom.setText("Q");
                    break;
                case 13:
                    tegTop.setText("K");
                    tegBottom.setText("K");
                    break;
                default:
                    break;
            }
        }
        else {
            String teg = Integer.toString(tegund);
            tegTop.setText(teg);
            tegBottom.setText(teg);
        }

        sortTop.setImage(sort);
        sortBottom.setImage(sort);
    }
    
}