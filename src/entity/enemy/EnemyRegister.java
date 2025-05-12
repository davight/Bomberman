package entity.enemy;

import util.WeightedRandomness;

import java.util.function.Supplier;

public enum EnemyRegister {

    BLUE_SLIME(1, BlueSlime::new),
    GREEN_SLIME(1, GreenSlime::new);

    private static WeightedRandomness<EnemyRegister> randomEnemies = new WeightedRandomness<>();
    static {
        for (EnemyRegister e : values()) {
            randomEnemies.add(e, e.getWeight());
        }
    }

    private final Supplier<AbstractEnemy> supplier;
    private final int weight;

    EnemyRegister(Supplier<AbstractEnemy> supplier) {
        this(1, supplier);
    }

    EnemyRegister(int weight, Supplier<AbstractEnemy> supplier) {
        this.supplier = supplier;
        this.weight = weight;
    }

    private int getWeight() {
        return this.weight;
    }

    public AbstractEnemy getNew() {
        return this.supplier.get();
    }

    public static EnemyRegister getRandom() {
        return randomEnemies.getRandom();
    }

}
