package grid.blocks;

import events.AfterEntityEnterBlockListener;
import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;

import java.util.Optional;

public class ColorBlock extends AbstractBlock implements AfterEntityEnterBlockListener {

    protected ColorBlock() {
        super("red_block", "green_block", "blue_block");
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
    public Optional<BlockRegister> afterBlockExplosionEvent() {
        return Optional.empty();
    }

    @Override
    public boolean onEntityEnterBlock(EntityEnterBlockEvent e) {
        return true;
    }

    @Override
    public void afterEnemyEnterBlock(EntityEnterBlockEvent e) {
        this.setRandomTexture();
    }

    @Override
    public boolean onPlayerEnterBlock(PlayerEnterBlockEvent e) {
        return true;
    }

    @Override
    public void afterPlayerEnterBlock(PlayerEnterBlockEvent e) {
        this.setRandomTexture();
    }
}
