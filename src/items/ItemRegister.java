package items;

import util.WeightedRandomness;
import java.util.function.Supplier;

/**
 * Enum typov itemov.
 */
public enum ItemRegister {

    /**
     * Freeze typ itemu. Reprezentujuci triedu FreezeItem
     * @see FreezeItem
     */
    FREEZE(4, FreezeItem::new),

    /**
     * Orbit typ itemu. Reprezentujuci triedu OrbitItem
     * @see OrbitItem
     */
    ORBIT(1, OrbitItem::new),

    /**
     * Heart typ itemu. Reprezentujuci triedu HeartItem
     * @see HeartItem
     */
    HEART(9, HeartItem::new);

    private static WeightedRandomness<ItemRegister> weightedList = new WeightedRandomness<>();
    static {
        for (ItemRegister i : ItemRegister.values()) {
            weightedList.add(i, i.weight);
        }
    }

    private final int weight;
    private final Supplier<AbstractItem> supplier;

    ItemRegister(int weight, Supplier<AbstractItem> supplier) {
        this.weight = weight;
        this.supplier = supplier;
    }

    /**
     * @return Novu instanciu tohto typu itemu.
     */
    public AbstractItem getNew() {
        return this.supplier.get();
    }

    /**
     * @return Nahodny typ itemu.
     */
    public static ItemRegister getRandom() {
        return weightedList.getRandom();
    }

}
