package files.vidmot;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class playerSnake extends snake {
    private ArrayList<Rectangle> snakePieces = new ArrayList<Rectangle>();

    public playerSnake(Pane p) {
        super(p);
        this.setWidth(50);
        this.setHeight(50);
        this.setFill(Color.BLUE);
        this.setX(50);
        this.setY(100);
        this.setStyle("-fx-stroke: white; -fx-stroke-width: 3;");
        snakePieces.add(this);
    }

    public void rotate(int direction) {
        this.setRotate(direction);
    }

    public Rectangle addTailPiece() {
        snakePieces.add(new Rectangle(50, 50));
        Rectangle piece = snakePieces.get(snakePieces.size() - 1);
        piece.setX(snakePieces.get(snakePieces.size() - 2).getX());
        piece.setY(snakePieces.get(snakePieces.size() - 2).getY());
        piece.setStyle("-fx-stroke: white; -fx-stroke-width: 3;");
        // pane.getChildren().add(piece);
        return piece;
    }

    public void tailMover() {
        for (int i = 1; i < snakePieces.size(); i++) {
            Rectangle piece = snakePieces.get(i);
            Rectangle parent = snakePieces.get(i - 1);
            int parentRotation = (int) parent.getRotate();
            piece.setRotate(parentRotation);
            switch (parentRotation) {
                case 0:
                    piece.setX(parent.getX() - 50);
                    piece.setY(parent.getY());
                    break;
                case 90:
                    piece.setY(parent.getY() - 50);
                    piece.setX(parent.getX());
                    break;
                case 180:
                    piece.setX(parent.getX() + 50);
                    piece.setY(parent.getY());
                    break;
                case 270:
                    piece.setY(parent.getY() + 50);
                    piece.setX(parent.getX());
                    break;
                default:
                    break;
            }
        }
    }

}
