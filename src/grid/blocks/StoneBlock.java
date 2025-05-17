package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;

public class StoneBlock extends AbstractBlock {

    protected StoneBlock() {
        super("rock1", "rock2", "rock3", "rock4", "rock5", "rock6", "rock7");
    }

    @Override
    public boolean isSeeThrough() {
        return false;
    }

    @Override
    public boolean isSpawnable() {
        return false;
    }

    @Override
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return false;
    }

    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return false;
    }
}
