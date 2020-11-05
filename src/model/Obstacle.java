package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle extends BaseGameObject {

    public ImageView img;
    public Image image;
    protected int x;
    protected String imgPath;
    protected static int direction = 1;
    protected double speed;

    public Obstacle(double y, double speed, int direction)
        {
            super(0, y, 50, 50, "images/Car_1.png");
            this.speed = speed;
            this.direction = direction;
        }

    public void update()
    {
        if(getX()>=700) {
            setX(-700 * direction);
        } else {
            setX(1 * direction * speed);
        }
    }
}