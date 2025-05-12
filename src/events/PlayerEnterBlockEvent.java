package events;

import entity.player.AbstractPlayer;
import grid.map.Tile;

public record PlayerEnterBlockEvent(AbstractPlayer player, Tile newTile, Tile oldTile) {

}
