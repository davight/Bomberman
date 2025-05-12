package items;

import util.WeightedRandomness;
import java.util.function.Supplier;

public enum ItemRegister {

    FREEZE(4, Freeze::new),
    ORBIT(1, Orbit::new),
    HEART(9, Heart::new);

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

    public AbstractItem getNew() {
        return this.supplier.get();
    }

    public static ItemRegister getRandom() {
        return weightedList.getRandom();
    }

}
