package files.vidmot;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class playerSnake extends snake {
    private Circle headHitBox = new Circle(5);


    public playerSnake() {
        this.setX(32);
        this.setY(128);
        headHitBox.setFill(Color.YELLOW);
        moveHitbox();
    }

    public void moveHitbox() {
        headHitBox.setCenterX(this.getX() + 16);
        headHitBox.setCenterY(this.getY() + 16);
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
        snakePieces.clear();
        snakeSprites.clear();
    }
}
