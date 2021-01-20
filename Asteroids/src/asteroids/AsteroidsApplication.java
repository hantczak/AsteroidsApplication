package asteroids;
import asteroids.views.GameView;
import asteroids.views.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class AsteroidsApplication extends Application {

    public void start(Stage window) {
        window.setTitle("Asteroids!");
        MainMenuView mainView = new MainMenuView();
        window.setScene(mainView.getView());
        mainView.getStartButton().setOnAction(actionEvent -> {
            GameView gameView = new GameView(mainView.getChosenWindowWidth(), mainView.getChosenWindowHeight());
            gameView.setupGameView();
            window.setScene(gameView.getGameScene());
        });
        window.show();
    }


    public static void main(String[] args) {
        launch(AsteroidsApplication.class);
    }
}
