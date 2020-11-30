import controller.GameViewController;
import controller.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FroggerFX extends Application {
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

        FXMLLoader loginLoader = new FXMLLoader(FroggerFX.class.getResource("view/Login.fxml"), null);
        loginLoader.setController(lvc);
        loginLoader.load();
        loginRoot = loginLoader.getRoot();
        loginScene = new Scene(loginRoot);
        loginScene.getRoot().requestFocus();
        lvc.setNextScene(gameScene);
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
