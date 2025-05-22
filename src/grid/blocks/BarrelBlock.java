package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;
import grid.map.Tile;
import items.ItemRegister;
import util.Util;
import util.WeightedRandomness;

import java.util.Optional;

/**
 * Trieda BarrelBlock, ktora predstavuje kocku Barrelu.
 */
public class BarrelBlock extends AbstractBlock implements Explodable {

    private static WeightedRandomness<BlockRegister> newBlocks = new WeightedRandomness<>();
    static {
        newBlocks.add(BlockRegister.GRASS, 8);
        newBlocks.add(BlockRegister.FIRE, 1);
        newBlocks.add(BlockRegister.SPIKES, 1);
    }

    protected BarrelBlock() {
        super("barrel", "barrel2", "barrel3", "barrel4");
    }

    /**
     * @return Ci je kocka bezpecna pre spawn hracov a nepriatelov.
     */
    @Override
    public boolean isSpawnable() {
        return false;
    }

    /**
     * @return Ci je mozne cez kocku vidiet dalej.
     */
    @Override
    public boolean isSeeThrough() {
        return false;
    }

    /**
     * @return Ci je mozne aby nepriatel vosiel na tuto kocku.
     */
    @Override
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return false;
    }

    /**
     * @return Ci je mozne aby hrac vosiel na tuto kocku.
     */
    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return false;
    }

    /**
     * @return Volitelnu kocku, ktora sa po vybuchu starej zobrazi na jej mieste.
     */
    @Override
    public Optional<BlockRegister> newBlock() {
        return Optional.of(newBlocks.getRandom());
    }

    /**
     * Spusti po vybuchnu na danom mieste.
     * @param at miesto vybuchu
     */
    @Override
    public void onExplosion(Tile at) {
        Util.randomBooleanThen(2, () -> at.spawnItem(ItemRegister.HEART));
    }
}
