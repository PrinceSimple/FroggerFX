package model;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends BaseGameObject {

    private final String img_url = "images/frog_50_38_lila.png";
    public Avatar avatar = new Avatar("images/frog_50_38_lila.png");
    private double x_coordinate;
    private double y_coordinate;
    private Image img;

        public Player(String img_url) {
            super(375,0,50,50);
            this.img = new Image(img_url);
        }

        public void move(double x, double y) {

        }

}



