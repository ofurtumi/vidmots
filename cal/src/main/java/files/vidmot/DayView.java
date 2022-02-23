/**
 * @ Author: Þorvaldur Tumi Baldursson
 * @ Create Time: 2022-02-21 11:25:10
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-23 11:48:37
 * @ Description: Sér um viðmótið fyrir einstakan dag
 */

package files.vidmot;

import java.io.Console;
import java.io.IOException;
import java.util.Iterator;

import files.vinnsla.Day;
import files.vinnsla.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class DayView extends VBox{
    private Day day;
    
    @FXML private Label dateLabel;
    @FXML private ScrollPane scrollpane;
    @FXML AnchorPane eventHolder;

    public DayView(Day day) {
        this.day = day;
        getView();
        this.dateLabel.setText(this.day.getDate().toString());
        scrollpane.setContent(eventHolder);
        addEvents();
    }

    private void addEvents() {
        // scrollpane = new ScrollPane(eventHolder);
        // this.getChildren().add(scrollpane);
        // ScrollPane scroll = new ScrollPane(eventHolder);


        Iterator events = this.day.iterator();

        while (events.hasNext()) {
            Event e = (Event) events.next();
            // showEvent(new EventView(e));
            EventView ev = new EventView(e);
            this.eventHolder.getChildren().add(ev);
            ev.move();
        }
    }

    public void doubleClickHandler(MouseEvent e) {
        if (e.getClickCount() == 2) {
            System.out.println("Nýr viðburður");
            EventDialog ed = new EventDialog(new Event(this.day.getDate(), 8, "Nýr viðburður", "Hér kemur lýsing fyrir nýja viðburðinn"));
            // System.out.print(ed.getChangedEvent());
            // this.event = ed.getChangedEvent() != null ? ed.getChangedEvent() : this.event;
            // this.event.setStart(9);
            // this.event.setTitle("Nýr title");
            this.day.addEvent(ed.getChangedEvent());
            e.consume();
            addEvents();
        }
    }

    private void getView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("day-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("Ehv klikkaði við að loada day-view.fxml, error: " + exception);
        }
    }

    public void showEvent(EventView e) {
        this.eventHolder.getChildren().add(e);
        getScene().getRoot().layout();
    }
}
