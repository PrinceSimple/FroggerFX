import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import controller.ViewController;

public class FroggerFX extends Application {
    ViewController vc;
    public FroggerFX()  {
        vc = new ViewController();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        Scene scene;

        FXMLLoader loader = new FXMLLoader(FroggerFX.class.getResource("view/Gameboard.fxml"), null);
        vc.getGameController().setObserver(vc);
        loader.setController(vc);
        loader.load();
        root = loader.getRoot();
        scene = new Scene(root);
        //Parent root = FXMLLoader.load(getClass().getResource("view/Gameboard.fxml"));
        //Scene scene = new Scene(root, 800, 800);
        scene.getRoot().requestFocus();
        primaryStage.setTitle("FroggerFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
