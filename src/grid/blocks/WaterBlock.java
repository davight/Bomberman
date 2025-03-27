package grid.blocks;

import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;

import java.util.Optional;

public class WaterBlock extends AbstractBlock {

    protected WaterBlock() {
        super("water", "water_with_lily_pad");
    }

    @Override
    public boolean isSeeThrough() {
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
        return true;
    }

    @Override
    public void afterPlayerEnterBlockEvent(PlayerEnterBlockEvent e) {
        e.player().takeDamage(999);
    }
}
