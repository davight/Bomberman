package grid.blocks;

import java.util.function.Supplier;

public enum BlockRegister {

    BARRIER(BarrierBlock::new),
    BARREL(BarrelBlock::new),
    COLOR(ColorBlock::new),
    FIRE(FireBlock::new),
    SPIKES(SpikesBlock::new),
    GRASS(GrassBlock::new),
    WATER(WaterBlock::new),
    STATUE(StatueBlock::new),
    STONE(StoneBlock::new);

    private final Supplier<AbstractBlock> runnable;

    BlockRegister(Supplier<AbstractBlock> runnable) {
        this.runnable = runnable;
    }

    public AbstractBlock getNew() {
        return this.runnable.get();
    }

}
