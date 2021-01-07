package controller;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.concurrent.Worker.State.RUNNING;

public class LoginViewController implements Initializable {
    NetworkController nc = new NetworkController();

    @FXML
    private Label message;
    @FXML
    private ImageView loadingLabel;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;


    Service<JSONObject> loginService = new Service<JSONObject>() {
        @Override
        protected Task<JSONObject> createTask() {
            return new Task<JSONObject>() {
                @Override
                protected JSONObject call() throws Exception {
                    JSONObject o = nc.login(username.getText(), password.getText());
                    return o;
                }
            };
        }
    };

    Service<JSONObject> registerService = new Service<JSONObject>() {
        @Override
        protected Task<JSONObject> createTask() {
            return new Task<JSONObject>() {
                @Override
                protected JSONObject call() throws Exception {
                    JSONObject o = nc.register(username.getText(), password.getText());
                    return o;
                }
            };
        }
    };

    public void setNextScene(Scene scene){
        //gameScene = scene;
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        loginService.start();
    }
    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
       registerService.start();
    }
    private void startGameScene() throws IOException{
        GameViewController gvc = new GameViewController();
        Parent gameRoot;
        Scene gameScene;
        FXMLLoader gameLoader = new FXMLLoader(LoginViewController.class.getResource("../view/Gameboard.fxml"), null);
        gameLoader.setController(gvc);
        gameLoader.load();
        gameRoot = gameLoader.getRoot();
        gameScene = new Scene(gameRoot);
        gameScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap");
        Stage primaryStage = (Stage)(loginButton).getScene().getWindow();
        gameScene.getRoot().requestFocus();
        primaryStage.setScene(gameScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.disableProperty().bind(Bindings.or(loginService.stateProperty().isEqualTo(RUNNING), registerService.stateProperty().isEqualTo(RUNNING)));
        registerButton.disableProperty().bind(Bindings.or(loginService.stateProperty().isEqualTo(RUNNING), registerService.stateProperty().isEqualTo(RUNNING)));

        RotateTransition loadingAnim = new RotateTransition(Duration.seconds(2), loadingLabel);
        loadingAnim.setToAngle(-360);
        loadingAnim.setInterpolator(Interpolator.LINEAR);
        loadingAnim.setCycleCount(RotateTransition.INDEFINITE);

        loadingLabel.setVisible(false);

        loginService.setOnRunning(event -> {
            message.setText("Logging in...");
            loadingLabel.setVisible(true);
            loadingAnim.play();
        });

        loginService.setOnSucceeded(event -> {
            loadingLabel.setVisible(false);
            message.setText(loginService.getValue().toString());
            loginService.reset();
            try {
                startGameScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        registerService.setOnRunning(event -> {
            message.setText("Registering new user...");
            loadingLabel.setVisible(true);
            loadingAnim.play();
        });

        registerService.setOnSucceeded(event -> {
            loadingLabel.setVisible(false);
            message.setText(registerService.getValue().toString());
            registerService.reset();
            try {
                startGameScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //changeScene();
        });
    }
}
