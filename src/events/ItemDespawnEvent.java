package events;

import items.AbstractItem;

/**
 * Record event trieda, ktora zaznamenava despawn itemu.
 * @param item item, ktory sa despawnul
 */
public record ItemDespawnEvent(AbstractItem item) implements Event {
}
