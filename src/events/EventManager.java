package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Trieda EventManager na spravovanie, registrovanie a odposluchanie hernych eventov.
 */
public class EventManager {

    private static HashMap<Class<? extends Event>, ArrayList<Consumer<? extends Event>>> events = new HashMap<>();

    static {
        registerEvent(EnemyEnterTileEvent.class);
        registerEvent(EnemyStepOnBlockEvent.class);
        registerEvent(EnemyDeathEvent.class);
        registerEvent(PlayerEnterTileEvent.class);
        registerEvent(PlayerStepOnBlockEvent.class);
        registerEvent(PlayerDeathEvent.class);
        registerEvent(ItemSpawnEvent.class);
        registerEvent(ItemDespawnEvent.class);
    }

    private static <T extends Event> void registerEvent(Class<T> event) {
        events.put(event, new ArrayList<>());
    }

    /**
     * "Spusti" dany event
     * @param event event, ktory sa ma spustit
     */
    @SuppressWarnings("unchecked")
    public static <T extends Event> void fireEvent(T event) {
        for (Consumer<? extends Event> listener : new ArrayList<>(events.get(event.getClass()))) {
            ((Consumer<T>)listener).accept(event);
        }
    }

    /**
     * Registruje novy handler pre dany typ eventu.
     * @param event typ eventu
     * @param handler kod, ktory bude spusteny
     */
    public static <T extends Event> void registerHandler(Class<T> event, Consumer<T> handler) {
        if (!events.containsKey(event)) {
            throw new IllegalArgumentException("Event " + event + " is not registered!");
        }
        events.get(event).add(handler);
    }

}
