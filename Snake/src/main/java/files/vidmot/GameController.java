package files.vidmot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import files.vinnsla.Map;
import files.vinnsla.Score;

public class GameController {
    @FXML
    private Pane pane;

    // @FXML
    // private Label startText;

    private Score score = new Score();
    private Label scoreLabel = new Label(score.getScore());

    private ImageView foodItem;
    private Bounds foodBounds;

    private KeyFrame k;
    private Timeline t;

    private ArrayList<snake> enemies = new ArrayList<snake>();

    private playerSnake ps;
    private boolean isDead;

    private boolean notPressedThisFrame;

    private Map map;

    private int counter = 0;

    Image apple = new Image(GameController.class.getResourceAsStream("imgs/apple.png"));

    private ArrayList<ImageView> edges = new ArrayList<>();

    /**
     * handler fyrir startbutton, gefur leikmanni smá tíma til að byrja leikinn
     * auk þess sem nær í scene sem er nauðsynlegt að hafa í start til að finna
     * staðsetningu veggjanna
     * 
     * @param event músasmell
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        // pane.getChildren().remove(startText);
        start();
    }

    /**
     * býr til tímalínu fyrir "leikja lykkjuna"
     * endar á að kalla á timeLineController sem er fallið sem
     * heldur utan um stöðu, [PLAY,PAUSE,STOP], leiksins
     * ! uppfæra tímalínu
     * ? Athuga stöður á kortinu útfrá reiknuðum, en ekki sýndum, stöðum ->
     * ? Ef allt er í góðu, teikna útfrá mappinu og hreinsa svo mappið ->
     * ? Ef ekki þá er dauði -> Uppfæra stig -> Reikna stöður fyrir nýja |
     */
    private void createTimeline() {
        k = new KeyFrame(Duration.millis(100), e -> {
            notPressedThisFrame = true;
            addFood();
            addEnemy();
            movePlayer();
            if (enemies.size() > 0)
                moveEnemies();
            intersectChecks();
            frontEdges();
            // updateMap();
        });
        t = new Timeline(k);
        t.setCycleCount(Timeline.INDEFINITE);

        timeLineController(1);
    }

    private void frontEdges() {
        for (ImageView i : edges) {
            i.toFront();
        }
    }

    // ! nenni þessu eiginlega ekki, ekki viss um að það þurfi þannig beila??
    private void updateMap() {
        map.clearMap();

        ArrayList<ImageView> psP = ps.getSprites();
        for (int i = 0; i < psP.size(); i++) {
            if (i == 0)
                map.setMapSquare(1, (int) psP.get(i).getX() / 32, (int) psP.get(i).getY() / 32);
            else if (i == psP.size() - 1)
                map.setMapSquare(3, (int) psP.get(i).getX() / 32, (int) psP.get(i).getY() / 32);
            else
                map.setMapSquare(2, (int) psP.get(i).getX() / 32, (int) psP.get(i).getY() / 32);
        }

        for (snake s : enemies) {
            ArrayList<ImageView> sprites = s.getSprites();
            for (int i = 0; i < sprites.size(); i++) {
                int x = (int) (sprites.get(i).getX() / 32);
                int y = (int) (sprites.get(i).getY() / 32);

                map.setMapSquare(4 + i, x, y);
            }
        }

        map.setMapSquare(7, (int) (foodItem.getX() / 32), (int) (foodItem.getY() / 32));
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
        ps.tailMover();
        ps.move();
        ps.moveHitbox();
    }

    /**
     * örfall til að athuga hvort snákur snerti mat
     * ef hann snertir mat, hækka stigin um 1,
     * drepa núverandi mat og bæta við nýjum halabút
     */
    private void intersectChecks() {
        if (ps.getHitbox().intersects(foodBounds)) {
            pane.getChildren().remove(foodItem);
            // System.out.println("nammi namm :)~");
            pane.getChildren().add(ps.addTailPiece());
            score.plus();
            scoreLabel.setText(score.getScore());
        }

        for (int i = 1; i < ps.getSprites().size(); i++) {
            Bounds sprite = ps.getSprites().get(i).getBoundsInParent();
            if (ps.getHitbox().intersects(sprite)) {
                death();
            }
        }
    }

