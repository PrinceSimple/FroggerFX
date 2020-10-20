package controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Player;

public class InputController {
    private Player player1;
    public InputController() {
        //this.player1 = p;
    }

    public void handle(KeyEvent event) {
        //System.out.println("Pressed key text: " + event.getCode());
        if (event.getCode() == KeyCode.RIGHT) {
            player1.move(50,0);
        }
        if (event.getCode() == KeyCode.LEFT) {
            player1.move(-50,0);
        }
        if (event.getCode() == KeyCode.UP) {
            player1.move(0,50);
        }
        if (event.getCode() == KeyCode.DOWN) {
            player1.move(0,-50);
        }
    }
}
