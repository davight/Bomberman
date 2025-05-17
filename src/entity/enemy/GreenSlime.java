package entity.enemy;

import entity.movement.Direction;
import entity.movement.MovementManager;
import entity.player.AbstractPlayer;
import util.Util;

import java.util.Map;

public class GreenSlime extends AbstractEnemy {

    private static final String PATH = "entity/classic/";

    public GreenSlime() {
        super();
    }

    @Override
    public void attack(AbstractPlayer player) {
        player.hurt(1);
    }

    @Override
    public int getTimeBetweenSteps() {
        return Util.randomInt(90, 110);
    }

    @Override
    public int millisBetweenMovement() {
        return 1000;
    }

    @Override
    public Map<Direction, MovementManager.Pack> getValidDirections() {
        return Map.of(
                Direction.UP, new MovementManager.Pack(PATH + "staying", PATH + "up_moving"),
                Direction.DOWN, new MovementManager.Pack(PATH + "staying", PATH + "down_moving"),
                Direction.LEFT, new MovementManager.Pack(PATH + "staying", PATH + "left_moving"),
                Direction.RIGHT, new MovementManager.Pack(PATH + "staying", PATH + "right_moving")
        );
    }
}
