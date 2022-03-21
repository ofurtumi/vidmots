package files.vidmot;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class snake extends ImageView { 
    // * breytti úr extends rectangle yfir í extends ImageView 
    // * fyrir auðveldara aðgengi að sprites
    protected ArrayList<Rectangle> snakePieces = new ArrayList<Rectangle>();
    protected ArrayList<ImageView> snakeSprites = new ArrayList<ImageView>();

    protected Image[] imgs = new Image[5];

    private int counter = 0;

    protected int velX = 1;
    protected int velY = 0;

    // ! Tekið út skjár verður bara 1024*1024 í prod
    // ! private Pane pane;
    // þarf að halda utan um pane til að geta farið í gegn um vegg
    // eftir að skjárinn er stækkaður

    public snake() {
        getImages();

        this.setImage(imgs[0]); 
        this.setFitWidth(32);
        this.setFitHeight(32);
        this.setX(256);
        this.setY(256);
        this.snakeSprites.add(this);
    }

    /**
     * ? gets sprite images from resource folder 
     * * head:  0
     * * body:  1
     * * left:  2
     * * right: 3
     * * tail:  4
     */
    private void getImages() {
        for (int i = 0; i < 5; i++) {
            imgs[i] = new Image(snake.class.getResourceAsStream("imgs/ps"+(i+1)+".png"));
        }
    }

    public ArrayList<Rectangle> getPieces() {
        return this.snakePieces;
    }

    public ArrayList<ImageView> getSprites() {
        return this.snakeSprites;
    }

    public void moveRandom() {
        if (counter++ == 50) {
            rotateRandom();
            counter = 0;
        }
        if (counter % 2 == 0) {
            tailMover();
            move();
        }
    }

    public void firstTail() {
        this.addTailPiece();
        this.addTailPiece();
    }

    public ImageView addTailPiece() {
        ImageView piece = new ImageView(imgs[4]);
        piece.setFitWidth(32);
        piece.setFitHeight(32);
        snakeSprites.add(piece);

        ImageView parent = snakeSprites.get(snakeSprites.size() - 2);
        piece.setX(parent.getX());
        piece.setY(parent.getY());
        if (snakeSprites.size() != 2) parent.setImage(imgs[1]);
        // ! afhverju í fokkanum snýr rassinn ekki rétt í byrjun???
        // todo laga það shit
        return piece;
    }

    public void tailMover() {
        for (int i = snakeSprites.size() - 1; i > 0; i--) {
            snakeSprites.get(i).setX(snakeSprites.get(i - 1).getX());
            snakeSprites.get(i).setY(snakeSprites.get(i - 1).getY());
        }
    }

    public void move() {
        if (this.velX == 1 && this.getX() == 992)
            this.setX(0);
        else if (this.velX == -1 && this.getX() == 0)
            this.setX(992);
        else
            this.setX(this.getX() + (32 * velX));

        if (this.velY == 1 && this.getY() == 992)
            this.setY(0);
        else if (this.velY == -1 && this.getY() == 0)
            this.setY(992);
        else
            this.setY(this.getY() + (32 * velY));

        snakeSprites.get(0).setX(this.getX());
        snakeSprites.get(0).setY(this.getY());

        updateSprites();
    }

    private void updateSprites() {
        if (velX != 0) {
            if (velX > 0) snakeSprites.get(0).setRotate(0);
            else snakeSprites.get(0).setRotate(180);
        }
        else if (velY > 0) snakeSprites.get(0).setRotate(90);
        else snakeSprites.get(0).setRotate(270);

        

        for (int i = 1; i < snakeSprites.size()-1; i++) {
            ImageView p = snakeSprites.get(i-1);
            ImageView t = snakeSprites.get(i);
            ImageView c = snakeSprites.get(i+1);

            if (p.getX() == t.getX()) {
                if (p.getY() < t.getY()) {
                    if (c.getX() == t.getX())       {t.setImage(imgs[1]); t.setRotate(0);}
                    else if (c.getX() < t.getX())   {t.setImage(imgs[2]); t.setRotate(90);}
                    else                            {t.setImage(imgs[3]); t.setRotate(270);}
                }
                else {
                    if (c.getX() == t.getX())       {t.setImage(imgs[1]); t.setRotate(180);}
                    else if (c.getX() < t.getX())   {t.setImage(imgs[2]); t.setRotate(0);}
                    else                            {t.setImage(imgs[3]); t.setRotate(0);}
                }
            }
            else if (p.getX() < t.getX()) {
                if (c.getX() == t.getX())           {
                    if (c.getY() < t.getY())        {t.setImage(imgs[2]); t.setRotate(90);}
                    else                            {t.setImage(imgs[2]); t.setRotate(0);}
                }
                else                                {t.setImage(imgs[1]); t.setRotate(90);}
            }
            else {
                if (c.getX() == t.getX()) {
                    if (c.getY() < t.getY())        {t.setImage(imgs[3]); t.setRotate(270);}
                    else                            {t.setImage(imgs[3]); t.setRotate(0);}
                }
                else                                {t.setImage(imgs[1]); t.setRotate(270);}
            }
        }

        ImageView p = snakeSprites.get(snakeSprites.size()-2);
        ImageView t = snakeSprites.get(snakeSprites.size()-1);

        if (p.getY() < t.getY())        {t.setRotate(180);}
        else if (p.getY() > t.getY())   {t.setRotate(0);}
        else if (p.getX() < t.getX())   {t.setRotate(90);}
        else                            {t.setRotate(270);}
    }

    private void rotateRandom() {
        int direction = (int) (Math.random() * 2);
        switch (direction) {
            case 0:
                if (this.velX != 0) {
                    this.velX = 0;
                    this.velY = 1;
                } else {
                    this.velX = 1;
                    this.velY = 0;
                }
                break;
            case 1:
                if (this.velX != 0) {
                    this.velX = 0;
                    this.velY = -1;
                } else {
                    this.velX = -1;
                    this.velY = 0;
                }
                break;
            default:
                break;

        }
    }
}
