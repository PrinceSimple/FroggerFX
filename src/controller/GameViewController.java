package controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.Obstacle;
import model.Player;
import model.Row;
import model.RowBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

    private ArrayList<Row> rows = new ArrayList<Row>();

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
                        if (r.getId() == 1) {
                            setPlayerHome(player1, o);
                        }
                        isSafe = true;
                    }
                }
                if (isSafe) {
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
        ImageView deathimage = new ImageView(new Image("assets/skull_head_death.png", 50, 50, false, false));
        deathimage.setX(p.getX());
        deathimage.setY(p.getY());
        FadeTransition ft = new FadeTransition(Duration.millis(3000), deathimage);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        playerLayer.getChildren().add(deathimage);
        ft.play();
        p.lives.set(p.lives.get() - 1);
        p.reset(350, 700);
        if(p.lives.get() == 0){
            gameOver();
        }
    }

    private void setPlayerHome(Player p, Obstacle h) {
        if(!h.isOccupied()){
            h.setOccupied(true);
            ImageView homeImage = new ImageView(new Image("assets/animated_example.gif", 50, 50, false, false));
            homeImage.setX(h.getX());
            homeImage.setY(h.getY());
            playerLayer.getChildren().add(homeImage);
            //playerLayer.getChildren().remove(homeImage);
            p.score.set(p.score.get() + 500);
            p.reset(350, 700);
            occupiedHomes++;
        } else{
            p.reset(player1.getX(), player1.getY() + 50);
        }

    }

    @FXML
    private void handleKeyPress(KeyEvent e) {
        //inputController.handle(e);
        switch (e.getCode()) {
            case RIGHT:
                if (player1.getX() < 700) {
                    player1.move(50, 0);
                }
                player1.img.setRotate(90);
                break;
            case LEFT:
                if (player1.getX() > 0) {
                    player1.move(-50, 0);
                }
                player1.img.setRotate(-90);
                break;
            case UP:
                if (player1.getY() > 0) {
                    player1.move(0, -50);
                    player1.score.set(player1.score.get() + 10);
                }
                player1.img.setRotate(0);
                break;
            case DOWN:
                if (player1.getY() < 700) {
                    player1.move(0, 50);
                }
                player1.img.setRotate(180);
                break;
            case ENTER:
                if (gamePaused) {
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
        for(int id=0; id < 15; id++){
            rows.add(new RowBuilder(id, 1, bgCanvas).build());
        }
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        bgLayer.getChildren().add(bgCanvas);
        for (Row r : rows) {
            for (Obstacle o : r.obstacles) {
                o.initImage(obstacleLayer);
            }
        }
        nameLabel.textProperty().bind(player1.name);
        startGame();
       /* for (HomeSlot h: homes) {
                h.initImage(playerLayer);
        }*/

    }

    private void startGame() {
        player1 = new Player(1, 350, 700);
        player1.initImage(playerLayer);
        player1.setImageTint(player1.colorAdjust);
        scoreLabel.textProperty().bind(player1.score.asString());
        gameTimeline.play();
    }

    private void gameOver() {
        player1.reset(1000, 10000);
        gameTimeline.stop();
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setStyle("-fx-font-family: 'Press Start 2P'; -fx-text-fill: Red;  -fx-font-size: 32;");
        gameOverLabel.setTranslateX(225);
        gameOverLabel.setTranslateY(375);
        playerLayer.getChildren().add(gameOverLabel);

        ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(500), gameOverLabel);
        scaleTrans.setByX(1.6);
        scaleTrans.setByY(1.6);
        scaleTrans.setAutoReverse(true);
        scaleTrans.setCycleCount(5);
        scaleTrans.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    endGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        scaleTrans.play();
    }

    private void endGame() throws IOException {
        //nc.fetchAllPlayers();
        System.out.println("YOYOYOYOYOYOYOYOYO");
    }
}