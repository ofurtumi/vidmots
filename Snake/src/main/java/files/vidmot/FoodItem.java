package files.vidmot;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FoodItem extends ImageView{
    public FoodItem () {
        this.setImage(new Image(getClass().getResourceAsStream("imgs/apple.gif")));
    }
}
