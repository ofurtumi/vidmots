package hi.verkefni.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BingoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BingoApplication.class.getResource("bingo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 650);
        scene.getRoot().requestFocus();
        stage.setTitle("BINGÓ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}