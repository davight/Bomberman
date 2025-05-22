package events;

import entity.player.AbstractPlayer;

/**
 * Record event trieda, ktora zaznamenava smrt hraca.
 * @param player hrac, ktory umrel
 */
public record PlayerDeathEvent(AbstractPlayer player) implements Event {
}
