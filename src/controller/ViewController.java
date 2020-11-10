package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Obstacle;
import model.Player;
import model.Row;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class ViewController {
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
    @FXML
    private Pane obstacleLayer;
    @FXML
    private Pane playerLayer;
    @FXML
    private Label scoreLabel;
    @FXML
    private Pane bgLayer;
    @FXML
    private GridPane Info;
    @FXML
    private GridPane player_grid;

//    Row row1 = new Row(0, Color.web("99FF99"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row2 = new Row(1, Color.web("99FF99"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row3 = new Row(2, Color.web("99FFFF"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row4 = new Row(3, Color.web("99FFFF"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row5 = new Row(4, Color.web("99FFFF"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row6 = new Row(5, Color.web("99FFFF"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row7 = new Row(6, Color.web("99FFFF"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row8 = new Row(7, Color.web("D6FF99"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row9 = new Row(8, Color.web("1f261f"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row10 = new Row(9, Color.web("1f261f"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row11 = new Row(10, Color.web("1f261f"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row12 = new Row(11, Color.web("1f261f"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row13 = new Row(12, Color.web("1f261f"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);
//    Row row14 = new Row(13, Color.web("1f261f"), bgCanvas, obstacleLayer, 4, 50, 2, 50, 1);
//    Row row15 = new Row(14, Color.web("99FF99"), bgCanvas, obstacleLayer, 0, 0, 0, 0, 0);

    public Row[] rows =
    {
        new Row(0, Color.web("99FF99"), bgCanvas, 0, 0, 0, 0, 0, true),
        new Row(1, Color.web("99FF99"), bgCanvas, 0, 0, 0, 0, 0, true),
        new Row(2, Color.web("99FFFF"), bgCanvas, 0, 0, 0, 0, 0, true),
        new Row(3, Color.web("99FFFF"), bgCanvas, 0, 0, 0, 0, 0, true),
        new Row(4, Color.web("99FFFF"), bgCanvas, 0, 0, 0, 0, 0, true),
        new Row(5, Color.web("99FFFF"), bgCanvas, 0, 0, 0, 0, 0, true),
        new Row(6, Color.web("99FFFF"), bgCanvas, 0, 0, 0, 0, 0, true),
        new Row(7, Color.web("D6FF99"), bgCanvas, 0, 0, 0, 0, 0, true),
        new Row(8, Color.web("1f261f"), bgCanvas, 4, 50, 100, 300, 0.2, false),
        new Row(9, Color.web("1f261f"), bgCanvas, 2, 50, 200, 50, 0.15, true),
        new Row(10, Color.web("1f261f"), bgCanvas,3, 50, 150, 150, 0.08, false),
        new Row(11, Color.web("1f261f"), bgCanvas,2, 50, 100, 100, 0.1, true),
        new Row(12, Color.web("1f261f"), bgCanvas,4, 50, 100, 200, 0.2, false),
        new Row(13, Color.web("1f261f"), bgCanvas,2, 50, 50, 50, 0.3, true),
        new Row(14, Color.web("99FF99"), bgCanvas,0, 0, 0, 0, 0, true)
    };

    long startTime = 0;
    boolean gamePaused = false;
    private Timeline gameTimeline = new Timeline(
        new KeyFrame(Duration.millis(1),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    nextTick();
                }
            }));

    private void nextTick() {
        int index = 0;
        for (Row r: rows) {
            for (Obstacle o: r.obstacles) {
                if (o.intersects(player1)){
                    player1.reset(350,700);
                }
                o.update();
            }
        }
    }

    public ViewController()  {
        gameController = new GameController();
    }

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
                if(gamePaused) {
                    gameTimeline.play();
                    gamePaused = false;
                } else {
                    gameTimeline.stop();
                    gamePaused = true;
                }
                break;
            default:
                break;
        }
    }

    public void initialize() {
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        bgLayer.getChildren().add(bgCanvas);

        player1 = new Player(350,700);
        player1.initImage(playerLayer);
        for (Row r: rows) {
            for (Obstacle o: r.obstacles) {
                o.initImage(obstacleLayer);
            }
        }
        gameTimeline.play();
        SimpleStringProperty s = new SimpleStringProperty("0");
        scoreLabel.textProperty().bind(s);
    }

    public void gameLoop() {

    }

    public GameController getGameController() {
        return gameController;
    }
}
