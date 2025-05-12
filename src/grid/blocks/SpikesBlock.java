package grid.blocks;

import events.AfterEntityEnterBlockListener;
import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;

import java.util.Optional;

public class SpikesBlock extends AbstractBlock implements AfterEntityEnterBlockListener {

    private boolean detonated;

    protected SpikesBlock() {
        super(0, "spikes_before", "spikes_after");
        this.detonated = false;
    }

    @Override
    public boolean isSeeThrough() {
        return true;
    }

    @Override
    public boolean isSpawnable() {
        return false;
    }

    @Override
    public Optional<BlockRegister> afterBlockExplosionEvent() {
        this.detonated = true;
        return Optional.empty();
    }

    @Override
    public boolean onEntityEnterBlock(EntityEnterBlockEvent e) {
        return true;
    }

    @Override
    public void afterEnemyEnterBlock(EntityEnterBlockEvent e) {
    }

    @Override
    public boolean onPlayerEnterBlock(PlayerEnterBlockEvent e) {
        return true;
    }

    @Override
    public void afterPlayerEnterBlock(PlayerEnterBlockEvent e) {
        if (!this.detonated) {
            e.player().hurt(1);
            this.setTexture(1);
            this.detonated = true;
        }
    }
}
