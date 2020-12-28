import controller.GameViewController;
import controller.LoginViewController;
import controller.NetworkController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class FroggerFX extends Application {
    NetworkController nc = new NetworkController();
    LoginViewController lvc = new LoginViewController();
    GameViewController gvc = new GameViewController();
    Parent loginRoot;
    Parent gameRoot;
    Scene loginScene;
    Scene gameScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader gameLoader = new FXMLLoader(FroggerFX.class.getResource("view/Gameboard.fxml"), null);
        gameLoader.setController(gvc);
        gameLoader.load();
        gameRoot = gameLoader.getRoot();
        gameScene = new Scene(gameRoot);
        gameScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap");
        FXMLLoader loginLoader = new FXMLLoader(FroggerFX.class.getResource("view/Login.fxml"), null);
        loginLoader.setController(lvc);
        loginLoader.load();
        loginRoot = loginLoader.getRoot();
        loginScene = new Scene(loginRoot);
        lvc.setNextScene(gameScene);
        //gameScene.getRoot().requestFocus();
        loginScene.getRoot().requestFocus();
        //primaryStage.setScene(gameScene);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public Scene getLoginScene() {
        return loginScene;
    }



    public static void main(String[] args) {
        launch(args);
    }

}
