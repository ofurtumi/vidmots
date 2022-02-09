package files.vidmot;

import java.net.URL;
import java.util.ResourceBundle;

import files.vinnsla.Blackjack;
import files.vinnsla.Leikmadur;
import files.vinnsla.SpilV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;


public class SpilController implements Initializable{
    private Blackjack bj;
    private String[] nofn;
    private Leikmadur p = new Leikmadur("Player", 0);
    private Leikmadur d = new Leikmadur("Dealer", 1);

    private final Image[] myndir = new Image[4];

    private void lesaMyndir() {
        for (int i=1; i<myndir.length+1; i++) {
            String nafn = "myndir/s" + i + ".png";
            myndir[i-1]=new Image(Spil.class.getResourceAsStream(nafn));
        }
    }

    @FXML
    private Label playerName;
    
    @FXML
    private Label dealerName;

    @FXML
    private VBox holder;

    @FXML Button startButton;

    @FXML
    private Label playerLabel;

    @FXML
    private GridPane cardHolder;

    @FXML
    public void draw() {
        bj.dragaSpil(p,d);
        showHands();
        if (bj.isLastRound() || bj.ehvBuinn(p, d)) {
            baraGodur();
        }

    }

    public void showHands() {
        playerLabel.setText(nofn[0] + " " + p.getSamtals() + ", " + nofn[1] + " " + d.getSamtals());
        int i = 0;
        for (SpilV ps : p.getHand()) {
            if (ps != null) {
                Spil s = new Spil();
                int teg = bj.tegundToInt(ps.getTegund());
                s.setSpil(myndir[teg], ps.getVirdi());
                cardHolder.add(s, i+1, 1);
                i++;
            }
        }
        i = 0;
        for (SpilV ds : d.getHand()) {
            if (ds != null) {
                Spil s = new Spil();
                int teg = bj.tegundToInt(ds.getTegund());
                s.setSpil(myndir[teg], ds.getVirdi());
                cardHolder.add(s, i+1, 0);
                i++;
            }
        }
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
            winLabel.setText(nofn[winner.getIndex()] + " er sigurvegari");
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
        cardHolder.getChildren().removeAll(cardHolder.getChildren());
        Label plalli = new Label();
        plalli.setText(nofn[0]);
        Label dilli = new Label();
        dilli.setText(nofn[1]);
        cardHolder.add(plalli, 0, 1);
        cardHolder.add(dilli, 0, 0);
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
        LeikmadurDialogPane d = new LeikmadurDialogPane();
        nofn = d.hvadHeitaLeikmenn();
        if (nofn != null) {
            System.out.println(nofn[0] + " og " + nofn[1] + " ætla að spila");
            playerName.setText(nofn[0]);
            dealerName.setText(nofn[1]);
        }
        else {
            System.out.println("Leikmenn hættu við");
            System.exit(0);
        }
        bj = new Blackjack();
        lesaMyndir();
    }
}