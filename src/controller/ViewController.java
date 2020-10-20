package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import model.Player;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import model.Background;

public class ViewController implements Initializable, Observer {
    private GameController gc;
    Canvas canvas = new javafx.scene.canvas.Canvas(750, 750);
    private String[] images = {"bg13.png", "bg12.jpg", "bg11.jpg", "bg10.jpg", "bg9.jpg", "bg8.jpg", "bg7.png",
            "bg6.jpg", "bg5.jpg", "bg4.jpg", "bg3.jpg", "bg2.jpg", "bg1.png"};
    private Player player1;
    @FXML
    Pane boardPanel;

    @FXML
    private GridPane GameBoard;

    public ViewController()  {
        gc = new GameController();
    }


    @FXML
    private void handleKeyPress(KeyEvent e) {
        gc.inputController.handle(e);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for(int col=0;col<1;col++){
            for(int row=0;row<13;row++){
                //Zeilenhöhe auf 50px setzen
                RowConstraints rc = new RowConstraints();
                rc.setMinHeight(50);
                rc.setPrefHeight(50);
                GameBoard.getRowConstraints().addAll(rc);
                // Bildernamen aus dem Array holen und neues Bildobjekt erzeugen
                Image img = new Image("images/" + images[row]);
                //Rectangle rect = new Rectangle(50,50, Color.BLUE);

                //Bilder dem Grid hinzufügen
                GameBoard.add(new Background(img), col, row);
            }
        }
        // Spielfigur erzeugen und Link zum Bild übergeben
        player1 = new Player("images/frog_50_38_lila.png");
        // Spielfigur dem Grid hinzufügen in Spalte 0, Zeile 13
        GameBoard.add(player1.avatar, 0, 0);
        boardPanel.getChildren().add(canvas);
        canvas.getGraphicsContext2D().fillText("Hallo Welt", 325,325);
    }

    public GameController getGameController() {
        return gc;
    }
}
