package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;

/**
 * Trieda StoneBlock, ktora predstavuje kocku Stone.
 */
public class StoneBlock extends AbstractBlock {

    protected StoneBlock() {
        super("rock1", "rock2", "rock3", "rock4", "rock5", "rock6", "rock7");
    }

    /**
     * @return Ci je mozne cez kocku vidiet dalej.
     */
    @Override
    public boolean isSeeThrough() {
        return false;
    }

    /**
     * @return Ci je kocka bezpecna pre spawn hracov a nepriatelov.
     */
    @Override
    public boolean isSpawnable() {
        return false;
    }

    /**
     * @return Ci je mozne aby nepriatel vosiel na tuto kocku.
     */
    @Override
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return false;
    }

    /**
     * @return Ci je mozne aby hrac vosiel na tuto kocku.
     */
    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return false;
    }
}
