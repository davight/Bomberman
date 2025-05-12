package events;

import entity.enemy.AbstractEnemy;
import grid.map.Tile;

public record EntityEnterBlockEvent(AbstractEnemy entity, Tile newTile, Tile oldTile) {

}
