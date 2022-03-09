package files.vidmot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;

import files.vinnsla.Score;

public class MainController {
    @FXML
    private Pane pane;

    @FXML
    private Label startText;

    private Score score = new Score();
    private Label scoreLabel = new Label(score.getScore());

    private Circle foodItem;
    private Bounds foodBounds;

    private KeyFrame k;
    private Timeline t;

    private ArrayList<snake> enemies = new ArrayList<snake>();

    private playerSnake ps;
    private boolean isDead;

    private int counter = 0;

    /**
     * handler fyrir startbutton, gefur leikmanni smá tíma til að byrja leikinn
     * auk þess sem nær í scene sem er nauðsynlegt að hafa í start til að finna
     * staðsetningu veggjanna
     * 
     * @param event músasmell
     */
    @FXML
    private void handleButtonAction(MouseEvent event) {
        pane.getChildren().remove(startText);
        start();
    }

    /**
     * býr til tímalínu fyrir "leikja lykkjuna"
     * endar á að kalla á timeLineController sem er fallið sem
     * heldur utan um stöðu, [PLAY,PAUSE,STOP], leiksins
     */
    private void createTimeline() {
        k = new KeyFrame(Duration.millis(50), e -> {
            addFood();
            addEnemy();
            if (enemies.size() > 0)
                moveEnemies();
            intersectChecks();
            movePlayer();
        });
        t = new Timeline(k);
        t.setCycleCount(Timeline.INDEFINITE);

        timeLineController(1);
    }

    /**
     * fall sem einfaldar það að pása, spila eða stoppa "leikjalykkjuna"
     * tekur inn heiltölu 1,2,3
     * 1, spilar lykkjuna
     * 2, pásar lykkjuna
     * 3, stoppar lykkjuna, gerist aðeins við dauða snáksins
     * 
     * @param status, heiltala 1,2,3
     */
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

    /**
     * örfall til að þurfa ekki að kalla á ps.move og ps.tailmove beint inn í
     * leikjalykkjunni
     */
    private void movePlayer() {
        ps.move();
        ps.tailMover();
    }

    /**
     * örfall til að athuga hvort snákur snerti mat
     * ef hann snertir mat, hækka stigin um 1,
     * drepa núverandi mat og bæta við nýjum halabút
     */
    private void intersectChecks() {
        if (ps.intersects(foodBounds)) {
            pane.getChildren().remove(foodItem);
            // System.out.println("nammi namm :)~");
            pane.getChildren().add(ps.addTailPiece());
            score.plus();
            scoreLabel.setText(score.getScore());
        }
    }

    /**
     * loopar yfir enemies listann og kallar á moveRandom() fyrir hvern og einn
     * athugar líka hvort player og enemy rekast á, ef svo kallar á death()
     */
    private void moveEnemies() {
        for (snake enemy : enemies) {
            if (enemy == null)
                continue;
            enemy.moveRandom();
            if (ps.intersects(enemy.getBoundsInParent())) {
                // System.out.println("Dead :/ at " + r.getX());
                death();
            }
        }
    }

    /**
     * mikilvægasta fallið, frumstillir allt, hreinsar skjáinn,
     * eyðir öllum óvinum, bætir við einum óvini, býr til nýjann playerSnake
     */
    private void start() {
        isDead = false;
        pane.getChildren().clear();
        enemies.clear();
        addEnemy();
        ps = new playerSnake(pane);

        // fyrir debug, leyfir það að færa snák með músinni
        // ps.setOnMouseDragged(event -> {
        // ps.setX(event.getX() - 50);
        // ps.setY(event.getY() - 50);
        // });

        pane.getChildren().addAll(scoreLabel, ps);

        createTimeline();

        pauseEvent();

        pane.getScene().setOnKeyPressed(e -> {
            if (t.getStatus() == Status.RUNNING && (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN ||
                    e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.W
                    || e.getCode() == KeyCode.A || e.getCode() == KeyCode.S || e.getCode() == KeyCode.D)) {

                KeyCode k = e.getCode();
                switch (k) {
                    case D:
                    case RIGHT:
                        ps.rotate(0);
                        break;
                    case S:
                    case DOWN:
                        ps.rotate(90);
                        break;
                    case A:
                    case LEFT:
                        ps.rotate(180);
                        break;
                    case W:
                    case UP:
                        ps.rotate(270);
                        break;
                    default:
                        break;
                }
            }
            e.consume();

        });
    }

    private void pauseEvent() {
        pane.getScene().setOnMouseClicked(e -> {
            if (!isDead) {
                if (t.getStatus() == Status.RUNNING)
                    timeLineController(2);
                else
                    timeLineController(1);
            }
        });
    }

    private void addFood() {
        if (!pane.getChildren().contains(foodItem)) {
            foodItem = new Circle(25, Color.RED);
            int yCoord = ((int) (Math.random() * pane.getHeight()) + 25);
            yCoord = yCoord - (yCoord % 50);
            int xCoord = ((int) (Math.random() * pane.getWidth()) + 25);
            xCoord = xCoord - (xCoord % 50);

            foodItem.setCenterY(yCoord);
            foodItem.setStyle("-fx-stroke: white; -fx-stroke-width: 3;");

            foodItem.setCenterX(xCoord);
            foodBounds = foodItem.getBoundsInParent();
            pane.getChildren().add(foodItem);
        }
    }

    private void addEnemy() {
        if (counter++ % 500 == 0) {
            snake s = new snake(pane);
            enemies.add(s);
            pane.getChildren().add(s);
        }
    }

    private void death() {
        isDead = true;
        timeLineController(3);
        counter = 0;
        startText.setFont(Font.font(16));
        startText.setPrefWidth(pane.getWidth());
        startText.setPrefHeight(pane.getHeight());
        startText.setText(score.saveScore() + "\n Smelltu hér til að byrja annann leik");
        pane.getChildren().add(startText);
    }

}