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

public class GameViewController {
    //private GameController gameController;

    private Player player1;

    Canvas bgCanvas = new javafx.scene.canvas.Canvas(750, 750);
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

    public Row[] rows =
    {
        new Row(0, Color.web("99FF99"), bgCanvas, 0, 0, 0, 0, 0, true, ""),
        new Row(1, Color.web("99FF99"), bgCanvas, 0, 0, 0, 0, 0, true, ""),
        new Row(2, Color.web("99FFFF"), bgCanvas, 3, 230, 300, 400, 0.1, true, "assets/Hog_1.png"),
        new Row(3, Color.web("99FFFF"), bgCanvas, 2, 175, 250, 230, 0.2, false, "assets/turtle_1.png"),
        new Row(4, Color.web("99FFFF"), bgCanvas, 2, 200, 300, 230, 0.18, true, "assets/Hog_1.png"),
        new Row(5, Color.web("99FFFF"), bgCanvas, 3, 230, 200, 50, 0.1, false, "assets/Hog_1.png"),
        new Row(6, Color.web("99FFFF"), bgCanvas, 3, 175, 200, 100, 0.05, true, "assets/turtle_1.png"),
        new Row(7, Color.web("D6FF99"), bgCanvas, 0, 0, 0, 0, 0, true, ""),
        new Row(8, Color.web("1f261f"), bgCanvas, 4, 50, 100, 300, 0.2, false, "assets/Car_1.png"),
        new Row(9, Color.web("1f261f"), bgCanvas, 2, 50, 200, 50, 0.15, true, "assets/Car_2.png"),
        new Row(10, Color.web("1f261f"), bgCanvas,3, 50, 150, 150, 0.08, false, "assets/Car_3.png"),
        new Row(11, Color.web("1f261f"), bgCanvas,2, 50, 100, 100, 0.1, true, "assets/Car_2.png"),
        new Row(12, Color.web("1f261f"), bgCanvas,4, 50, 100, 200, 0.2, false, "assets/Car_3.png"),
        new Row(13, Color.web("1f261f"), bgCanvas,2, 50, 50, 50, 0.3, true, "assets/Car_1.png"),
        new Row(14, Color.web("99FF99"), bgCanvas,0, 0, 0, 0, 0, true, "")
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
        for (Row r : rows) {
            r.update();
            if (r.intersects(player1) && r.isWater()) {
                boolean isSafe = false;
                for (Obstacle o : r.obstacles) {
                    if (o.contains(player1)) {
                        isSafe = true;
                    }
                }
                if(isSafe) {
                    player1.moveX(r.obstacles[0].speed);
                }else {
                    player1.reset(350, 700);
                }
            } else if (r.intersects(player1) && !r.isWater()) {
                for (Obstacle o : r.obstacles) {
                    if (o.contains(player1)) {
                        player1.reset(350, 700);
                    }
                }
            }
        }
    }

//    public ViewController()  {
//        gameController = new GameController();
//    }

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
        //SimpleStringProperty s = new SimpleStringProperty(Integer.toString(player1.getScore()));

        //scoreLabel.textProperty().bind(s);

    }


//    public GameController getGameController() {
//        return gameController;
//    }
}
