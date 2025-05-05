package grid.blocks;

import events.EntityEnterBlockEvent;
import events.PlayerEnterBlockEvent;

import java.util.Optional;
import java.util.Random;

public class BarrelBlock extends AbstractBlock {

    private static final Random RANDOM = new Random();

    protected BarrelBlock() {
        super("barrel", "barrel2");
    }

    @Override
    public boolean isSeeThrough() {
        return false;
    }

    @Override
    public Optional<BlockRegister> afterBlockExplosionEvent() {
        return Optional.of(RANDOM.nextInt(100) > 80 ? BlockRegister.WATER : BlockRegister.GRASS);
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
