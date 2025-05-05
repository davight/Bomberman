package events;

import entity.Player;
import entity.enemy.AbstractEnemy;

public record PlayerCollisionWithEnemyEvent(Player player, AbstractEnemy enemy) {
}
