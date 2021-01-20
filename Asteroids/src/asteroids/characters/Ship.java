package asteroids.characters;


import javafx.scene.shape.Polygon;

public class Ship extends Character implements Movable{

    public Ship(int x, int y, int shipLives) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y, shipLives);
        if (shipLives == 0) {
            this.setAlive(false);
        }else {
            this.setAlive(true);
        }
    }

    public void increaseLives() {
        this.setLivesLeft(getLivesLeft() + 1);
    }

    public void decreaseLives() {
        this.setLivesLeft(this.getLivesLeft() - 1);
    }

    @Override
    public boolean isAlive() {
        return super.isAlive();
    }
}
