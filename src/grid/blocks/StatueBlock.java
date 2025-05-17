package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;
import grid.map.Tile;
import items.ItemRegister;
import util.Util;

import java.util.Optional;

public class StatueBlock extends AbstractBlock implements Explodable {

    protected StatueBlock() {
        super("statue1");
    }

    @Override
    public boolean isSpawnable() {
        return false;
    }

    @Override
    public boolean isSeeThrough() {
        return false;
    }

    @Override
    public boolean canEnemyEnterBlock(EnemyEnterTileEvent e) {
        return false;
    }

    @Override
    public boolean canPlayerEnterBlock(PlayerEnterTileEvent e) {
        return false;
    }

    @Override
    public Optional<BlockRegister> newBlock() {
        return Optional.of(BlockRegister.GRASS);
    }

    @Override
    public void onExplosion(Tile at) {
        Util.randomBooleanThen(70, () -> at.spawnItem(ItemRegister.getRandom().getNew()));
    }
}
