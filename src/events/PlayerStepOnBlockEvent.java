package events;

import entity.player.AbstractPlayer;
import grid.blocks.AbstractBlock;

public record PlayerStepOnBlockEvent(AbstractPlayer player, AbstractBlock block) implements Event {
}
