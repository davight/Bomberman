package events;

import entity.enemy.AbstractEnemy;

/**
 * Record event trieda, ktora zaznamenava umrtie nepriatela.
 * @param enemy nepriatel, ktory umrel
 */
public record EnemyDeathEvent(AbstractEnemy enemy) implements Event {
}
