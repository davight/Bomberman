package events;

import entity.player.AbstractPlayer;
import grid.map.Tile;

public record PlayerEnterTileEvent(AbstractPlayer player, Tile newTile, Tile oldTile) implements Event {
}
