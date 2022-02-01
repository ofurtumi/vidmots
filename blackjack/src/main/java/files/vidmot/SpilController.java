package files.vidmot;

import java.net.URL;
import java.util.ResourceBundle;

import files.vinnsla.Blackjack;
import files.vinnsla.Leikmadur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;


public class SpilController implements Initializable{
    private Blackjack bj;
    private Leikmadur p = new Leikmadur("Player");
    private Leikmadur d = new Leikmadur("Dealer");

    @FXML
    private VBox holder;

    @FXML Button startButton;

    @FXML
    private Label playerLabel;

    @FXML
    public void draw() {
        bj.dragaSpil(p,d);
        showHands();
        if (bj.isLastRound() || bj.ehvBuinn(p, d)) {
            baraGodur();
        }

    }

    public void showHands() {
        playerLabel.setText("p: " + p.getSamtals() + ", d: " + d.getSamtals());
    }

    public void baraGodur() {
        Button playerB = (Button)holder.lookup("#playerButton");
        Button doneB = (Button)holder.lookup("#doneButton");

        holder.getChildren().removeAll(playerB,doneB);
        bj.isDealerDone(d,p);
        showHands();
        Label winLabel = new Label();
        System.out.println(p.hvorVann(d));
        if (p.hvorVann(d) != null) {
            Leikmadur winner = p.hvorVann(d);
            winLabel.setText(winner.getNafn() + " er sigurvegari");
        }
        else {
            winLabel.setText("Enginn vann :(");
        }

        Button restartButton = new Button();
        restartButton.setText("Nýr leikur!");
        restartButton.setId("restartButton");
        restartButton.addEventHandler(ActionEvent.ACTION, e -> {restart(restartButton, winLabel);});

        holder.getChildren().addAll(winLabel,restartButton);
    }

    public void startGame() {
        bj.nyrLeikur(p,d);
        holder.getChildren().remove(startButton);
        playerTakki();
        doneTakki();
        draw();
        draw();
        showHands();
    }

    public void playerTakki() {
        Button playerButton = new Button();
        playerButton.setText("Draga spil");
        playerButton.setId("playerButton");
        playerButton.addEventHandler(ActionEvent.ACTION, e -> {draw();bj.newRound();});
    
        holder.getChildren().add(playerButton);
    }

    public void doneTakki() {
        Button doneButton = new Button();
        doneButton.setText("Komið nóg!");
        doneButton.setId("doneButton");
        doneButton.addEventHandler(ActionEvent.ACTION, e -> {baraGodur();});
        
        holder.getChildren().add(doneButton);
    }

    public void restart(Button rb, Label wl) {
        holder.getChildren().removeAll(rb,wl);
        bj.nyrLeikur(p,d);
        System.out.println(holder.getChildren());
        playerLabel.setText("");
        playerTakki();
        doneTakki();
        draw();
        draw();
        showHands();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bj = new Blackjack();
    }
}