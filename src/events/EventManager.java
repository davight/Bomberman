package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class EventManager {

    private static HashMap<Class<? extends Event>, ArrayList<Consumer<? extends Event>>> events = new HashMap<>();

    static {
        registerEvent(EnemyEnterTileEvent.class);
        registerEvent(EnemyStepOnBlockEvent.class);
        registerEvent(EnemyDeathEvent.class);
        registerEvent(PlayerEnterTileEvent.class);
        registerEvent(PlayerStepOnBlockEvent.class);
        registerEvent(PlayerDeathEvent.class);
        registerEvent(PlayerDeathEvent.class);
    }

    private static <T extends Event> void registerEvent(Class<T> event) {
        events.put(event, new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Event> void fireEvent(T event) {
        for (Consumer<? extends Event> listener : events.get(event.getClass())) {
            ((Consumer<T>)listener).accept(event); // je to safe kedze T implementuje Event
        }
    }

    public static <T extends Event> void registerHandler(Class<T> event, Consumer<T> handler) {
        if (!events.containsKey(event)) {
            throw new IllegalArgumentException("Event " + event + " is not registered!");
        }
        events.get(event).add(handler);
    }

}
