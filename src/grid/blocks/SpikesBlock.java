package grid.blocks;

import events.EnemyEnterTileEvent;
import events.EventManager;
import events.PlayerEnterTileEvent;
import events.PlayerStepOnBlockEvent;

public class SpikesBlock extends AbstractBlock {

    private boolean detonated;

    protected SpikesBlock() {
        super(0, "spikes_before", "spikes_after");
        this.detonated = false;

        EventManager.registerHandler(PlayerStepOnBlockEvent.class, (event) -> {
            if (event.block() == this) {
                this.afterPlayerStepOnBlock(event);
            }
        });
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
            this.setTexture(1);
            this.detonated = true;
        }
    }
}
