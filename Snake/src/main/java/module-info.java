module files.snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens files.vidmot to javafx.fxml;
    exports files.vidmot;
}