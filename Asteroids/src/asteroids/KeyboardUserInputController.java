package asteroids;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class KeyboardUserInputController {
    private Map<KeyCode,Boolean> pressedKeys;
    private Scene gameScene;
    private Character ship;

    public KeyboardUserInputController(Scene gameScene, Character ship){
        this.pressedKeys = new HashMap<>();
        this.gameScene = gameScene;
        this.ship = ship;
    }

    public void checkForInput(){
        turn();
        accelerate();
        decelerate();
    }

    public void readKeyboardInput() {
        gameScene.setOnKeyPressed(keyEvent -> {
            this.pressedKeys.put(keyEvent.getCode(), true);
        });

        gameScene.setOnKeyReleased(keyEvent -> {
            this.pressedKeys.put(keyEvent.getCode(), false);
        });
    }

    public void turn(){
        if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
            ship.turnLeft();
        }
        if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
            ship.turnRight();
        }
    }

    public void accelerate(){
        if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
            ship.accelerate();
        }
    }

    public void decelerate(){
        if (pressedKeys.getOrDefault(KeyCode.DOWN, false)) {
            ship.decelerate();
        }
    }

    public boolean isShooting(){
        if (pressedKeys.getOrDefault(KeyCode.SPACE, false)) {
            pressedKeys.put(KeyCode.SPACE, false);
            return true;
        }
        return false;
    }

}