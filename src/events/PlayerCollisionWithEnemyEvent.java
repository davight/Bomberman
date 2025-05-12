package events;

import entity.player.AbstractPlayer;
import entity.enemy.AbstractEnemy;

public record PlayerCollisionWithEnemyEvent(AbstractPlayer player, AbstractEnemy enemy) {
}
