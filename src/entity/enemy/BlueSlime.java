package entity.enemy;

import entity.movement.Direction;
import entity.movement.MovementManager;
import fri.shapesge.Image;
import resources.ImageManager;

import java.util.Map;

public class BlueSlime extends AbstractEnemy {

    public BlueSlime() {
        super(new Image(ImageManager.getImage("entity/explosive/staying")));
    }

    @Override
    public void attack() {

    }

    @Override
    public void ultimate() {

    }

    @Override
    public int getTimeBetweenSteps() {
        return 20;
    }

    @Override
    public Map<Direction, MovementManager.Pack> getValidDirections() {
        return Map.of(
                Direction.UP, new MovementManager.Pack(ImageManager.getImage("st"), null),
                Direction.DOWN, new MovementManager.Pack(ImageManager.getImage("st"), null)
        );
    }
}
