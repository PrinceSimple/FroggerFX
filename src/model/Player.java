package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class Player extends BaseGameObject {
    public SimpleIntegerProperty lives = new SimpleIntegerProperty(5);
    public SimpleIntegerProperty score = new SimpleIntegerProperty(0);
    private String imgPath;
    public ColorAdjust colorAdjust = new ColorAdjust();

    public Player(int id, double x, double y) {

        super(x, y, 50, 50, "assets/frog.png");
        colorAdjust.setHue(-0.01);
        //System.out.print(this.img);
        //setImageTint(colorAdjust);
    }

    public void reset(double x, double y) {
        setX(x);
        setY(y);
    }

    public void die() {

    }

}



