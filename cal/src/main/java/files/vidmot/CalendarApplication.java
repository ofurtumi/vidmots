/**
 * @ Author: Þorvaldur Tumi Baldursson
 * @ Create Time: 2022-02-21 10:24:40
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-23 15:01:23
 * @ Description: Main klasinn fyrir verkefnið
 */

package files.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalendarApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalendarApplication.class.getResource("calendar-main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 500);
        scene.setUserData(fxmlLoader.getController());
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}