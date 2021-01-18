package asteroids;

import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class PointsCounter {
    private AtomicInteger points;
    private Text text;
    private int difficultyLevel;

    public PointsCounter(Text text){
        this.points = new AtomicInteger();
        this.text = text;
        this.difficultyLevel = MainMenuView.getChosenDifficultyLevel();
    }

    public void increasePoints(){
        text.setText("Points: " + points.addAndGet(1000*difficultyLevel));
    }

    public int getPoints(){
       return points.get();
    }
}
