package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class Player extends BaseGameObject {
    public SimpleIntegerProperty lives = new SimpleIntegerProperty(5);
    public SimpleIntegerProperty score = new SimpleIntegerProperty(0);
    public SimpleIntegerProperty highscore = new SimpleIntegerProperty(0);
    public SimpleStringProperty name = new SimpleStringProperty("anonymous");
    public ColorAdjust colorAdjust = new ColorAdjust();

    public Player(int id, double x, double y) {
        super(x, y, 50, 50, "assets/frog.png");
        colorAdjust.setHue(-0.01);
    }

    public void reset(double x, double y) {
        setX(x);
        setY(y);
    }

}



