package controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Player;

public class InputController {
    private Player player1;
    public void handle(KeyEvent e) {
        switch(e.getCode()){
            case RIGHT:
                if(player1.getX() < 700){
                    player1.move(50,0);
                }
                break;
            case LEFT:
                if(player1.getX() > 0){
                    player1.move(-50,0);
                }
                break;
            case UP:
                if(player1.getY() > 0){
                    player1.move(0,-50);
                }
                break;
            case DOWN:
                if(player1.getY() < 700) {
                    player1.move(0, 50);
                }
                break;
            case ENTER:
                /*if(gamePaused) {
                    gameTimeline.play();
                    gamePaused = false;
                } else {
                    gameTimeline.stop();
                    gamePaused = true;
                }*/
                break;
            default:
                break;
        }
    }
}
