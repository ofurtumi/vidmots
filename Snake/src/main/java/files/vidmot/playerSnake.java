package files.vidmot;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class playerSnake extends snake {
    private Circle headHitBox = new Circle(5);


    public playerSnake() {
        getImages();

        this.setImage(imgs[0]);
        this.setX(32);
        this.setY(128);
        moveHitbox();
    }

    public void moveHitbox() {
        headHitBox.setCenterX(this.getX() + 16);
        headHitBox.setCenterY(this.getY() + 16);
    }

    /**
     * gets sprite images from resource folder 
     * * head:  0
     * * body:  1
     * * left:  2
     * * tail:  3
     */
    private void getImages() {
        for (int i = 0; i < 4; i++) {
            this.imgs[i] = new Image(snake.class.getResourceAsStream("imgs/ps"+(i+1)+".png"));
        }
    }

    public ArrayList<ImageView> getSprites() {
        return this.snakeSprites;
    }

    public Circle getHitbox() {
        return headHitBox;
    }

    public void turn(KeyCode dir) {
        switch (dir) {
            case D:
            case RIGHT:
                if (this.velY != 0 && this.velX != -1) {
                    this.velY = 0;
                    this.velX = 1;
                }
                break;
            case S:
            case DOWN:
                if (this.velX != 0 && this.velY != -1) {
                    this.velY = 1;
                    this.velX = 0;
                }
                break;
            case A:
            case LEFT:
                if (this.velY != 0 && this.velX != 1) {
                    this.velY = 0;
                    this.velX = -1;
                }
                break;
            case W:
            case UP:
                if (this.velX != 0 && this.velY != 1) {
                    this.velY = -1;
                    this.velX = 0;
                }
                break;
            default:
                break;
        }
    }

    public void death() {
        snakeSprites.clear();
    }
}
