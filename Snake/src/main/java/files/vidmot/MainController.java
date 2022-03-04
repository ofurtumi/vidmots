package files.vidmot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import files.vinnsla.Tuple;

public class MainController implements Initializable {
    @FXML
    private Pane pane;

    @FXML
    private Label startText;

    private int score;
    private Label scoreLabel = new Label();

    private ArrayList<Rectangle> snakePieces = new ArrayList<Rectangle>();

    private Circle foodItem;
    private Bounds foodBounds;

    private KeyFrame k;
    private Timeline t;

    private ArrayList<snake> enemies = new ArrayList<snake>();

    // private Scene scene = pane.getScene();

    private playerSnake ps;

    @FXML
    private void handleButtonAction(MouseEvent event) {
        Scene scene = pane.getScene();
        pane.getChildren().remove(startText);
        start(scene);
    }

    private HashMap<Integer, Tuple> moveMap = new HashMap<Integer, Tuple>();

    private void createTimeline() {
        k = new KeyFrame(Duration.millis(50), e -> {
            // checkSetColor(r);
            addFood();

            // enemies[0].move();
            for (snake enemy : enemies) {
                if (enemy == null)
                    continue;
                enemy.moveRandom();
                if (ps.intersects(enemy.getBoundsInParent())) {
                    // System.out.println("Dead :/ at " + r.getX());
                    timeLineController(2);
                }
            }

            if (ps.intersects(foodBounds)) {
                pane.getChildren().remove(foodItem);
                // System.out.println("nammi namm :)~");
                addTailPiece();
                pane.getChildren().add(ps.addTailPiece());
                score++;
                scoreLabel.setText(Integer.toString(score));
            }

            ps.move();
            ps.tailMover();
        });
        t = new Timeline(k);
        t.setCycleCount(999999999);

        timeLineController(1);
    }

    private void timeLineController(int status) {
        // 1 play, 2 pause, 3 stop
        if (status == 1) {
            t.play();
        } else if (status == 2) {
            t.pause();
        } else if (status == 3) {
            t.stop();
        }
    }

    // uncommenta þetta fyrir löggusnák
    // private void checkSetColor(Rectangle r) {
    // if (r.getFill().equals(Color.BLUE))
    // r.setFill(Color.RED);
    // else
    // r.setFill(Color.BLUE);
    // }

    private void moveRect(Rectangle r) {
        int rotation = (int) r.getRotate();
        Tuple movement = (Tuple) moveMap.get(rotation);

        if (rotation == 0 && r.getX() >= pane.getWidth() - 25)
            r.setX(-25);

        else if (rotation == 180 && r.getX() <= -25)
            r.setX(pane.getWidth() - 25);
        else
            r.setX(r.getX() + movement.x);

        if (rotation == 90 && r.getY() >= pane.getHeight() - 25)
            r.setY(-25);
        else if (rotation == 270 && r.getY() <= -25)
            r.setY(pane.getHeight() - 25);
        else
            r.setY(r.getY() + movement.y);

        // tail(piece, parent);
    }

    private void tail(Rectangle piece, Rectangle parent) {
        int parentRotation = (int) parent.getRotate();
        piece.setRotate(parentRotation);
        switch (parentRotation) {
            case 0:
                piece.setX(parent.getX() - 25);
                piece.setY(parent.getY());
                break;
            case 90:
                piece.setY(parent.getY() - 25);
                piece.setX(parent.getX());
                break;
            case 180:
                piece.setX(parent.getX() + 25);
                piece.setY(parent.getY());
                break;
            case 270:
                piece.setY(parent.getY() + 25);
                piece.setX(parent.getX());
                break;
            default:
                break;
        }
    }

    private void start(Scene scene) {
        addEnemy();
        Rectangle r = new Rectangle(50, 50, Color.GREEN);
        r.setX(50);
        r.setY(50);
        snakePieces.add(r);
        ps = new playerSnake(pane);

        pane.getChildren().addAll(r, scoreLabel, ps);

        r.setOnMouseDragged(event -> {
            r.setX(event.getX() - 50);
            r.setY(event.getY() - 50);
        });

        createTimeline();

        pane.getScene().setOnKeyPressed(e -> {
            if (t.getStatus() == Status.RUNNING && (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN ||
                    e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.LEFT)) {

                KeyCode k = e.getCode();
                switch (k) {
                    case RIGHT:
                        r.setRotate(0);
                        ps.rotate(0);
                        break;
                    case DOWN:
                        r.setRotate(90);
                        ps.rotate(90);
                        break;
                    case LEFT:
                        r.setRotate(180);
                        ps.rotate(180);
                        break;
                    case UP:
                        r.setRotate(270);
                        ps.rotate(270);
                        break;
                    default:
                        break;
                }
            }

            if (e.getCode() == KeyCode.SPACE) {
            }

            if (e.getCode() == KeyCode.ESCAPE) {
                if (t.getStatus() == Status.RUNNING)
                    timeLineController(2);
                else
                    timeLineController(1);
            }
            e.consume();

        });
    }

    private void addTailPiece() {
        // todo stóra, bæta við röndum
        snakePieces.add(new Rectangle(50, 50));
        Rectangle piece = snakePieces.get(snakePieces.size() - 1);
        piece.setX(snakePieces.get(snakePieces.size() - 2).getX());
        piece.setY(snakePieces.get(snakePieces.size() - 2).getY());
        pane.getChildren().add(piece);
    }

    private void addFood() {
        if (!pane.getChildren().contains(foodItem)) {
            foodItem = new Circle(25, Color.RED);
            int yCoord = ((int) (Math.random() * pane.getHeight()) + 25);
            yCoord = yCoord - (yCoord % 50);
            int xCoord = ((int) (Math.random() * pane.getWidth()) + 25);
            xCoord = xCoord - (xCoord % 50);

            foodItem.setCenterY(yCoord);
            foodItem.setCenterX(xCoord);
            foodBounds = foodItem.getBoundsInParent();
            pane.getChildren().add(foodItem);
        }
    }

    private void addEnemy() {
        snake s = new snake(pane);
        enemies.add(s);
        pane.getChildren().add(s);
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