    /**
     * loopar yfir enemies listann og kallar á moveRandom() fyrir hvern og einn
     * athugar líka hvort player og enemy rekast á, ef svo kallar á death()
     */
    private void moveEnemies() {
        Circle hb = ps.getHitbox();
        for (snake enemy : enemies) {
            if (enemy == null)
                continue;
            enemy.moveRandom();
            for (ImageView piece : enemy.getSprites()) {
                if (hb.intersects(piece.getBoundsInParent())) {
                    death();
                    break;
                }
            }
        }
    }

    /**
     * mikilvægasta fallið, frumstillir allt, hreinsar skjáinn,
     * eyðir öllum óvinum, bætir við einum óvini, býr til nýjann playerSnake
     */
    public void start() {

        map = new Map();
        isDead = false;
        pane.getChildren().clear();
        enemies.clear();
        edges.clear();
        addEnemy();
        ps = new playerSnake();
        ps.firstTail();

        pane.getChildren().add(scoreLabel);
        pane.getChildren().addAll(ps.getSprites());
        // pane.getChildren().addAll(ps.getHitbox());
        createEdges();

        createTimeline();

        pane.getScene().setOnKeyPressed(e -> {
            if (notPressedThisFrame && t.getStatus() == Status.RUNNING
                    && (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN ||
                            e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.W
                            || e.getCode() == KeyCode.A || e.getCode() == KeyCode.S || e.getCode() == KeyCode.D)) {

                notPressedThisFrame = false;
                KeyCode k = e.getCode();
                ps.turn(k);
            }

            if (!isDead && e.getCode() == KeyCode.ESCAPE) {
                if (t.getStatus() == Status.RUNNING) {
                    timeLineController(2);
                    System.out.println(map.toString());
                } else
                    timeLineController(1);
            }
            e.consume();

        });
    }

    private void createEdges() {
        Image side = new Image(GameController.class.getResourceAsStream("imgs/edge-m.png"));
        for (int j = 0; j < 30; j++) {
            ImageView emb = new ImageView(side);
            emb.setX(32 + (j * 32));
            emb.setY(992);
            edges.add(emb);
        }
        for (int j = 0; j < 30; j++) {
            ImageView emb = new ImageView(side);
            emb.setX(32 + (j * 32));
            emb.setY(0);
            emb.setRotate(180);
            edges.add(emb);
        }
        for (int j = 0; j < 30; j++) {
            ImageView emb = new ImageView(side);
            emb.setX(0);
            emb.setY(32 + (j * 32));
            emb.setRotate(90);
            edges.add(emb);
        }
        for (int j = 0; j < 30; j++) {
            ImageView emb = new ImageView(side);
            emb.setX(992);
            emb.setY(32 + (j * 32));
            emb.setRotate(270);
            edges.add(emb);
        }
        Image corner = new Image(GameController.class.getResourceAsStream("imgs/edge-e.png"));
        for (int i = 0; i < 4; i++) {
            ImageView ec = new ImageView(corner);
            ec.setX(i < 2 ? 0 : 992);
            ec.setY(i != 0 && i <= 2 ? 992 : 0);
            ec.setRotate(540 - (i * 90));
            edges.add(ec);
        }
        pane.getChildren().addAll(edges);
    }

    private void addFood() {
        if (!pane.getChildren().contains(foodItem)) {
            foodItem = new ImageView(apple);
            int yCoord = ((int) (64 + (Math.random() * 900)));
            yCoord = yCoord - (yCoord % 16);
            int xCoord = ((int) (64 + (Math.random() * 900)));
            xCoord = xCoord - (xCoord % 16);

            foodItem.setX(yCoord);
            foodItem.setY(xCoord);
            foodBounds = foodItem.getBoundsInParent();
            pane.getChildren().add(foodItem);
        }
    }

    private void addEnemy() {
        if (counter++ % 250 == 0) {
            snake s = new snake();
            enemies.add(s);
            s.firstTail();
            // pane.getChildren().addAll(s.getPieces());
            pane.getChildren().addAll(s.getSprites());
        }
    }

    private void death() {
        isDead = true;
        ps.death();
        timeLineController(3);
        counter = 0;

        // startText.setFont(Font.font(16));
        // startText.setPrefWidth(pane.getWidth());
        // startText.setPrefHeight(pane.getHeight());
        // startText.setText(score.saveScore() + "\n Smelltu hér til að byrja annann
        // leik");
        // pane.getChildren().add(startText);

    }

    public void switchScenes(ActionEvent aE) {
        Stage s = (Stage) ((Node) aE.getSource()).getScene().getWindow();
        s.setScene(pane.getScene());
    }
}