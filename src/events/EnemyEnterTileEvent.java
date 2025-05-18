package events;

import entity.enemy.AbstractEnemy;
import grid.map.Tile;

/**
 * Record event trieda, ktora zaznamenava pohyb nepriatela na tile.
 * @param entity nepriatel, ktory vstupil na tile
 * @param newTile tile, na ktory vstupil nepriatel
 * @param oldTile tile, na ktorom predtym nepriatel stal
 */
public record EnemyEnterTileEvent(AbstractEnemy entity, Tile newTile, Tile oldTile) implements Event {
}
