package grid.blocks;

import events.EnemyEnterTileEvent;
import events.PlayerEnterTileEvent;
import grid.map.Tile;
import items.ItemRegister;
import util.Util;

import java.util.Optional;

public class BarrelBlock extends AbstractBlock implements Explodable {

    protected BarrelBlock() {
        super("barrel", "barrel2");
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
        return Optional.of(Util.randomBoolean(90) ? BlockRegister.GRASS : BlockRegister.COLOR);
    }

    @Override
    public void onExplosion(Tile at) {
        Util.randomBooleanThen(2, () -> at.spawnItem(ItemRegister.HEART.getNew()));
    }
}
