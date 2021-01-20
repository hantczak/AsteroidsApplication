package asteroids.characters;

import asteroids.pointsManagement.PointsCounter;
import asteroids.views.GameView;
import asteroids.views.MainMenuView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CharactersList {
    private List<Character> asteroids;
    private List<Character> projectiles;
    private Pane gamePane;
    private Ship ship;
    private PointsCounter pointsCounter;

    public CharactersList(PointsCounter pointsCounter,Pane gamePane){
        this.pointsCounter = pointsCounter;
        this.asteroids = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.gamePane = gamePane;
    }

    public void setupInitialCharacters(int playerLives){
        this.ship = new Ship(GameView.getGameScreenWidth() / 2, GameView.getGameScreenHeight() / 2, playerLives);
        spawnInitialAsteroids();
        asteroids.forEach(asteroid -> gamePane.getChildren().add(asteroid.getCharacterShape()));
        gamePane.getChildren().add(ship.getCharacterShape());
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

    public void deleteAllDeadCharacters(){
        deleteDeadCharacters(asteroids);
        deleteDeadCharacters(projectiles);

        if(!ship.isAlive()){
            gamePane.getChildren().remove(ship.getCharacterShape());
        }
    }

    private void deleteDeadCharacters(List<Character> listOfCharacters) {
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
        if (Math.random() < 0.005 * MainMenuView.getChosenDifficultyLevel() * 0.5&&asteroids.size()<10) {
            Random rand = new Random();
            Asteroid asteroid = new Asteroid(rand.nextInt(GameView.getGameScreenWidth()), rand.nextInt(GameView.getGameScreenHeight()));
            if (!asteroid.collide(ship)) {
                asteroids.add(asteroid);
                asteroid.setAlive(true);
                gamePane.getChildren().add(asteroid.getCharacterShape());
            }
        }
    }

    public void ensureCharactersMovement() {
        ship.move();
        asteroids.forEach(Character::move);
        projectiles.forEach(Character::move);
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

    public void setProjectileSpeed(Character projectile) {
        projectile.accelerate();
        projectile.setMovement(projectile.getMovement().normalize().multiply(4));
    }

    public int getPlayersLivesLeft(){
        return ship.getLivesLeft();
    }

    public Ship getShip(){
        return this.ship;
    }

    public void setAllCharactersDead(){
        asteroids.forEach(asteroid -> asteroid.setAlive(false));
        projectiles.forEach(projectile -> projectile.setAlive(false));
        ship.setAlive(false);
    }
}
