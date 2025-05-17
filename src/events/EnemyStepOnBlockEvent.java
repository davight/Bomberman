package events;

import entity.enemy.AbstractEnemy;
import grid.blocks.AbstractBlock;

public record EnemyStepOnBlockEvent(AbstractBlock block, AbstractEnemy enemy) implements Event {
}
