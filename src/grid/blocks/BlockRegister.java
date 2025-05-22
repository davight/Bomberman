package grid.blocks;

import java.util.function.Supplier;

/**
 * Enum typov kociek.
 */
public enum BlockRegister {

    /**
     * Barrier typ kocky. Reprezentujuci triedu BarrierBlock
     * @see BarrierBlock
     */
    BARRIER(BarrierBlock::new),

    /**
     * Barrel typ kocky. Reprezentujuci triedu BarrelBlock
     * @see BarrelBlock
     */
    BARREL(BarrelBlock::new),

    /**
     * Fire typ kocky. Reprezentujuci triedu FireBlock
     * @see FireBlock
     */
    FIRE(FireBlock::new),

    /**
     * Spikes typ kocky. Reprezentujuci triedu SpikesBlock
     * @see SpikesBlock
     */
    SPIKES(SpikesBlock::new),

    /**
     * Grass typ kocky. Reprezentujuci triedu GrassBlock
     * @see GrassBlock
     */
    GRASS(GrassBlock::new),

    /**
     * Water typ kocky. Reprezentujuci triedu WaterBlock
     * @see WaterBlock
     */
    WATER(WaterBlock::new),

    /**
     * Statue typ kocky. Reprezentujuci triedu StatueBlock
     * @see StatueBlock
     */
    STATUE(StatueBlock::new),

    /**
     * Stone typ kocky. Reprezentujuci triedu StoneBlock
     * @see StoneBlock
     */
    STONE(StoneBlock::new);

    private final Supplier<AbstractBlock> runnable;

    BlockRegister(Supplier<AbstractBlock> runnable) {
        this.runnable = runnable;
    }

    /**
     * @return Novu instanciu daneho typu kocky.
     */
    public AbstractBlock getNew() {
        return this.runnable.get();
    }

}
