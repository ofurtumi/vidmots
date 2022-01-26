package hi.verkefni.vidmot;

import java.net.URL;
import java.util.ResourceBundle;

import hi.verkefni.vinnsla.BingoSpjald;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class BingoController implements Initializable {
    @FXML VBox container;
    @FXML Button byrja;

    private BingoSpjald bSpjald;

    Label win = new Label();

    @FXML
    /**
     * tekur við skilaboði frá "byrja leik" takkanum sem er skilgreindur í fxml skránni og býr til bingóspjald
     */
    private void handle() {
        container.getChildren().remove(byrja);
        GridPane g = new GridPane();
        g.setGridLinesVisible(true);
        g.setAlignment(Pos.CENTER);
        g.maxHeight(600);
        g.minHeight(600);
        g.maxWidth(600);
        g.minWidth(600);

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints(100);
            g.getRowConstraints().add(row);
        }

        for (int i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints(100);
            g.getColumnConstraints().add(column);
        }

        Font font = new Font(45.0);
        g.add(createLabel("B", font), 0, 0);
        g.add(createLabel("I", font), 1, 0);
        g.add(createLabel("N", font), 2, 0);
        g.add(createLabel("G", font), 3, 0);
        g.add(createLabel("O", font), 4, 0);

        font = new Font(34);
        int[][] spjald = bSpjald.nyttSpjald();

        for (int j = 0; j < 5; j++) {
            for (int i = 1; i <= 5; i++) {
                String temp = Integer.toString(spjald[j][i-1]);
                // String temp = j+""+(i-1);
                Button b = new Button(temp);
                b.setContentDisplay(ContentDisplay.CENTER);
                b.setMaxHeight(90);
                b.setMinHeight(90);
                b.setMaxWidth(90);
                b.setMinWidth(90);
                b.setTextAlignment(TextAlignment.CENTER);
                b.setFont(font);
                int y = i-1;
                int x = j;
                b.addEventHandler(ActionEvent.ACTION, event -> changeColor(b,x,y));
                g.add(b, j, i);
                g.setValignment(b, VPos.CENTER);
                g.setHalignment(b, HPos.CENTER);
            }
        }
        container.getChildren().add(g);
        container.getChildren().add(win);
        container.requestFocus();
    }

    /**
     * litar takkann sem tekinn er inn og setur gildi takkans í spjald fylkinu sem true
     * virkjar líka leitarföllin sem athuga hvort það hafi komið bingó við þessa aðgerð
     * @param b Button object, reference á takkann sem ýtt var á
     * @param x x hnit takkans á bingóspjaldinu
     * @param y y hnit takkans á bingóspjaldinu
     */
    private void changeColor(Button b, int x, int y) {
        b.setStyle("-fx-background-color: red");
        container.requestFocus();
        b.setDisable(true);
        bSpjald.aReit(x, y);
        // System.out.println(x+" " + y);
        if(bSpjald.erBingo(x,y)) {
            win.setText("BINGÓ!");
        }
    }


    /**
     * hjálparfall til þess að búa til label fyrir takka
     * @param text  texti sem label inniheldur
     * @param font  Font hlutur sem inniheldur stærð leturs
     * @return      Label hlut
     */
    private Label createLabel(String text, Font font) {
        Label out = new Label(text);
        out.setFont(font);
        out.setAlignment(Pos.CENTER);
        out.setMaxHeight(50);
        out.setMinHeight(50);
        out.setMaxWidth(100);
        out.setMinWidth(100);
        out.setContentDisplay(ContentDisplay.CENTER);
        return out;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bSpjald = new BingoSpjald();
    }
}