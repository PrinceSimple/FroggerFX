package model;

public class Obstacle extends BaseGameObject {

    protected double speed;

    public Obstacle(double x, double y, int width, double speed, String imgPath)
        {
            super(x, y, width, 50, imgPath);
            this.speed = speed;
        }

    public void update()
    {
        if(getX() > 750) {
            setX(-50);
        } else if (getX() < -50) {
            setX(750);
        } else {
            moveX(speed);
        }
    }
}