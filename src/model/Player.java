package model;

import javafx.beans.property.SimpleIntegerProperty;

public class Player extends BaseGameObject {

    private int score = 0;
    private String imgPath;
    private Obstacle raft;

    public Player(double x, double y) {
        super(x, y, 50, 50, "assets/animated_example.gif");
    }

    public void reset(double x, double y) {
        setX(x);
        setY(y);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}



