package asteroids.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MainMenuView {
    private static int chosenDifficultyLevel = 1;
    private int chosenWindowWidth = 640;
    private int chosenWindowHeight = 480;
    private Button startButton;

    public Scene getView() {
        GridPane layout = createMenuLayout();
        Scene mainMenu = new Scene(layout);

        return mainMenu;
    }

    public GridPane createMenuLayout(){
        Label difficultyLabel = new Label("Difficulty level selection:");
        Label resolutionLabel = new Label("Window resolution selection:");
        Label chosenDifficulty = new Label("Current level: Newbie");
        Label chosenResolution = new Label("Current resolution: 640x480");

        startButton = new Button("START");
        GridPane layout = new GridPane();
        layout.add(difficultyLabel,0,0);
        layout.add(createDifficultySelectionPanel(chosenDifficulty),2,3);
        layout.add(startButton, 1, 8);
        layout.add(createResolutionSelectionPanel(chosenResolution),2,7);
        layout.add(chosenResolution,0,7);
        layout.add(resolutionLabel,0,6);
        layout.add(chosenDifficulty,0,3);
        return layout;
    }


    public HBox createDifficultySelectionPanel(Label chosenDifficulty) {

        Button newbieButton = new Button("Newbie");
        newbieButton.setOnAction(event->{
            setDifficultyLevel(1);
            chosenDifficulty.setText("Current level: Newbie");
        });
        Button moderateButton = new Button("Moderate");
        moderateButton.setOnAction(event->{
            setDifficultyLevel(2);
            chosenDifficulty.setText("Current level: Moderate");
        });
        Button hardButton = new Button("Hard");
        hardButton.setOnAction(event->{
            setDifficultyLevel(3);
            chosenDifficulty.setText("Current level: Hard");

        });
        HBox difficultySelectionPanel = new HBox();

        difficultySelectionPanel.getChildren().addAll(newbieButton, moderateButton, hardButton);
        difficultySelectionPanel.setSpacing(10);

        return difficultySelectionPanel;
    }

    public HBox createResolutionSelectionPanel(Label chosenResolution) {
        Button lowerResolution = new Button("640x480");
        lowerResolution.setOnAction(event->{
            setChosenWindowHeight(480);
            setChosenWindowWidth(640);
            chosenResolution.setText("Current resolution: 640x480");
        });
        Button higherResolution = new Button("800x600");
        higherResolution.setOnAction(event->{
            setChosenWindowHeight(600);
            setChosenWindowWidth(800);
            chosenResolution.setText("Current resolution: 800x600");
        });

        HBox resolutionSelectionPanel = new HBox();

        resolutionSelectionPanel.getChildren().addAll(lowerResolution, higherResolution);
        resolutionSelectionPanel.setSpacing(10);
        return resolutionSelectionPanel;
    }

    public static void setDifficultyLevel(int level){
        chosenDifficultyLevel = level;
    }

    public static int getChosenDifficultyLevel(){
        return chosenDifficultyLevel;
    }

    public void setChosenWindowWidth(int width){
        this.chosenWindowWidth = width;
    }

    public void setChosenWindowHeight(int height){
        this.chosenWindowHeight = height;
    }

    public int getChosenWindowWidth(){
        return chosenWindowWidth;
    }

    public int getChosenWindowHeight(){
        return chosenWindowHeight;
    }

    public Button getStartButton() {
        return startButton;
    }
}

