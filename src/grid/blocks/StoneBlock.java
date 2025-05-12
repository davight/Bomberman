package grid.blocks;

import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;

import java.util.Optional;

public class StoneBlock extends AbstractBlock {

    protected StoneBlock() {
        super("rock4", "rock5", "rock6", "rock7", "rock8");
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
    public Optional<BlockRegister> afterBlockExplosionEvent() {
        return Optional.empty();
    }

    @Override
    public boolean onEntityEnterBlock(EntityEnterBlockEvent e) {
        return false;
    }

    @Override
    public boolean onPlayerEnterBlock(PlayerEnterBlockEvent e) {
        return false;
    }
}
