package model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
// Hintergrundklasse erbt alles von Imageview
public class Background extends ImageView {

    //Konstruktor setzt das übergebende Bild
    public Background(Image img){
        this.setImage(img);
    }
}
