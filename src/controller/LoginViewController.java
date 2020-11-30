package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {
    NetworkController nc = new NetworkController();
    private Scene gameScene;
    @FXML
    private Label message;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        //changeScene();
        //Alert a = new Alert(Alert.AlertType.INFORMATION);
        //a.setContentText("BLabla");
        //a.showAndWait();
        try {
            JSONObject ob = nc.login(username.getText(), password.getText());
            message.setText(ob.toString());
            nc.fetchAllPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        try {
            JSONObject ob = nc.register(username.getText(), password.getText());
            message.setText(ob.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setNextScene(Scene scene){
        gameScene = scene;
    }
    public void changeScene(){
        Stage primaryStage = (Stage)(loginButton).getScene().getWindow();
        primaryStage.setScene(gameScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*try {
            nc.login("Phrink", "Test123!");
            System.out.println(nc.fetchAllPlayers());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
