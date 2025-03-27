package grid.blocks;

import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;

import java.util.Optional;

public class ColorBlock extends AbstractBlock {

    protected ColorBlock() {
        super("red_block", "green_block", "blue_block");
    }

    @Override
    public boolean isSeeThrough() {
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
    public void afterEntityEnterBlockEvent(EntityEnterBlockEvent e) {
        this.setRandomTexture();
    }

    @Override
    public boolean onPlayerEnterBlock(PlayerEnterBlockEvent e) {
        return true;
    }

    @Override
    public void afterPlayerEnterBlockEvent(PlayerEnterBlockEvent e) {
        this.setRandomTexture();
    }
}
