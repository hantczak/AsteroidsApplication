package asteroids;

import java.util.Random;

public class Asteroid extends Character {

    private double rotationalMovement;

    public Asteroid(int x, int y) {
        super(PolygonFactory.createPolygon(), x, y,1);

        Random rand = new Random();

        super.getCharacterShape().setRotate(rand.nextInt(360));
        int accelerationValue = rand.nextInt(10) + 1;
        for(int i = 0; i<accelerationValue;i++){
            accelerate();
        }

        this.rotationalMovement = 0.5-rand.nextDouble();
    }

    public void move(){
        super.move();
        super.getCharacterShape().setRotate(super.getCharacterShape().getRotate()+rotationalMovement);
    }
}
