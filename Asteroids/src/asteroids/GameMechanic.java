package asteroids;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GameMechanic {
    private Pane gamePane;
    private Scene gameScene;
    private List<Character> asteroids;
    private List<Character> projectiles;
    private Ship ship;
    private PointsCounter pointsCounter;

    public GameMechanic(Scene scene) {
        asteroids = new ArrayList<>();
        projectiles = new ArrayList<>();
        this.gamePane = new Pane();
        gamePane.setPrefSize(GameView.getGameScreenWidth(), GameView.getGameScreenHeight());
        this.gameScene = scene;
    }


    public void setupGameComponents(PointsCounter pointsCounter, int playerLives) {
        this.pointsCounter = pointsCounter;

        spawnInitialAsteroids();

        ship = new Ship(GameView.getGameScreenWidth() / 2, GameView.getGameScreenHeight() / 2, playerLives);
        asteroids.forEach(asteroid -> gamePane.getChildren().add(asteroid.getCharacterShape()));
        gamePane.getChildren().add(ship.getCharacterShape());

        KeyboardUserInputController controller = new KeyboardUserInputController(gameScene, ship);
        controller.readKeyboardInput();
        startGame(controller);

    }

    public void startGame(KeyboardUserInputController controller) {
        Timer timer = new Timer();
        new AnimationTimer() {
            public void handle(long now) {
                controller.checkForInput();

                if (controller.isShooting()) {
                    shoot();
                }

                ensureCharactersMovement();

                if (checkForShipCollision()) {
                    if (!ship.isAlive()) {
                        stop();
                        System.out.println("End!");
                    } else if (ship.isAlive()) {
                        ship.decreaseLives();
                        int livesLeft = ship.getLivesLeft();
                        clearScreen();
                        restartGame(livesLeft);
                    }
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
        if (Math.random() < 0.005 * MainMenuView.getChosenDifficultyLevel()*0.5) {
            Random rand = new Random();
            Asteroid asteroid = new Asteroid(rand.nextInt(GameView.getGameScreenWidth()), rand.nextInt(GameView.getGameScreenHeight()));
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
            Asteroid asteroid = new Asteroid(rand.nextInt(GameView.getGameScreenWidth()), rand.nextInt(GameView.getGameScreenHeight()));
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

    public boolean checkForShipCollision() {
        AtomicBoolean ifTrue = new AtomicBoolean(false);
        asteroids.forEach(asteroid -> {
            if (asteroid.collide(ship)) {
                ifTrue.set(true);
            }
        });
        return ifTrue.get();
    }

    public void checkForProjectileCollision() {
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

    public void restartGame(int livesLeft) {
        setupGameComponents(pointsCounter, livesLeft);
    }

    public void clearScreen() {
        asteroids.forEach(asteroid -> asteroid.setAlive(false));
        projectiles.forEach(projectile -> projectile.setAlive(false));
        ship.setAlive(false);

        deleteDeadCharacters(asteroids);
        deleteDeadCharacters(projectiles);
        gamePane.getChildren().remove(ship.getCharacterShape());
    }
}