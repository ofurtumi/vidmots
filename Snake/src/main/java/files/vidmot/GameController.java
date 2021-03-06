package files.vidmot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import files.vinnsla.Map;
import files.vinnsla.Score;
import files.vinnsla.Stats;

public class GameController {
    @FXML
    private Pane pane;

    @FXML
    private Button startButton;

    private Score score = new Score();
    private Label scoreLabel = new Label(score.getScoreString());

    private ImageView foodItem;
    private Bounds foodBounds;

    private KeyFrame k;
    private Timeline t;

    private ArrayList<snake> enemies = new ArrayList<snake>();

    private playerSnake ps;
    private boolean isDead;

    private boolean notPressedThisFrame;

    private Stats stats;
    private Stats thisGame;

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
            pane.getChildren().add(ps.addTailPiece());
            score.plus();
            stats.setApples(1);
            thisGame.setApples(1);
            scoreLabel.setText(thisGame.toString());
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
        try {
            stats = new Stats();
        } catch (Exception e) {
            System.out.println("new Stats() --> " + e);
        }

        thisGame = new Stats(true);

        map = new Map();
        isDead = false;
        pane.getChildren().clear();

        scoreLabel.setFont(Font.font(64));
        scoreLabel.setTranslateX(512);
        scoreLabel.setTranslateY(512);
        scoreLabel.setVisible(false);

        enemies.clear();
        edges.clear();
        addEnemy();
        stats.setGames(1);
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
                    scoreLabel.setVisible(true);
                } else {
                    scoreLabel.setVisible(false);
                    timeLineController(1);
                }
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
            int yCoord = ((int) ((64 + (Math.random() * 832)) / 32)) * 32;
            int xCoord = ((int) ((64 + (Math.random() * 832)) / 32)) * 32;

            foodItem.setX(yCoord);
            foodItem.setY(xCoord);
            foodBounds = foodItem.getBoundsInParent();
            pane.getChildren().add(foodItem);
        }

        // FoodItem fi = new FoodItem();
        // fi.setX(600);
        // fi.setY(600);
        // pane.getChildren().add(fi);
    }

    private void addEnemy() {
        if (counter++ % 250 == 0) {
            stats.setEnemies(1);
            thisGame.setEnemies(1);
            scoreLabel.setText(thisGame.toString());
            snake s = new snake();
            enemies.add(s);
            s.firstTail();
            pane.getChildren().addAll(s.getSprites());
        }
    }

    private void death() {
        stats.saveStats();
        isDead = true;
        ps.death();
        timeLineController(3);
        counter = 0;

        // * pásar í smá til þess að gefa notanda sýn yfir það hvað drap
        // * stundum sést það ekki þannig bara best að beila
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e1) {
        // // TODO Auto-generated catch block
        // e1.printStackTrace();
        // }

        Stage thisStage = (Stage) pane.getScene().getWindow();
        FXMLLoader death = new FXMLLoader(getClass().getResource("death-view.fxml"));
        try {
            Scene scene = new Scene(death.load());
            DeathController DC = death.getController();
            DC.sendData(score);
            thisStage.setScene(scene);
        } catch (Exception e) {
            System.out.println("death scene --> " + e);
        }
    }

    // @FXML
    // private void initialize() {
    // start();
    // }
}