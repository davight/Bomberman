package entity.enemy;

import entity.movement.Direction;
import entity.movement.MovementManager;
import entity.player.AbstractPlayer;
import resources.ImageManager;
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
    public void ultimate() {

    }

    @Override
    public int getTimeBetweenSteps() {
        return Util.randomInt(90, 110);
    }

    @Override
    public Map<Direction, MovementManager.Pack> getValidDirections() {
        return Map.of(
                Direction.UP, new MovementManager.Pack(ImageManager.getImage(PATH + "staying"), ImageManager.getImage(PATH + "up_moving")),
                Direction.DOWN, new MovementManager.Pack(ImageManager.getImage(PATH + "staying"), ImageManager.getImage(PATH + "down_moving")),
                Direction.LEFT, new MovementManager.Pack(ImageManager.getImage(PATH + "staying"), ImageManager.getImage(PATH + "left_moving")),
                Direction.RIGHT, new MovementManager.Pack(ImageManager.getImage(PATH + "staying"), ImageManager.getImage(PATH + "right_moving"))
        );
    }
}
