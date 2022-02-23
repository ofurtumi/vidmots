/**
 * @ Author: Þorvaldur Tumi Baldursson 
 * @ Create Time: 2022-02-21 10:24:40
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-23 15:02:18
 * @ Description: Klasi sem sér um að tengja vinnsluklasa og notendaviðmót
 */

package files.vidmot;

import java.time.LocalDate;
import java.util.List;

import files.vinnsla.Day;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;

public class MainController {
    @FXML
    private HBox container;

    private final List<Day> days = FXCollections.observableArrayList();
    private final List<LocalDate> dates = FXCollections.observableArrayList();
    private Day currentDay;

    // public void showEvents(ActionEvent actionEvent) {
    //     for (Event e in events) {
    //         DayView.showEvent(e);
    //     }
    // }

    public void dateSelected(ActionEvent selectedDate) {
        DatePicker dpSelectedDate = (DatePicker) selectedDate.getSource();
        LocalDate date = (LocalDate) dpSelectedDate.getValue();
        System.out.println(date);

        if (this.dates.contains(date)) {
            System.out.println("Er hér");
            this.container.getChildren().remove(this.container.getChildren().get(2));
            Day thisDay = this.days.get(this.dates.indexOf(date));
            // thisDay.addEvent(new Event(date, 8+thisDay.getEventNum(), "Test nr: " + thisDay.getEventNum(), "Virka mörg event?"));
            this.container.getChildren().addAll(new DayView(thisDay));
            currentDay = thisDay;
        }
        else {
            if (this.container.getChildren().size() > 2) {
                this.container.getChildren().remove(this.container.getChildren().get(2));
            }
            this.dates.add(date);
            Day newDay = new Day(date);
            // newDay.addEvent(new Event(date,8,"Test event", "Þetta event verður svo mikið test :)"));
            this.container.getChildren().add(new DayView(newDay));
            this.days.add(newDay);
            currentDay = newDay;
        }
    }

    public void deleteEvent(EventView e) {
        currentDay.removeEvent(e.getEvent());
        this.container.getChildren().remove(this.container.getChildren().get(2));
        this.container.getChildren().add(new DayView(currentDay));
    }
}