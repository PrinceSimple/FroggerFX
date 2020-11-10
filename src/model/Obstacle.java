package model;

public class Obstacle extends BaseGameObject {

    protected double speed;

    public Obstacle(double x, double y, int width, double speed)
        {
            super(x, y, width, 50, "images/Car_1.png");
            this.speed = speed;
        }

    public void update()
    {
        if(getX()>700) {
            setX(0);
        } else if (getX()<0) {
            setX(700);
        } else {
            moveX(speed);
        }
    }
}