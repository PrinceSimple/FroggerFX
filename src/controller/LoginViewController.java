package controller;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML
    public void onEnter(ActionEvent e){
        loginService.start();
    }

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

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        loginService.start();
    }
    @FXML
    private void handleRegisterButtonAction(ActionEvent event) throws IOException {
        registerService.start();
    }
    private void startGameScene(JSONObject player) throws IOException{
        GameViewController gvc = new GameViewController(this.nc, player);
        Scene gameScene;
        URL fxmlURL = getClass().getClassLoader().getResource("view/Gameboard.fxml");
        System.out.println(fxmlURL);
        FXMLLoader gameLoader = new FXMLLoader(fxmlURL);
        gameLoader.setController(gvc);
        gameLoader.load();
        gameScene = new Scene(gameLoader.getRoot());
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
            JSONObject response = loginService.getValue();
            loadingLabel.setVisible(false);
            message.setText(response.toString());
            loginService.reset();
            if(response.getJSONObject("user").get("username").toString().equals(username.getText())) {
                try {
                    startGameScene(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        registerService.setOnRunning(event -> {
            message.setText("Registering new user...");
            loadingLabel.setVisible(true);
            loadingAnim.play();
        });

        registerService.setOnSucceeded(event -> {
            JSONObject response = registerService.getValue();
            loadingLabel.setVisible(false);
            message.setText(response.toString());
            registerService.reset();
            if(response.getJSONObject("user").get("username").toString().equals(username.getText())) {
                try {
                    startGameScene(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                username.requestFocus();
            }
        });
    }
}
