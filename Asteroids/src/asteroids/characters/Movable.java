package asteroids.characters;


import javafx.scene.shape.Polygon;

public interface Movable {
    public void move();

    public Polygon getCharacterShape();

    public boolean isAlive();

    public void setAlive(boolean ifAlive);

    public boolean collide(Movable movable);

    public void accelerate();

    public void decelerate();
}
