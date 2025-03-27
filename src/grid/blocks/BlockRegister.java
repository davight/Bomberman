package grid.blocks;

import java.util.function.Supplier;

public enum BlockRegister {

    BARRIER(BarrierBlock::new),
    BRICKS(BricksBlock::new),
    GRASS(GrassBlock::new),
    WATER(WaterBlock::new),
    STONE(StoneBlock::new);

    private final Supplier<AbstractBlock> runnable;

    BlockRegister(Supplier<AbstractBlock> block) {
        this.runnable = block;
    }

    public AbstractBlock getNew() {
        return this.runnable.get();
    }

}
