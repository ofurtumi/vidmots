/**
 * @ Author: Þorvaldur Tumi Baldursson
 * @ Create Time: 2022-02-23 10:46:16
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-23 11:50:45
 * @ Description:   Heldur utan um dialog til að bæði smíða og breyta eventum
 */

package files.vidmot;

import java.io.IOException;
import java.util.Optional;

import files.vinnsla.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar;

public class EventDialog extends DialogPane {

    private final String NAFNFORRITS = "VIÐBURÐUR";
    private final String TILKYNNING = "Hvað heitið þið?";
    private final String SPURNING_SPILARI = "Nafn spilara...";
    private final String SPURNING_DEALER = "Nafn dealers...";
    private final String KOMID = "Komið!";
    private final String HAETTA = "Hætta við";

    private Event event;

    @FXML
    private Label eventDate;
    @FXML
    private TextField eventTitle;
    @FXML
    private TextField eventDescription;
    @FXML
    private ComboBox eventStartHour;
    @FXML
    private ButtonType okButton;

    public EventDialog(Event event) {
        this.event = event;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("event-dialog-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.eventDate.setText(this.event.getDate().toString());
        this.eventTitle.setText(this.event.getTitle());
        this.eventDescription.setText(this.event.getDescription());

        eventStartHour.getItems().addAll(8, 9, 10, 11, 12, 13, 14);
        eventStartHour.setValue(this.event.getStart());
    }

    private void lesaPane() {

    }

    public Event getChangedEvent() {
        Dialog<ButtonType> d = new Dialog<>();
        d.setDialogPane(this);

        iLagiRegla(lookupButton(okButton));

        Event r = getEvent(d);
        return r;
    }

    private void iLagiRegla(Node ilagi) {
        ilagi.disableProperty().bind(eventTitle.textProperty().isEmpty());
    }

    private Event getEvent(Dialog<ButtonType> d) {
        Optional<ButtonType> utkoma = d.showAndWait();
        if (utkoma.isPresent() && (utkoma.get().getButtonData() == ButtonBar.ButtonData.OK_DONE)) {
            this.event.setTitle(eventTitle.getText());
            this.event.setDescription(eventDescription.getText());
            this.event.setStart((int) eventStartHour.getValue());
            System.out.println("Tokst!!");
            return event;
        }
        return null;
    }
}