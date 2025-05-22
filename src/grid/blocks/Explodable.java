package grid.blocks;

import grid.map.Tile;

import java.util.Optional;

/**
 * Interface Explodable na oznacenie kociek, ktore mozu vybuchnut a po ich vybuchnuti moze nieco nastat.
 */
public interface Explodable {

    /**
     * @return Volitelnu kocku, ktora sa po vybuchu starej zobrazi na jej mieste.
     */
    Optional<BlockRegister> newBlock();

    /**
     * Spusti po vybuchnu na danom mieste.
     * @param at miesto vybuchu
     */
    default void onExplosion(Tile at) {
    }
}
