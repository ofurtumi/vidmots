package files.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        Parent menu = loaderMenu.load();
        stage.setTitle("Snake!");
        Scene scene = new Scene(menu, 1024, 1024);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}