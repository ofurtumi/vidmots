/**
 * @ Author: Þorvaldur Tumi Baldursson
 * @ Create Time: 2022-02-21 11:47:55
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-23 14:24:12
 * @ Description: Heldur utan um viðmót fyrir viðburði
 */

package files.vidmot;

import java.io.IOException;
import java.time.LocalDate;

import files.vinnsla.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class EventView extends AnchorPane{
    private Event event;
    @FXML private Label title;
    @FXML private Label time;
    @FXML private Label description;
    @FXML private ContextMenu contextmenu;

    public EventView(Event e) {
        getView();
        this.event = e;
        setTitle();
        setTime();
        setDescription();
    }

    public LocalDate getDate() {
        return this.event.getDate();
    }

    public Event getEvent() {
        return this.event;
    }

    private void setTitle() {
        this.title.setText(event.getTitle());
    }

    private void setTime() {
        this.time.setText(event.getStart()+":00");
    }

    private void setDescription() {
        this.description.setText(event.getDescription());
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

    public void deleteMenu(ContextMenuEvent ce) {
        contextmenu.show(this, ce.getScreenX(), ce.getScreenY());
    }

    public void deleteEvent(ActionEvent ae) {
        MainController controller = (MainController) getScene().getUserData();
        System.out.println(controller);
        System.out.println(this);
        controller.deleteEvent(this);
        System.out.println("Viðburði eytt!");
    }

    public void doubleClickHandler(MouseEvent e) {
        if (e.getClickCount() == 2) {
            System.out.println("Breyta viðburði");
            EventDialog ed = new EventDialog(this.event);
            this.event = ed.getChangedEvent();
            // System.out.print(ed.getChangedEvent());
            // this.event = ed.getChangedEvent() != null ? ed.getChangedEvent() : this.event;
            // this.event.setStart(9);
            // this.event.setTitle("Nýr title");
            updateEvent();
            move();
            e.consume();
        }
    }

    private void updateEvent() {
        setTitle();
        setTime();
        setDescription();
    }

    public void move() {
        double y = 100 * (event.getStart() - 8);
        setTranslateY(y);
    }
}
