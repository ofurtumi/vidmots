package files.vidmot;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar;


public class LeikmadurDialogPane extends DialogPane{

    private final String NAFNFORRITS = "NAFN";
    private final String TILKYNNING = "Hvað heitið þið?";
    private final String SPURNING_SPILARI = "Nafn spilara...";
    private final String SPURNING_DEALER = "Nafn dealers...";
    private final String KOMID = "Komið!";
    private final String HAETTA = "Hætta við";

    @FXML
    private TextField pNafn;

    @FXML
    private TextField dNafn;

    @FXML
    private ButtonType okButton;

    public LeikmadurDialogPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leikmadur-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void lesaPane() {

    }

    public String[] hvadHeitaLeikmenn() {
        Dialog<ButtonType> d = new Dialog<>();
        d.setDialogPane(this);
        
        iLagiRegla(lookupButton(okButton));
        
        return getNofnLeikmanna(d);
    }

    private void iLagiRegla(Node ilagi) {
        ilagi.disableProperty().bind(pNafn.textProperty().isEmpty().or(dNafn.textProperty().isEmpty()));
    }

    private String[] getNofnLeikmanna(Dialog<ButtonType> d) {
        Optional<ButtonType> utkoma = d.showAndWait();
        if (utkoma.isPresent() && (utkoma.get().getButtonData() == ButtonBar.ButtonData.OK_DONE)) {
            return new String[]{pNafn.getText(),dNafn.getText()};
        }
        return null;
    }
}