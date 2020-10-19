package controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.Avatar;
import model.Background;

public class Controller {

    // String Array mit den Bildernamen für den Hintergrund
    private String[] images = {"bg13.png", "bg12.jpg", "bg11.jpg", "bg10.jpg", "bg9.jpg", "bg8.jpg", "bg7.png",
                "bg6.jpg", "bg5.jpg", "bg4.jpg", "bg3.jpg", "bg2.jpg", "bg1.png"};
    // Spielfigur
    private Avatar avatar;

    @FXML
    private GridPane Game;

    public void initialize() {
        //GridPane wird mit 1 Spalte und 13 Zeilen befüllt
        for(int row=0;row<1;row++){
            for(int col=1;col<14;col++){
                //Zeilenhöhe auf 55px setzen
                RowConstraints rc = new RowConstraints();
                rc.setMinHeight(55);
                rc.setPrefHeight(55);
                Game.getRowConstraints().addAll(rc);
                // Bildernamen aus dem Array holen und neues Bildobjekt erzeugen
                Image img = new Image("images/" + images[col-1]);
                //Bilder dem Grid hinzufügen
                Game.add(new Background(img), row, col);
            }
        }
        // Spielfigur erzeugen und Link zum Bild übergeben
        avatar = new Avatar("images/frog_50_38_lila.png");
        // Spielfigur dem Grid hinzufügen in Spalte 0, Zeile 13
        Game.add(avatar, 0, 13);
    }

    // Welche Tasten wurden gedrückt, Aufruf der Bewegungsmethoden des Avatar-Objekts
    public void handleOnKeyPressed(KeyEvent event)    {
        //System.out.println("Pressed key text: " + event.getCode());
        if (event.getCode() == KeyCode.RIGHT){
            avatar.moveRight();
        }
        if (event.getCode() == KeyCode.LEFT){
            avatar.moveLeft();
        }
        if (event.getCode() == KeyCode.UP){
            avatar.moveUp();
        }
        if (event.getCode() == KeyCode.DOWN){
            avatar.moveDown();
        }

    }


}
