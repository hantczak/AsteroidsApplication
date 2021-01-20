package asteroids.views;

import asteroids.gameMechanic.GameMechanic;
import asteroids.pointsManagement.PointsCounter;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class GameView {
    private Scene gameScene;
    public static int gameScreenWidth;
    public static int gameScreenHeight;

    public GameView(int width, int height){
        gameScreenWidth = width;
        gameScreenHeight = height;
    }


    public void setupGameView() {
        Text pointsLabel = new Text(10, 20, "Points: 0");
        Text livesLeftLabel = new Text(10,20,"Lives left: ");
        PointsCounter pointsCounter = new PointsCounter(pointsLabel);

        HBox topDisplay = new HBox();
        BorderPane gameView = new BorderPane();
        gameScene = new Scene(gameView);
        GameMechanic game = new GameMechanic(gameScene,livesLeftLabel);
        gameView.setPrefSize(gameScreenWidth,gameScreenHeight);


        game.setupGameComponents(pointsCounter,4-MainMenuView.getChosenDifficultyLevel());

        topDisplay.getChildren().addAll(pointsLabel,livesLeftLabel);
        topDisplay.setSpacing(200);
        gameView.getChildren().add(game.getGamePane());
        gameView.getChildren().add(topDisplay);
    }

    public static int getGameScreenWidth(){
        return gameScreenWidth;
    }
    public static int getGameScreenHeight(){
        return gameScreenHeight;
    }
    public Scene getGameScene(){
        return gameScene;
    }
}
