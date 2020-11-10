package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class BaseGameObject implements Serializable {
    protected double x;
    protected double y;
    protected double w;
    protected double h;
    protected String imgPath;
    protected ImageView img;

    public BaseGameObject(double x, double y, double w, double h, String imgPath) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.imgPath = imgPath;
    }

    public void move(double x, double y) {
        setX(this.x + x);
        setY(this.y + y);
    }

    public void moveX(double x) {
        setX(this.x + x);
    }

    public boolean intersects(BaseGameObject other) {
        return this.img.intersects(other.img.getLayoutBounds());
    }

    public double getX(){
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
        img.setX(this.x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        img.setY(this.y);
    }

    public void initImage(Pane pane) {
        this.img = new ImageView();
        Image im = new Image(imgPath,(double) w-2,(double) h-2, false, false);
        this.img.setImage(im);
        this.img.setX(this.x);
        this.img.setY(this.y);
        pane.getChildren().add(img);
    }
}
