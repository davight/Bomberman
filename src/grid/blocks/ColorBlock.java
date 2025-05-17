package grid.blocks;

import events.EnemyStepOnBlockEvent;
import events.EnemyEnterTileEvent;
import events.EventManager;
import events.PlayerEnterTileEvent;
import events.PlayerStepOnBlockEvent;

public class ColorBlock extends AbstractBlock {

    protected ColorBlock() {
        super("red_block", "green_block", "blue_block");

        EventManager.registerHandler(PlayerStepOnBlockEvent.class, (event) -> {
            if (event.block() == this) {
                this.onBlockStep();
            }
        });
        EventManager.registerHandler(EnemyStepOnBlockEvent.class, (event) -> {
            if (event.block() == this) {
                this.onBlockStep();
            }
        });
    }

    @Override
    public boolean isSeeThrough() {
        return true;
    }

    @Override
    public boolean isSpawnable() {
        return true;
    }

    @Override
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return true;
    }

    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return true;
    }

    private void onBlockStep() {
        super.setRandomTexture();
    }
}
