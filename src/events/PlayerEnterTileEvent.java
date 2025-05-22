package events;

import entity.player.AbstractPlayer;
import grid.map.Tile;

/**
 * Record event trieda, ktora zaznamenava pohyb hrac na tile.
 * @param player hrac, ktory vstupil na tile
 * @param newTile tile, na ktory vstupil hrac
 * @param oldTile tile, na ktorom predtym hrac stal
 */
public record PlayerEnterTileEvent(AbstractPlayer player, Tile newTile, Tile oldTile) implements Event {
}
