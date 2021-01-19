package controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Obstacle;
import model.Player;
import model.Row;
import model.RowBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class GameViewController {
    private Player player1;
    private Player player2;
    private boolean gameOver = false;
    //private InputController inputController = new InputController(player1);
    private NetworkController nc;
    private int occupiedHomes = 0;
    private ArrayList<Row> rows = new ArrayList<Row>();
    private int homeScore = 500;
    private boolean gamePaused = false;
    private Canvas bgCanvas = new javafx.scene.canvas.Canvas(750, 750);

    public GameViewController(NetworkController nc, JSONObject player) {
        this.nc = nc;
        this.player1 = new Player(1, 350, 700);
        this.player1.name.set(player.getJSONObject("user").get("username").toString());
        this.player1.highscore.set((Integer) player.getJSONObject("user").getJSONObject("player").get("highscore"));
    }

    @FXML
    private Pane obstacleLayer;
    @FXML
    private Pane expendableLayer;
    @FXML
    private Pane highscoresLayer;
    @FXML
    private Pane playerLayer;
    @FXML
    private Pane bgLayer;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label livesLabel;
    @FXML
    private Label highscoreLabel;
    @FXML
    private Button restartButton;
    @FXML
    private Label restartInfo;

    Service<JSONArray> fetchHighscoresService = new Service<JSONArray>() {
        @Override
        protected Task<JSONArray> createTask() {
            return new Task<JSONArray>() {
                @Override
                protected JSONArray call() throws Exception {
                    JSONArray ar = nc.fetchAllPlayers();
                    return ar;
                }
            };
        }
    };

    Service<Void> updateHighscoreService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    nc.updateHighscore(player1.highscore.get());
                    return null;
                }
            };
        }
    };

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
        ImageView deathimage = new ImageView(new Image("assets/frog_dead.png", 50, 50, false, false));
        deathimage.setX(p.getX());
        deathimage.setY(p.getY());
        FadeTransition ft = new FadeTransition(Duration.millis(3000), deathimage);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        expendableLayer.getChildren().add(deathimage);
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
            ImageView homeImage = new ImageView(new Image("assets/frog_home.png", 50, 50, false, false));
            homeImage.setX(h.getX());
            homeImage.setY(h.getY());
            expendableLayer.getChildren().add(homeImage);
            p.score.set(p.score.get() + homeScore);
            p.reset(350, 700);
            occupiedHomes++;
            if(occupiedHomes == 5){
                // LEVEL UP
                for (Row r : rows) {
                    for (Obstacle o : r.obstacles) {
                        o.setOccupied(false);
                        o.speed = o.speed * 1.2;
                    }
                }
                expendableLayer.getChildren().clear();
                occupiedHomes = 0;
                homeScore += 250;
            }
        } else{
            p.reset(player1.getX(), player1.getY() + 50);
        }
    }

    @FXML
    private void handleKeyPress(KeyEvent e) {
        //inputController.handle(e);
        switch (e.getCode()) {
            case RIGHT:
                if (player1.getX() < 700 && !gamePaused) {
                    player1.move(50, 0);
                }
                player1.img.setRotate(90);
                break;
            case LEFT:
                if (player1.getX() > 0 && !gamePaused) {
                    player1.move(-50, 0);
                }
                player1.img.setRotate(-90);
                break;
            case UP:
                if (player1.getY() > 0 && !gamePaused) {
                    player1.move(0, -50);
                    player1.score.set(player1.score.get() + 10);
                }
                player1.img.setRotate(0);
                break;
            case DOWN:
                if (player1.getY() < 700 && !gamePaused) {
                    player1.move(0, 50);
                }
                player1.img.setRotate(180);
                break;
            case ESCAPE:
                if (gamePaused) {
                    gameTimeline.play();
                    gamePaused = false;
                } else {
                    gameTimeline.stop();
                    gamePaused = true;
                }
                break;
            case ENTER:
                if (gameOver) {
                    restartGame();
                }
                break;
            default:
                break;
        }
    }

    public void initialize() throws IOException {
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        bgLayer.getChildren().add(bgCanvas);
        for(int id=0; id < 15; id++){
            rows.add(new RowBuilder(id, 1, bgCanvas).build());
        }
        for (Row r : rows) {
            for (Obstacle o : r.obstacles) {
                o.initImage(obstacleLayer);
            }
        }
        fetchHighscoresService.setOnSucceeded( event -> {
            showHighscores();
            fetchHighscoresService.reset();
        });
        updateHighscoreService.setOnSucceeded( event -> {
            updateHighscoreService.reset();
        });

        startGame();
    }

    private void showHighscores() {
        expendableLayer.getChildren().clear();
        highscoresLayer.setVisible(true);
        JSONArray players = fetchHighscoresService.getValue();
        //Sortingfoo because JSON is inherently unordered
        SortedMap<Integer, String> sm = new TreeMap<Integer, String>(new Comparator<Integer>() {
            public int compare(Integer a, Integer b) { return b.compareTo(a); }
        });
        for(int i = 0; i < players.length(); ++i){
            JSONObject player = players.getJSONObject(i);
            sm.put(player.getJSONObject("player").getInt("highscore"),player.getString("username"));
        }

        //Couldn't use table? --> this is for the lazy ones (me...)
        int i = 1;
        for (Map.Entry<Integer, String> entry : sm.entrySet()) {
            Integer score = entry.getKey();
            String name = entry.getValue();
            Label tmp = new Label( i + ". " + name + " - " + score);
            tmp.setTranslateX(150);
            tmp.setTranslateY(i*50+50);
            tmp.setStyle("-fx-font-family: 'Press Start 2P'; -fx-text-fill: White;  -fx-font-size: 28;");
            highscoresLayer.getChildren().add(tmp);
            if(i == 10){
                break;
            }
            i++;
        }

    }

    private void restartGame() {
        gameOver = false;
        gamePaused = false;
        gameTimeline.stop();
        player1.reset(350,700);
        player1.score.set(0);
        player1.lives.set(5);
        highscoresLayer.setVisible(false);
        gameTimeline.play();
        obstacleLayer.getChildren().clear();
        ArrayList<Row> newRows = new ArrayList<Row>();
        for(int id=0; id < 15; id++){
            newRows.add(new RowBuilder(id, 1, bgCanvas).build());
        }
        for (Row r : newRows) {
            for (Obstacle o : r.obstacles) {
                o.initImage(obstacleLayer);
            }
        }
        rows = newRows;
    }

    private void startGame() {
        player1.initImage(playerLayer);
        player1.setImageTint(player1.colorAdjust);
        nameLabel.textProperty().bind(player1.name);
        scoreLabel.textProperty().bind(player1.score.asString());
        livesLabel.textProperty().bind(player1.lives.asString());
        highscoreLabel.textProperty().bind(player1.highscore.asString());
        gameTimeline.play();
    }

    private void gameOver() {
        gameOver = true;
        player1.reset(1000,10000);
        gameTimeline.stop();
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setStyle("-fx-font-family: 'Press Start 2P'; -fx-text-fill: Red;  -fx-font-size: 32;");
        gameOverLabel.setTranslateX(230);
        gameOverLabel.setTranslateY(375);
        expendableLayer.getChildren().add(gameOverLabel);

        ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(500), gameOverLabel);
        scaleTrans.setByX(1.6);
        scaleTrans.setByY(1.6);
        scaleTrans.setAutoReverse(true);
        scaleTrans.setCycleCount(5);
        scaleTrans.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fetchHighscoresService.start();
            }
        });
        scaleTrans.play();

        if (player1.highscore.get() < player1.score.get()) {
            updateHighscoreService.start();
            player1.highscore.set(player1.score.get());
        }
    }
}