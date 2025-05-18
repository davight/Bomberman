package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;
import grid.map.Tile;
import items.ItemRegister;
import util.Util;

import java.util.Optional;

/**
 * Trieda StatueBlock, ktora predstavuje kocku Statue.
 */
public class StatueBlock extends AbstractBlock implements Explodable {

    protected StatueBlock() {
        super("statue1");
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
        return Optional.of(BlockRegister.GRASS);
    }

    /**
     * Spusti po vybuchnu na danom mieste.
     * @param at miesto vybuchu
     */
    @Override
    public void onExplosion(Tile at) {
        Util.randomBooleanThen(70, () -> at.spawnItem(ItemRegister.getRandom()));
    }
}
