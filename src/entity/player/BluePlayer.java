package entity.player;

import entity.movement.Direction;
import entity.movement.MovementManager;
import grid.map.Tile;
import resources.ImageManager;
import util.Debug;

import java.util.Map;

public class BluePlayer extends AbstractPlayer {

    public BluePlayer(Tile tile) {
        super(tile, false);
    }

    public void moveBlueDown() {
        super.move(Direction.DOWN);
    }

    public void moveBlueUp() {
        super.move(Direction.UP);
    }

    public void moveBlueLeft() {
        super.move(Direction.LEFT);
    }

    public void moveBlueRight() {
        super.move(Direction.RIGHT);
    }

    public void activateBlue() {
        super.pressEnter();
    }

    @Override
    public Map<Direction, MovementManager.Pack> getValidDirections() {
        return Map.of(
                Direction.UP, new MovementManager.Pack(ImageManager.getImage("player/up_staying"), ImageManager.getImage("player/up_moving")),
                Direction.DOWN, new MovementManager.Pack(ImageManager.getImage("player/down_staying"), ImageManager.getImage("player/down_moving")),
                Direction.LEFT, new MovementManager.Pack(ImageManager.getImage("player/left_staying"), ImageManager.getImage("player/left_moving")),
                Direction.RIGHT, new MovementManager.Pack(ImageManager.getImage("player/right_staying"), ImageManager.getImage("player/right_moving"))
        );
    }
}
