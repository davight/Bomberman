package events;

import entity.player.AbstractPlayer;
import grid.blocks.AbstractBlock;

/**
 * Record event trieda, ktora zaznamenava pohyb hraca na kocku.
 * @param block kocka, na ktoru hrac stupil
 * @param player hrac, ktory na kocku stupil
 */
public record PlayerStepOnBlockEvent(AbstractPlayer player, AbstractBlock block) implements Event {
}
