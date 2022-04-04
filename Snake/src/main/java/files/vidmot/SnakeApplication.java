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
        nyrGluggi(stage, menu, "Snake Menu!");
        // Controller menuController = loaderMenu.getController();

        // FXMLLoader loaderGame = new FXMLLoader(getClass().getResource("game-view.fxml"));
        // Parent game = loaderGame.load();
        // new Scene(game, 1024, 1024);
        // Controller gameController = loaderGame.getController();
    }

    private void nyrGluggi(Stage s, Parent root, String tit) {
        s.setTitle(tit);
        Scene scene = new Scene(root, 1024, 1024);
        s.setScene(scene);
        s.show();
    }

    public static void main(String[] args) {
        launch();
    }
}