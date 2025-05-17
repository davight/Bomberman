package events;

import entity.enemy.AbstractEnemy;
import grid.map.Tile;

public record EnemyEnterTileEvent(AbstractEnemy entity, Tile newTile, Tile oldTile) implements Event {
}
