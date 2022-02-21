/**
 * @ Author: Þorvaldur Tumi Baldursson 
 * @ Create Time: 2022-02-21 10:24:40
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-21 12:26:58
 * @ Description: Klasi sem sér um að tengja vinnsluklasa og notendaviðmót
 */

package files.vidmot;

import files.vinnsla.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void showEvents(ActionEvent actionEvent) {
        for (Event e in events) {
            showEvent(e);
        }
    }
}