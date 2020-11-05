package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends BaseGameObject {

    public ImageView img;
    public Image image;
    protected double x;
    protected double y;
    protected double row;
    protected double column;
    protected String imgPath;
    protected boolean sitting = false;

    public Player(double x, double y) {
        super(x, y, 50, 50, "images/animated_example.gif");
    }

    public void move(double x, double y) {
        setX(this.x + x);
        setY(this.y + y);
    }

}



