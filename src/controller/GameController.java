package controller;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.Player;
import model.Background;

import java.util.Observable;
import java.util.Observer;

public class GameController extends Observable {

    public InputController inputController = new InputController();
    // String Array mit den Bildernamen für den Hintergrund

    @FXML
    Observer observer;

    public void initialize() {



    }

    private void notifyObserver() {
        if (observer != null) {
            observer.update(this, null);
        }
    }
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

}