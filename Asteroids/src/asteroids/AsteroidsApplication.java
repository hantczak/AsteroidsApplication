package asteroids;

import javafx.application.Application;
import javafx.stage.Stage;

public class AsteroidsApplication extends Application {

    public void start(Stage window) {
        GameView gameView = new GameView(640, 480);
        gameView.setupGameView();

        window.setTitle("Asteroids!");
        window.setScene(gameView.getGameScene());
        window.show();
    }


    public static void main(String[] args) {
        launch(AsteroidsApplication.class);
    }
}
