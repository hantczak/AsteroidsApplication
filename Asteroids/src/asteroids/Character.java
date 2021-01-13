package asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Character {

    private Polygon characterShape;
    private Point2D movement;
    private boolean alive;
    private int livesLeft;

    protected Character(Polygon polygon, int x, int y, int livesLeft) {
        this.characterShape = polygon;
        this.characterShape.setTranslateX(x);
        this.characterShape.setTranslateY(y);
        this.livesLeft = livesLeft;

        this.movement = new Point2D(0, 0);
    }

    public Polygon getCharacterShape() {
        return characterShape;
    }

    public void turnLeft() {
        this.characterShape.setRotate(this.characterShape.getRotate() - 5);
    }

    public void turnRight() {
        this.characterShape.setRotate(this.characterShape.getRotate() + 5);
    }

    public void move() {
        this.characterShape.setTranslateX(this.characterShape.getTranslateX() + this.movement.getX());
        this.characterShape.setTranslateY(this.characterShape.getTranslateY() + this.movement.getY());

        if (this.characterShape.getTranslateX() < 0) {
            this.characterShape.setTranslateX(this.characterShape.getTranslateX() + GameView.gameScreenWidth);
        }

        if (this.characterShape.getTranslateX() > GameView.gameScreenWidth) {
            this.characterShape.setTranslateX(this.characterShape.getTranslateX() % GameView.gameScreenWidth);
        }

        if (this.characterShape.getTranslateY() < 0) {
            this.characterShape.setTranslateY(this.characterShape.getTranslateY() + GameView.gameScreenHeight);
        }

        if (this.characterShape.getTranslateY() > GameView.gameScreenHeight) {
            this.characterShape.setTranslateY(this.characterShape.getTranslateY() % GameView.gameScreenHeight);
        }
    }


    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.characterShape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.characterShape.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }

    public void decelerate() {
        double changeX = Math.cos(Math.toRadians(this.characterShape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.characterShape.getRotate()));

        changeX *= -0.05;
        changeY *= -0.05;

        this.movement = this.movement.add(changeX, changeY);
    }

    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(this.characterShape, other.getCharacterShape());
        return collisionArea.getBoundsInLocal().getWidth() > 0;
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = this.movement.add(movement.getX(), movement.getY());
    }

    public void setAlive(boolean ifAlive) {
        this.alive = ifAlive;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setLivesLeft(int number) {
        this.livesLeft = number;
        if (livesLeft == 0) {
            setAlive(false);
        }
    }

    public int getLivesLeft() {
        return this.livesLeft;
    }
}