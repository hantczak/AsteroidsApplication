package asteroids.gameMechanic;

import asteroids.characters.*;
import asteroids.pointsManagement.PointsCounter;
import asteroids.views.EndScreen;
import asteroids.views.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class GameMechanic {
    private Pane gamePane;
    private Scene gameScene;
    private PointsCounter pointsCounter;
    private CharactersList charactersList;
    Text livesLeftLabel;

    public GameMechanic(Scene scene, Text livesLeft) {
        this.gamePane = new Pane();
        gamePane.setPrefSize(GameView.getGameScreenWidth(), GameView.getGameScreenHeight());
        this.gameScene = scene;
        this.livesLeftLabel = livesLeft;
    }


    public void setupGameComponents(PointsCounter pointsCounter, int playerLives) {
        this.pointsCounter = pointsCounter;
        this.charactersList = new CharactersList(pointsCounter,this.gamePane);
        charactersList.setupInitialCharacters(playerLives);

        KeyboardUserInputController controller = new KeyboardUserInputController(gameScene, charactersList.getShip());
        controller.readKeyboardInput();
        startGame(controller);
        livesLeftLabel.setText("Lives left: " + charactersList.getPlayersLivesLeft());
    }

    public void startGame(KeyboardUserInputController controller) {
        new AnimationTimer() {
            public void handle(long now) {
                controller.checkForInput();
                if (controller.isShooting()) {
                    charactersList.shoot();
                }
                charactersList.ensureCharactersMovement();

                if (charactersList.checkForShipCollision()) {
                    if (charactersList.getPlayersLivesLeft()==1) {
                        decreaseLives();
                        updateLivesDisplay();
                        stop();
                        showEndScreen();
                    } else if(charactersList.getPlayersLivesLeft()>1){
                        decreaseLives();
                        restartGame(charactersList.getPlayersLivesLeft());
                        updateLivesDisplay();
                    }
                }

                charactersList.checkForProjectileCollision();
                charactersList.deleteAllDeadCharacters();
                charactersList.spawnAdditionalAsteroid();
            }
        }.start();
    }

    public void restartGame(int livesLeft) {
        charactersList.setAllCharactersDead();
        charactersList.deleteAllDeadCharacters();
        setupGameComponents(pointsCounter, livesLeft);
    }

    public void showEndScreen() {
        charactersList.setAllCharactersDead();
        charactersList.deleteAllDeadCharacters();
        EndScreen endScreen = new EndScreen(pointsCounter, gamePane);
        endScreen.create();
    }

    public void decreaseLives(){
        charactersList.getShip().decreaseLives();
    }

    public void updateLivesDisplay(){
        livesLeftLabel.setText("Lives left: " + charactersList.getPlayersLivesLeft());
    }


    public Pane getGamePane() {
        return gamePane;
    }
}