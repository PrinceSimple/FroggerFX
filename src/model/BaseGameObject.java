package model;

import javafx.scene.shape.Rectangle;

public class BaseGameObject  {
    private Rectangle area;

    public BaseGameObject (double x, double y, double w, double h) {
        area = new Rectangle(x, y, w, h );
    }

    public boolean intersects(BaseGameObject other) {
        return this.area.intersects(other.area.getLayoutBounds());
    }
}
