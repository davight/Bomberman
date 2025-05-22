package entity.enemy;

import entity.movement.Direction;
import entity.movement.MovementManager;
import entity.player.AbstractPlayer;
import util.Util;

import java.util.EnumMap;

public class DeadlySlime extends AbstractEnemy {

    private static final String PATH = "entity/deadly/";

    private EnumMap<Direction, MovementManager.Pack> map = null;

    protected DeadlySlime() {
        super();
    }

    /**
     * @return Cas v milisekundach medzi pokusom o dalsi pohyb.
     */
    @Override
    public int millisBetweenMovement() {
        return 2000;
    }

    /**
     * Utok na hraca.
     * @param player hrac, na ktoreho bude nepriatel utocit.
     */
    @Override
    public void attack(AbstractPlayer player) {
        player.kill();
    }

    /**
     * @return Cas v milisekundach medzi krokmi pri pohybe.
     */
    @Override
    public int getTimeBetweenSteps() {
        return 100;
    }

    /**
     * @return Zoznam stran s ich prislusnymi texturami, ktorymi sa nepriatel moze pohybovat.
     */
    @Override
    public EnumMap<Direction, MovementManager.Pack> getValidDirections() {
        if (this.map == null) {
            this.map = new EnumMap<>(Direction.class);
            if (Util.randomBoolean(50)) {
                this.map.put(Direction.UP, new MovementManager.Pack(PATH + "staying", PATH + "up_moving"));
                this.map.put(Direction.DOWN, new MovementManager.Pack(PATH + "staying", PATH + "down_moving"));
            } else {
                this.map.put(Direction.LEFT, new MovementManager.Pack(PATH + "staying", PATH + "left_moving"));
                this.map.put(Direction.RIGHT, new MovementManager.Pack(PATH + "staying", PATH + "right_moving"));
            }
        }
        return this.map;
    }
}
