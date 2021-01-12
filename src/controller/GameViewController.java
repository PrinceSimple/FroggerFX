package controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import model.HomeSlot;
import model.Obstacle;
import model.Player;
import model.Row;
import org.json.JSONObject;

public class GameViewController {
    private Player player1;
    private Player player2;
    private boolean isTwoPlayerGame = false;
    //private InputController inputController = new InputController(player1);
    NetworkController nc;

    public GameViewController(NetworkController nc, JSONObject player) {
        this.nc = nc;
        this.player1 = new Player(1, 350, 750);
        this.player1.name.set(player.getJSONObject("user").get("username").toString());
        this.player1.highscore.set((Integer) player.getJSONObject("user").getJSONObject("player").get("highscore"));
    }

    Canvas bgCanvas = new javafx.scene.canvas.Canvas(750, 750);


    private int occupiedHomes = 0;

    @FXML
    private Pane obstacleLayer;
    @FXML
    private Pane playerLayer;
    @FXML
    private Pane bgLayer;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private GridPane Info;
    @FXML
    private GridPane player_grid;

    public Row[] rows =
    {
        new Row(0, Color.web("99FF99"), bgCanvas, 0, 0, 0, 0, 0, true, ""),
        new Row(1, Color.web("99FF99"), bgCanvas, 5, 50, 140, 75, 0, true, "assets/home.png"),
        new Row(2, Color.web("99FFFF"), bgCanvas, 3, 230, 300, 400, 1, true, "assets/Hog_1.png"),
        new Row(3, Color.web("99FFFF"), bgCanvas, 2, 175, 250, 230, 2, false, "assets/turtle_1.png"),
        new Row(4, Color.web("99FFFF"), bgCanvas, 2, 200, 300, 230, 1.8, true, "assets/Hog_1.png"),
        new Row(5, Color.web("99FFFF"), bgCanvas, 3, 230, 200, 50, 1, false, "assets/Hog_1.png"),
        new Row(6, Color.web("99FFFF"), bgCanvas, 3, 175, 200, 100, 0.5, true, "assets/turtle_1.png"),
        new Row(7, Color.web("D6FF99"), bgCanvas, 0, 0, 0, 0, 0, true, ""),
        new Row(8, Color.web("1f261f"), bgCanvas, 4, 50, 100, 300, 2, false, "assets/Car_1.png"),
        new Row(9, Color.web("1f261f"), bgCanvas, 2, 50, 200, 50, 1.5, true, "assets/Car_2.png"),
        new Row(10, Color.web("1f261f"), bgCanvas,3, 50, 150, 150, 0.8, false, "assets/Car_3.png"),
        new Row(11, Color.web("1f261f"), bgCanvas,2, 50, 100, 100, 1, true, "assets/Car_2.png"),
        new Row(12, Color.web("1f261f"), bgCanvas,4, 50, 100, 200, 2, false, "assets/Car_3.png"),
        new Row(13, Color.web("1f261f"), bgCanvas,2, 50, 50, 50, 3, true, "assets/Car_1.png"),
        new Row(14, Color.web("99FF99"), bgCanvas,0, 0, 0, 0, 0, true, "")
    };

    long startTime = 0;
    boolean gamePaused = false;
    private Timeline gameTimeline = new Timeline(
        new KeyFrame(Duration.millis(30),
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
                        if(r.getId() == 1) {
                            setPlayerHome(player1, o);
                        }
                        isSafe = true;
                    }
                }
                if(isSafe) {
                    player1.moveX(r.obstacles[0].speed);
                } else {
                    killPlayer(player1);
                }
            } else if (r.intersects(player1) && !r.isWater()) {
                for (Obstacle o : r.obstacles) {
                    if (o.contains(player1)) {
                        killPlayer(player1);
                    }
                }
            }
        }
    }

    private void killPlayer(Player p) {
        ImageView deathimage = new ImageView(new Image("assets/skull_head_death.png",50,50, false, false));
        deathimage.setX(p.getX());
        deathimage.setY(p.getY());
        FadeTransition ft = new FadeTransition(Duration.millis(3000), deathimage);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        playerLayer.getChildren().add(deathimage);
        ft.play();
        p.lives.subtract(1);
        p.reset(350, 700);
    }

    private void setPlayerHome(Player p, Obstacle h) {
        ImageView homeImage = new ImageView(new Image("assets/animated_example.gif",50,50, false, false));
        homeImage.setX(h.getX());
        homeImage.setY(h.getY());
        playerLayer.getChildren().add(homeImage);
       //playerLayer.getChildren().remove(homeImage);
        p.score.set(p.score.get() + 500);
        p.reset(350, 700);
        occupiedHomes++;
    }

    @FXML
    private void handleKeyPress(KeyEvent e) {
        //inputController.handle(e);
        switch(e.getCode()){
            case RIGHT:
                if(player1.getX() < 700){
                    player1.move(50,0);
                }
                player1.img.setRotate(90);
                break;
            case LEFT:
                if(player1.getX() > 0){
                    player1.move(-50,0);
                }
                player1.img.setRotate(-90);
                break;
            case UP:
                if(player1.getY() > 0){
                    player1.move(0,-50);
                    player1.score.set(player1.score.get() + 10);
                }
                player1.img.setRotate(0);
                break;
            case DOWN:
                if(player1.getY() < 700) {
                    player1.move(0, 50);
                }
                player1.img.setRotate(180);
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
        nameLabel.textProperty().bind(player1.name);
        startGame();
       /* for (HomeSlot h: homes) {
                h.initImage(playerLayer);
        }*/

    }

    public void startGame(){
        player1 = new Player(1, 350,700);
        player1.initImage(playerLayer);
        player1.setImageTint(player1.colorAdjust);
        scoreLabel.textProperty().bind(player1.score.asString());
        for (Row r: rows) {
            for (Obstacle o: r.obstacles) {
                o.initImage(obstacleLayer);
            }
        }
        gameTimeline.play();
    }
//    public GameController getGameController() {
//        return gameController;
//    }
}
