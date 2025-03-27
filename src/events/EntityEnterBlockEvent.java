package events;

import entity.enemy.AbstractEnemy;
import grid.Tile;

public record EntityEnterBlockEvent(AbstractEnemy entity, Tile newTile, Tile oldTile) {

}
