package events;

import entity.enemy.AbstractEnemy;
import grid.blocks.AbstractBlock;

/**
 * Record event trieda, ktora zaznamenava pohyb nepriatela na kocku.
 * @param block kocka, na ktoru nepriatel stupil
 * @param enemy nepriatel, ktory na kocku stupil
 */
public record EnemyStepOnBlockEvent(AbstractBlock block, AbstractEnemy enemy) implements Event {
}
