package asteroids;

import javafx.scene.shape.Polygon;

public class Projectile extends Character{
    public Projectile(int x, int y){
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y,1);
    }

    @Override
    public void move() {
        super.getCharacterShape().setTranslateX(super.getCharacterShape().getTranslateX() + super.getMovement().getX());
        super.getCharacterShape().setTranslateY(super.getCharacterShape().getTranslateY() + super.getMovement().getY());

        if(super.getCharacterShape().getTranslateX()>GameView.gameScreenWidth){
            setAlive(false);
        }

        if(super.getCharacterShape().getTranslateX()<0){
            setAlive(false);
        }

        if(super.getCharacterShape().getTranslateY()>GameView.gameScreenHeight){
            setAlive(false);
        }

        if(super.getCharacterShape().getTranslateY()<0){
            setAlive(false);
        }
    }
}
