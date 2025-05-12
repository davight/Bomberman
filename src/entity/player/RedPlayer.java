package entity.player;

import entity.movement.Direction;
import entity.movement.MovementManager;
import grid.map.Tile;
import resources.ImageManager;

import java.util.Map;

public class RedPlayer extends AbstractPlayer {

    public RedPlayer(Tile tile) {
        super(tile, true);
    }

    public void moveRedDown() {
        super.move(Direction.DOWN);
    }

    public void moveRedUp() {
        super.move(Direction.UP);
    }

    public void moveRedLeft() {
        super.move(Direction.LEFT);
    }

    public void moveRedRight() {
        super.move(Direction.RIGHT);
    }

    public void activateRed() {
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
