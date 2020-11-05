package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Obstacle;
import model.Player;
import model.Row;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class ViewController implements Initializable, Observer {
    private GameController gameController;
    Canvas bgCanvas = new javafx.scene.canvas.Canvas(750, 750);
    //private String[] images = {"bg13.png", "bg12.jpg", "bg11.jpg", "bg10.jpg", "bg9.jpg", "bg8.jpg", "bg7.png",
    //        "bg6.jpg", "bg5.jpg", "bg4.jpg", "bg3.jpg", "bg2.jpg", "bg1.png"};
    private Player player1;
    private Obstacle car1;
    private static Color[] rowColors = {
            Color.web("99FF99"), Color.web("99FF99"), Color.web("99FFFF"), Color.web("99FFFF"),
            Color.web("99FFFF"), Color.web("99FFFF"), Color.web("99FFFF"), Color.web("D6FF99"),
            Color.web("1f261f"), Color.web("1f261f"), Color.web("1f261f"), Color.web("1f261f"),
            Color.web("1f261f"), Color.web("1f261f"), Color.web("99FF99")
            };

    //private AnimationTimer animationTimer;
    long startTime = 0;
    boolean atStarted = false;
    private Timeline gameTimeline = new Timeline(
        new KeyFrame(Duration.millis(1),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    nextTick();
                }
            }));

    private void nextTick() {
        if(player1.intersects(car1)){
            System.out.println("INTERSECTS!!!!!!!!!");
        }
        car1.update();
    }

    public ViewController()  {
        gameController = new GameController();
    }

    @FXML
    private Pane playerLayer;
    @FXML
    private Pane obstacleLayer;
    @FXML
    private Pane bgLayer;
    @FXML
    private GridPane Info;
    @FXML
    private GridPane player_grid;
    @FXML
    private void handleKeyPress(KeyEvent e) {
        //gameController.inputController.handle(e);
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
                if(!atStarted) {
                    gameTimeline.play();
                    atStarted = true;
                } else {
                    gameTimeline.stop();
                    atStarted = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gameTimeline.setCycleCount(Timeline.INDEFINITE);

        for(int row=0;row<15;row++){
            new Row(0,row * 50, 750, 50, rowColors[row], bgCanvas);
        }

        player1 = new Player(350,700);
        player1.initImage(playerLayer);
        bgLayer.getChildren().add(bgCanvas);
        car1 = new Obstacle(400, 0.1, 1);
        car1.initImage(obstacleLayer);
        gameTimeline.play();
        atStarted = true;

    }

    public void gameLoop() {

    }

    public GameController getGameController() {
        return gameController;
    }
}
