package model;

public class Player extends BaseGameObject {

    private double row;
    private double column;
    private String imgPath;
    private boolean sitting = false;

    public Player(double x, double y) {
        super(x, y, 50, 50, "assets/animated_example.gif");
    }

    public void reset(double x, double y) {
        setX(x);
        setY(y);
    }

}



