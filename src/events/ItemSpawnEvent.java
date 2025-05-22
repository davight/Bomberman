package events;

import grid.map.Tile;
import items.AbstractItem;

/**
 * Record event trieda, ktora zaznamenava spawn noveho itemu.
 * @param item item, ktory sa spawnul
 * @param at tile, na ktorom sa spawnul
 */
public record ItemSpawnEvent(AbstractItem item, Tile at) implements Event {
}
