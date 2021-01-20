package asteroids.views;

import asteroids.pointsManagement.PointsCounter;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EndScreen {
    PointsCounter pointsCounter;
    Pane endgamePane;

    public EndScreen(PointsCounter pointsCounter, Pane gamePane){
        this.pointsCounter = pointsCounter;
        this.endgamePane = gamePane;
    }


    public void create(){
        Label endText = new Label("The end!");
        Label finalPoints = new Label("Your Points: " + pointsCounter.getPoints());

        VBox display = new VBox();
        display.getChildren().add(endText);
        display.getChildren().add(finalPoints);
        display.setTranslateX(GameView.getGameScreenHeight() / 2.0);
        display.setTranslateY( GameView.getGameScreenWidth() / 2.0);

        endgamePane.getChildren().add(display);
    }
}
