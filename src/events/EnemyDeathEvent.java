package events;

import entity.enemy.AbstractEnemy;

public record EnemyDeathEvent(AbstractEnemy enemy) implements Event {
}
