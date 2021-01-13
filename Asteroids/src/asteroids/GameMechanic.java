package asteroids;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GameMechanic {
    private GameView gameSettings;
    private Pane gamePane;
    private Scene gameScene;
    private List<Character> asteroids;
    private List<Character> projectiles;
    private Character ship;
    private PointsCounter pointsCounter;

    public GameMechanic(Scene scene, GameView gameView) {
        this.gameSettings = gameView;
        asteroids = new ArrayList<>();
        projectiles = new ArrayList<>();
        this.gamePane = new Pane();
        gamePane.setPrefSize(gameSettings.getGameScreenWidth(), gameSettings.getGameScreenHeight());
        this.gameScene = scene;
    }


    public void setupGameComponents(PointsCounter pointsCounter) {
        this.pointsCounter = pointsCounter;

        spawnInitialAsteroids();

        ship = new Ship(gameSettings.getGameScreenWidth() / 2, gameSettings.getGameScreenHeight() / 2);
        asteroids.forEach(asteroid -> gamePane.getChildren().add(asteroid.getCharacterShape()));
        gamePane.getChildren().add(ship.getCharacterShape());

        KeyboardUserInputController controller = new KeyboardUserInputController(gameScene,ship);
        controller.readKeyboardInput();
        startGame(controller);

    }

    public void startGame(KeyboardUserInputController controller) {
        new AnimationTimer() {
            public void handle(long now) {
                controller.checkForInput();

                if(controller.isShooting()){
                    shoot();
                }

                ensureCharactersMovement();

                if(checkForShipCollision()){
                    stop();
                }

                checkForProjectileCollision();

                deleteDeadCharacters(projectiles);
                deleteDeadCharacters(asteroids);
                spawnAdditionalAsteroid();

            }
        }.start();
    }

    public void deleteDeadCharacters(List<Character> listOfCharacters) {
        listOfCharacters.stream()
                .filter(character -> !character.isAlive())
                .forEach(character -> {
                    gamePane.getChildren().remove(character.getCharacterShape());
                });
        listOfCharacters.removeAll(listOfCharacters.stream()
                .filter(character -> !character.isAlive())
                .collect(Collectors.toList()));
    }


    public void spawnAdditionalAsteroid() {
        if (Math.random() < 0.005) {
            Random rand = new Random();
            Asteroid asteroid = new Asteroid(rand.nextInt(gameSettings.getGameScreenWidth()), rand.nextInt(gameSettings.getGameScreenHeight()));
            if (!asteroid.collide(ship)) {
                asteroids.add(asteroid);
                asteroid.setAlive(true);
                gamePane.getChildren().add(asteroid.getCharacterShape());
            }
        }
    }

    public void spawnInitialAsteroids() {
        asteroids = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            Random rand = new Random();
            Asteroid asteroid = new Asteroid(rand.nextInt(gameSettings.getGameScreenWidth()), rand.nextInt(gameSettings.getGameScreenHeight()));
            asteroids.add(asteroid);
            asteroid.setAlive(true);
        }
    }

    public void setProjectileSpeed(Character projectile) {
        projectile.accelerate();
        projectile.setMovement(projectile.getMovement().normalize().multiply(4));
    }

    public void ensureCharactersMovement() {
        ship.move();
        asteroids.forEach(Character::move);
        projectiles.forEach(Character::move);
    }

    public boolean checkForShipCollision(){
        AtomicBoolean ifTrue = new AtomicBoolean(false);
        asteroids.forEach(asteroid -> {
            if (asteroid.collide(ship)) {
                ifTrue.set(true);
            }
        });
        return ifTrue.get();
    }

    public void checkForProjectileCollision(){
        projectiles.forEach(projectile -> {
            asteroids.forEach(asteroid -> {
                if (projectile.collide(asteroid)) {
                    projectile.setAlive(false);
                    asteroid.setAlive(false);
                    pointsCounter.increasePoints();
                }
            });
        });
    }

    public void shoot() {
        if (projectiles.size() < 5) {
            Projectile projectile = new Projectile((int) ship.getCharacterShape().getTranslateX(), (int) ship.getCharacterShape().getTranslateY());
            projectile.getCharacterShape().setRotate(ship.getCharacterShape().getRotate());
            projectile.setAlive(true);
            setProjectileSpeed(projectile);

            projectiles.add(projectile);
            gamePane.getChildren().add(projectile.getCharacterShape());
        }
    }

    public Pane getGamePane() {
        return gamePane;
    }
}