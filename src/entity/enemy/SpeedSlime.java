package entity.enemy;

import entity.movement.Direction;
import entity.movement.MovementManager;
import entity.player.AbstractPlayer;
import util.Util;

import java.util.EnumMap;

public class SpeedSlime extends AbstractEnemy {

    private static final String PATH = "entity/classic/";

    protected SpeedSlime() {
        super();
    }

    /**
     * Utok na hraca.
     * @param player hrac, na ktoreho bude nepriatel utocit.
     */
    @Override
    public void attack(AbstractPlayer player) {
        player.hurt(1);
    }

    /**
     * @return Cas v milisekundach medzi krokmi pri pohybe.
     */
    @Override
    public int getTimeBetweenSteps() {
        return Util.randomInt(90, 110);
    }

    /**
     * @return Cas v milisekundach medzi pokusom o dalsi pohyb.
     */
    @Override
    public int millisBetweenMovement() {
        return 1000;
    }

    /**
     * @return Zoznam stran s ich prislusnymi texturami, ktorymi sa nepriatel moze pohybovat.
     */
    @Override
    public EnumMap<Direction, MovementManager.Pack> getValidDirections() {
        EnumMap<Direction, MovementManager.Pack> pack = new EnumMap<>(Direction.class);
        pack.put(Direction.UP, new MovementManager.Pack(PATH + "staying", PATH + "up_moving"));
        pack.put(Direction.DOWN, new MovementManager.Pack(PATH + "staying", PATH + "down_moving"));
        pack.put(Direction.LEFT, new MovementManager.Pack(PATH + "staying", PATH + "left_moving"));
        pack.put(Direction.RIGHT, new MovementManager.Pack(PATH + "staying", PATH + "right_moving"));
        return pack;
    }
}
