package model;

public class Obstacle extends BaseGameObject {

    public double speed;
    private boolean isDeadly;
    private double width;
    public boolean occupied = false;

    public Obstacle(double x, double y, double width, double speed, boolean deadly, String imgPath)
        {
            super(x, y, width, 50, imgPath);
            this.speed = speed;
            this.isDeadly = deadly;
            this.width = width;
        }

    public void update()
    {
        if(getX() > 700 + this.w) {
            setX(-this.w);
        } else if (getX() < -this.w) {
            setX(700 + this.w);
        } else {
            moveX(speed);
        }
    }
    public boolean isDeadly() {
        return isDeadly;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public double getWidth() {
        return width;
    }
}
