import controller.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class FroggerFX extends Application {
    LoginViewController lvc = new LoginViewController();
    Parent loginRoot;
    Scene loginScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL fxmlURL = getClass().getClassLoader().getResource("view/Login.fxml");
        FXMLLoader loginLoader = new FXMLLoader(fxmlURL);
        loginLoader.setController(lvc);
        loginLoader.load();
        loginRoot = loginLoader.getRoot();
        loginScene = new Scene(loginRoot);
        loginScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap");
        loginScene.getRoot().requestFocus();
        primaryStage.setResizable(false);
        primaryStage.setTitle("FroggerFX");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
