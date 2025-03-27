package entity.enemy;

import entity.movement.Direction;
import fri.shapesge.Image;
import fri.shapesge.ImageData;
import resources.ImageManager;

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
    public Direction.Pack[] getValidDirections() {
        return new Direction.Pack[] {
            new Direction.Pack(Direction.UP, ImageManager.getImage("st"), new ImageData[]{}),
            new Direction.Pack(Direction.DOWN, ImageManager.getImage("st"), new ImageData[]{}),
        };
    }
}
