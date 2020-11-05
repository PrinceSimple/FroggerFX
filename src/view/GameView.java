package view;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameView  {
    public GameView() {
        for(int col=0;col<13;col++){
            for(int row=0;row<13;row++){
                Rectangle rect = new Rectangle(row * 50, col * 50, 50,50);
                rect.setFill(Color.BLUE);


//                //Zeilenhöhe auf 50px setzen
//                RowConstraints rc = new RowConstraints();
//                rc.setMinHeight(50);
//                rc.setPrefHeight(50);
//                GameBoard.getRowConstraints().addAll(rc);
//                // Bildernamen aus dem Array holen und neues Bildobjekt erzeugen
//                Image img = new Image("images/" + images[row]);
//                //Rectangle rect = new Rectangle(50,50, Color.BLUE);
//
//                //Bilder dem Grid hinzufügen
//                GameBoard.add(new Background(img), col, row);
            }
        }
    }
}
