package grid.blocks;

import grid.map.Tile;

import java.util.Optional;

public interface Explodable {

    Optional<BlockRegister> newBlock();

    default void onExplosion(Tile at) {
    }
}
