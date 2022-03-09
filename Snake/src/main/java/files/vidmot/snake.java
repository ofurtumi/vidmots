package files.vidmot;

import java.util.HashMap;

import files.vinnsla.Tuple;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class snake extends Rectangle {
    private HashMap<Integer, Tuple> moveMap = new HashMap<Integer, Tuple>();
    private int counter = 0;
    private Pane pane;
    // þarf að halda utan um pane til að geta farið í gegn um vegg
    // eftir að skjárinn er stækkaður

    public snake(Pane p) {
        this.setWidth(150);
        this.setHeight(50);
        this.setFill(Color.ORANGE);
        this.setX(250);
        this.setY(250);
        this.pane = p;
        this.setStyle("-fx-stroke: white; -fx-stroke-width: 3;");


        onStart();
    }

    public void moveRandom() {
        if (counter++ == 50) {
            rotateRandom();
            counter = 0;
        }
        if (counter % 2 == 0) {
            move();
        }
    }

    public void move() {
        int rotation = (int) this.getRotate();
        Tuple movement = (Tuple) moveMap.get(rotation);

        if (rotation == 0 && this.getX() >= pane.getWidth() - (this.getWidth()/2))
            this.setX(-(this.getWidth()/2));

        else if (rotation == 180 && this.getX() <= -(this.getWidth()/2))
            this.setX(pane.getWidth() - (this.getWidth()/2));
        else
            this.setX(this.getX() + movement.x);

        if (rotation == 90 && this.getY() >= pane.getHeight() - (this.getWidth()/2))
            this.setY(-(this.getWidth()/2));
        else if (rotation == 270 && this.getY() <= -(this.getWidth()/2))
            this.setY(pane.getHeight() - (this.getWidth()/2));
        else
            this.setY(this.getY() + movement.y);
    }

    private void rotateRandom() {
        int direction = (int) (Math.random() * 4);
        switch (direction) {
            case 0:
                this.setRotate(0);
                break;
            case 1:
                this.setRotate(90);
                break;
            case 2:
                this.setRotate(180);
                break;
            case 3:
                this.setRotate(270);
                break;
            default:
                break;

        }
    }

    private void onStart() {
        Tuple RIGHT = new Tuple(25, 0);
        Tuple LEFT = new Tuple(-25, 0);
        Tuple UP = new Tuple(0, -25);
        Tuple DOWN = new Tuple(0, 25);

        moveMap.put(0, RIGHT);
        moveMap.put(90, DOWN);
        moveMap.put(180, LEFT);
        moveMap.put(270, UP);
    }
}
