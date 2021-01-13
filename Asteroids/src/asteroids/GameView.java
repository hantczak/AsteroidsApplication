package asteroids;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class GameView {
    private Scene gameScene;
    static int gameScreenWidth;
    static int gameScreenHeight;

    public GameView(int width, int height){
        gameScreenWidth = width;
        gameScreenHeight = height;
    }


    public void setupGameView() {
        BorderPane gameView = new BorderPane();
        gameScene = new Scene(gameView);
        GameMechanic game = new GameMechanic(gameScene);
        gameView.setPrefSize(gameScreenWidth,gameScreenHeight);



        Text text = new Text(10, 20, "Points: 0");
        PointsCounter pointsCounter = new PointsCounter(text);

        game.setupGameComponents(pointsCounter);

        gameView.getChildren().add(game.getGamePane());
        gameView.getChildren().add(text);

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
