package events;

import entity.player.AbstractPlayer;

public record PlayerDeathEvent(AbstractPlayer player) implements Event {
}
