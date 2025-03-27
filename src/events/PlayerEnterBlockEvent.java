package events;

import entity.Player;
import grid.Tile;

public record PlayerEnterBlockEvent(Player player, Tile newTile, Tile oldTile) {

}
