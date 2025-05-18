package grid.blocks;

import events.EnemyEnterTileEvent;
import events.EventManager;
import events.PlayerEnterTileEvent;
import events.PlayerStepOnBlockEvent;

public class SpikesBlock extends AbstractBlock {

    private boolean detonated;

    static {
        EventManager.registerHandler(PlayerStepOnBlockEvent.class, (event) -> {
            if (event.block() instanceof SpikesBlock block) {
                block.afterPlayerStepOnBlock(event);
            }
        });
    }

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
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return true;
    }

    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return true;
    }

    private void afterPlayerStepOnBlock(PlayerStepOnBlockEvent event) {
        if (!this.detonated) {
            event.player().hurt(1);
            super.setTexture(1);
            event.player().getTile().update();
            this.detonated = true;
        }
    }
}
