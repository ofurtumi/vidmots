package files.vidmot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import files.vinnsla.Tuple;

public class MainController implements Initializable {
    @FXML
    private Pane pane;

    @FXML
    private Label startText;

    // private Scene scene = pane.getScene();

    @FXML
    private void handleButtonAction(MouseEvent event) {
        Scene scene = pane.getScene();
        System.out.println(scene); // Gives you the Scene
        pane.getChildren().remove(startText);
        start(scene);
    }

    private HashMap moveMap = new HashMap<Integer, Tuple>();

    private void StartTimeline(Rectangle r) {
        KeyFrame k = new KeyFrame(Duration.millis(250), e -> {
            checkSetColor(r);
            moveRect(r);
        });

        Timeline t = new Timeline(k);
        t.setCycleCount(999999999);
        t.play();
    }

    private void checkSetColor(Rectangle r) {
        if (r.getFill().equals(Color.BLUE))
            r.setFill(Color.RED);
        else
            r.setFill(Color.BLUE);
    }

    private void moveRect(Rectangle r) {
        int rotation = (int) r.getRotate();
        Tuple movement = (Tuple) moveMap.get(rotation);

        if (rotation == 0 && r.getX() >= 475)
            r.setX(-25);

        else if (rotation == 180 && r.getX() <= -25)
            r.setX(475);
        else
            r.setX(r.getX() + movement.x);

        if (rotation == 90 && r.getY() >= 475)
            r.setY(-25);
        else if (rotation == 270 && r.getY() <= -25)
            r.setY(475);
        else
            r.setY(r.getY() + movement.y);
    }

    private void start(Scene scene) {
        Rectangle r = new Rectangle(50, 50, Color.RED);
        r.setX(50);
        r.setY(50);

        pane.getChildren().add(r);

        r.setOnMouseDragged(event -> {
            r.setX(event.getX() - 50);
            r.setY(event.getY() - 50);
        });

        pane.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN ||
                    e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.LEFT) {

                KeyCode k = e.getCode();
                switch (k) {
                    case RIGHT:
                        r.setRotate(0);
                        break;
                    case DOWN:
                        r.setRotate(90);
                        break;
                    case LEFT:
                        r.setRotate(180);
                        break;
                    case UP:
                        r.setRotate(270);
                        break;

                    default:
                        break;
                }
                System.out.println(k);
            }
            e.consume();

        });
        StartTimeline(r);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tuple RIGHT = new Tuple(25, 0);
        Tuple LEFT = new Tuple(-25, 0);
        Tuple UP = new Tuple(0, -25);
        Tuple DOWN = new Tuple(0, 25);

        moveMap.put(0, RIGHT);
        moveMap.put(90, DOWN);
        moveMap.put(180, LEFT);
        moveMap.put(270, UP);

        // start();
    }
}