/**
 * @ Author: Þorvaldur Tumi Baldursson
 * @ Create Time: 2022-02-21 11:47:55
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-21 12:25:18
 * @ Description: Heldur utan um viðmót fyrir viðburði
 */

package files.vidmot;

import java.io.IOException;

import files.vinnsla.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class EventView extends StackPane{
    private Event event;

    public EventView(Event e) {
        getView();
        event = e;
    }

    private void getView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("event-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("Ehv klikkaði við að loada event-view.fxml, error: " + exception);
        }
    }

    public void doubleClickHandler(MouseEvent e) {
        if (e.getClickCount() == 2) {
            System.out.println("Breyta viðburði");
            event.setStart(12);
            move();
            e.consume();
        }
    }

    public void move() {
        double y = (getHeight()+1) * event.getStart();
        setTranslateY(y);
    }
}
